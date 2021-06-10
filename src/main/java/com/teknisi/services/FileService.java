package com.teknisi.services;

import java.io.File;
import java.io.IOException;

import org.supercsv.prefs.CsvPreference;

public interface FileService {
	File getLastModified();
	CsvPreference customCsvPreference();
	void exportToCSV() throws IOException;
}
