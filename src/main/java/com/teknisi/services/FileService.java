package com.teknisi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.supercsv.prefs.CsvPreference;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface FileService {
	File getLastModified(String path);
	CsvPreference customCsvPreference();
	byte[] exportToCSV() throws IOException;
	byte[] exportToPDF() throws FileNotFoundException, IOException, JRException, MessagingException;
	byte[] exportToXLS() throws IOException, JRException;
	byte[] exportToPDFChart() throws FileNotFoundException, JRException, IOException;
	byte[] exportPDF(Date start_date, Date end_date) throws IOException, JRException;
}
