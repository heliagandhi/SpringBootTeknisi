package com.teknisi.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.dao.RequestDaoImpl;
import com.teknisi.model.Chart;
import com.teknisi.model.Request;

import net.sf.jasperreports.engine.JRDataSource;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
	public void exportToCSV() throws IOException {
        List<Request> listRequest = requestService.showAllPendingRequest();
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        FileWriter filePath = new FileWriter("./csv/"+ "REQUEST_"+ currentDateTime + ".csv", true);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(filePath, customCsvPreference());
        String[] csvHeader = {"Teknisi ID", "Request ID", "Merchant Name", "Address", "City", "PIC", "Created_date", "Status"};
        String[] nameMapping = {"teknisi_id", "request_id", "merchant_name", "address", "city","pic","created_date","status"};
        csvWriter.writeHeader(csvHeader);
        for (Request request : listRequest) {
            csvWriter.write(request, nameMapping);
        }
        csvWriter.close();
	}


	@Override
	public void exportToPDF() throws FileNotFoundException, JRException {
		ArrayList<Request> arrayListRequest =(ArrayList<Request>) requestService.getAllStatusRequest("FINISHED");
//		ArrayList<Request> arrayListRequest = new ArrayList <Request>();
		Object[] arrayObjectRequest = arrayListRequest.toArray();
		JRBeanArrayDataSource beanCollectionDataSource = new JRBeanArrayDataSource(arrayObjectRequest);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/FinishedRequestList.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
		JasperExportManager.exportReportToPdfFile(report, "./pdf/"+"FINISHED_REQUEST_"+ currentDateTime + ".pdf");
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportToXLS() throws IOException, JRException {

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

        DateFormat dateFileFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-MM-ss");
        String currentFileDateTime = dateFileFormatter.format(new Date());
        File outputFile = new File("./xls/"+ "REKAP_REQUEST_"+ currentFileDateTime + ".xls");
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             OutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            Exporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            byteArrayOutputStream.writeTo(fileOutputStream);
        }

    }


	@Override
	public void exportToPDFChart() throws FileNotFoundException, JRException {
//		ArrayList<Chart> arrayListRequest = new ArrayList<>();
		List<Chart> arrReq = requestDaoImpl.getAllRequestCount();
//		arrayListRequest.add(arrReq.get(0));
//		logger.info("tes DATASOURCE --------> {}", arrReq);
//		ArrayList<Request> arrayListRequest = new ArrayList <Request>();
//		Object[] arrayObjectRequest = arrayListRequest.toArray();
//		logger.info("tes DATASOURCE {}", arrayListRequest);
//		logger.info("tes DATASOURCE {}", arrayObjectRequest.length);
//		logger.info("tes DATASOURCE {}", arrayObjectRequest.getClass().getName());
		JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(arrReq);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("./jasper/RecapitulationChartBar.jrxml"));
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, -7);
        Date lastWeekDate = calender.getTime();    
        String lastWeekFriday = dateFormatter.format(lastWeekDate);
		JasperExportManager.exportReportToPdfFile(report, "./pdf/barChart/"+"REQUEST_"+lastWeekFriday+" - " +currentDateTime + ".pdf");
	}
	
	
	
	
}
