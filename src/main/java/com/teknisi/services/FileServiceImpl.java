package com.teknisi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@Service
public class FileServiceImpl implements FileService{
	@Autowired RequestService requestService;
	
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

	@SuppressWarnings("deprecation")
	@Override
	public void exportToXLS() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("Recapitulation");
		sheet.setDefaultColumnWidth(20);
		sheet.setColumnWidth(0, 5);;
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(font);
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		String[] xlsHeader = {"No", "Tanggal Process", "Request ID", "Merchant Name", "Address", "City", "PIC", "Teknisi ID","Teknisi Name", "Status"};
	    
		XSSFRow headlineHeader = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress( 0, 0, 1, 9 ));
		XSSFCell headlineCell = headlineHeader.createCell(1);
		headlineCell.setCellValue("Rekapitulasi Data Request");
		headlineCell.setCellStyle(headerStyle);

		XSSFRow dateHeader = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress( 1, 1, 1, 9 ));
		XSSFCell  dateCell = dateHeader.createCell(1);
		DateFormat dateXLSFormatter = new SimpleDateFormat("dd/MM/yyyy_hh:mm:ss");
        String currentXLSDateTime = dateXLSFormatter.format(new Date());
        dateCell.setCellValue("Report Date: " + currentXLSDateTime);
        dateCell.setCellStyle(headerStyle);
        

        
		XSSFCellStyle columnTitle = workbook.createCellStyle();
		columnTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		columnTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		columnTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		columnTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		columnTitle.setFont(font);
		columnTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		columnTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        
        XSSFRow dataHeader = sheet.createRow(3);
        for(int i = 0; i < xlsHeader.length; i++) {
        	Cell nameCell = dataHeader.createCell(i);
        	nameCell.setCellValue(xlsHeader[i]);
        	nameCell.setCellStyle(columnTitle);
        }
        
        XSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setWrapText(true);
        contentStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        
        List<Request> listRequest = requestService.showAllRecapitulationRequest();
        int index = 0;
        for (Request request : listRequest) {
        	Row row = sheet.createRow(4+index);
        	XSSFCell  columnOne = (XSSFCell) row.createCell(0);
        	XSSFCell  columnTwo = (XSSFCell) row.createCell(1);
        	XSSFCell  columnThree = (XSSFCell) row.createCell(2);
        	XSSFCell  columnFour = (XSSFCell) row.createCell(3);
        	XSSFCell  columnFive = (XSSFCell) row.createCell(4);
        	XSSFCell  columnSix = (XSSFCell) row.createCell(5);
        	XSSFCell  columnSeven = (XSSFCell) row.createCell(6);
        	XSSFCell  columnEight = (XSSFCell) row.createCell(7);
        	XSSFCell  columnNine = (XSSFCell) row.createCell(8);
        	XSSFCell  columnTen = (XSSFCell) row.createCell(9);
        	
        	columnOne.setCellValue(index+1);
        	if(request.getUpdate_date() == null) {
        		columnTwo.setCellValue(new SimpleDateFormat("MM/dd/yyyy  hh:mm:ss").format(request.getCreated_date()));
        	}else {
        		columnTwo.setCellValue(new SimpleDateFormat("MM/dd/yyyy  hh:mm:ss").format(request.getUpdate_date()));
        	}
        	columnThree.setCellValue(request.getRequest_id());
        	columnFour.setCellValue(request.getMerchant_name());
        	columnFive.setCellValue(request.getAddress());
        	columnSix.setCellValue(request.getCity());
        	columnSeven.setCellValue(request.getPic());
        	columnEight.setCellValue(request.getTeknisi().getId());
        	columnNine.setCellValue(request.getTeknisi().getName());
        	columnTen.setCellValue(request.getStatus());
        	
        	columnOne.setCellStyle(contentStyle);
        	columnTwo.setCellStyle(contentStyle);
        	columnThree.setCellStyle(contentStyle);
        	columnFour.setCellStyle(contentStyle);
        	columnFive.setCellStyle(contentStyle);
        	columnSix.setCellStyle(contentStyle);
        	columnSeven.setCellStyle(contentStyle);
        	columnEight.setCellStyle(contentStyle);
        	columnNine.setCellStyle(contentStyle);
        	columnTen.setCellStyle(contentStyle);
        	index++;
        }
        DateFormat dateFileFormatter = new SimpleDateFormat("dd-MM-yyyy_hh-MM-ss");
        String currentFileDateTime = dateFileFormatter.format(new Date());
        File file = new File("./xls/"+ "REKAP_REQUEST_"+ currentFileDateTime + ".xls");
        String filePath = file.getAbsolutePath();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();
    }
	
}
