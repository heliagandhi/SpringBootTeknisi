package com.teknisi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.supercsv.prefs.CsvPreference;

import net.sf.jasperreports.engine.JRException;

public interface FileService {
	File getLastModified(String path);
	CsvPreference customCsvPreference();
	void exportToCSV() throws IOException;
	void exportToPDF() throws FileNotFoundException, JRException;
	void exportToXLS() throws IOException;
}
