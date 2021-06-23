package com.teknisi.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.dao.RequestDaoImpl;
import com.teknisi.model.Chart;
import com.teknisi.model.Request;

//import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class FileServiceImpl implements FileService{
	@Autowired RequestService requestService;
	@Autowired RequestDaoImpl requestDaoImpl;	
	
	@Override
	public File getLastModified(String path) {
		 File directory = new File(path);
		    File[] files = directory.listFiles(File::isFile);
		    long lastModifiedTime = Long.MIN_VALUE;
		    File chosenFile = null;

		    if (files != null)
		    {
		        for (File file : files)
		        {
		            if (file.lastModified() > lastModifiedTime)
		            {
		                chosenFile = file;
		                lastModifiedTime = file.lastModified();
		            }
		        }
		    }

		    return chosenFile;
	}


	@Override
	public CsvPreference customCsvPreference() {
		return new CsvPreference.Builder('|', ',', "\n").build();
	}

	@Override
	public byte[] exportToCSV() throws IOException {
        List<Request> listRequest = requestService.showAllPendingRequest();
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        File outputFile = File.createTempFile("./csv/"+ "REQUEST_"+ currentDateTime+"#", ".csv");
        FileWriter filePath = new FileWriter(outputFile, true);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(filePath, customCsvPreference());
        String[] csvHeader = {"Teknisi ID", "Request ID", "Merchant Name", "Address", "City", "PIC", "Created_date", "Status"};
        String[] nameMapping = {"teknisi_id", "request_id", "merchant_name", "address", "city","pic","created_date","status"};
        csvWriter.writeHeader(csvHeader);
        for (Request request : listRequest) {
            csvWriter.write(request, nameMapping);
        }
        csvWriter.close();
		return  Files.readAllBytes(Paths.get(outputFile.getPath()));
	}


	@Override
	public byte[] exportToPDF() throws FileNotFoundException, IOException, JRException, MessagingException {
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.getAllStatusRequest("FINISHED");
//		ArrayList<Request> arrayListRequest = new ArrayList <Request>();
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/FinishedRequestList.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        File outputFile = File.createTempFile("./pdf/"+"FINISHED_REQUEST_"+ currentDateTime+"#", ".pdf");
        JasperExportManager.exportReportToPdfStream(report,new FileOutputStream(outputFile));
		return JasperExportManager.exportReportToPdf(report);
	}
	
	
	@Override
	public byte[] exportPDF(Date start_date, Date end_date) throws IOException, JRException {
		byte[] rep = null;
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showRequestReport(start_date, end_date);
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/Report.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");  
        String stringStartDate = dateFormat.format(start_date); 
        String stringEndDate = dateFormat.format(end_date); 
		map.put("StartDate", stringStartDate);
		map.put("EndDate", stringEndDate);
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
		rep = JasperExportManager.exportReportToPdf(report);
		return rep;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public byte[] exportToXLS() throws IOException, JRException {
		byte[] report = null;
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/RekapitulasiDataRequest.jrxml"));
        ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.showAllRecapitulationRequest();
//        ArrayList<Request> arrayListRequest = new ArrayList <Request>();
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
        Map<String, Object> params = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanCollectionDataSource);

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setIgnoreGraphics(false);
        configuration.setDetectCellType(true);

        try {
     	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Exporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            report =  byteArrayOutputStream.toByteArray();
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
		return report;

    }
	
	
	@Override
	public byte[] exportToPDFChart() throws FileNotFoundException, JRException, IOException  {
		ArrayList<Chart> arrayListRequest =(ArrayList<Chart>) requestDaoImpl.getAllRequestCount();
//		ArrayList<Chart> arrayListRequest = new ArrayList <Chart>();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(arrayListRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/RecapitulationChartBar.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanColDataSource);
		return JasperExportManager.exportReportToPdf(report);
	}
	
}
