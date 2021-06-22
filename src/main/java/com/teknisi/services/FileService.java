package com.teknisi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.supercsv.prefs.CsvPreference;

import net.sf.jasperreports.engine.JRException;

public interface FileService {
	File getLastModified(String path);
	CsvPreference customCsvPreference();
	byte[] exportToCSV() throws IOException;
	byte[] exportToPDF() throws FileNotFoundException, IOException, JRException, MessagingException;
	byte[] exportToXLS() throws IOException, JRException;
	byte[] exportToPDFChart() throws FileNotFoundException, JRException, IOException;
}
