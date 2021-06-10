package com.teknisi.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.teknisi.model.Request;

@Service
public class FileServiceImpl implements FileService{
	@Autowired RequestService requestService;
	
	@Override
	public File getLastModified() {
		 File directory = new File("./csv");
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
	
}
