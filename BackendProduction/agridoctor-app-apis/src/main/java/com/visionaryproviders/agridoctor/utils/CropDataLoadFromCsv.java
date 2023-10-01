package com.visionaryproviders.agridoctor.utils;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CropDataLoadFromCsv {

	
	private static final String CSV_FILE_PATH = "../csv/fertilizer.csv";

	    public List<CropData> loadCropData() throws IOException {
	        List<CropData> cropDataList = new ArrayList<>();

	        try (FileReader reader = new FileReader(CSV_FILE_PATH);
	             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

	            for (CSVRecord csvRecord : csvParser) {
	                CropData cropData = new CropData();
	                cropData.setId(Integer.parseInt(csvRecord.get("id")));
	                cropData.setCropName(csvRecord.get("cropName"));
	                cropData.setN(Integer.parseInt(csvRecord.get("n")));
	                cropData.setP(Integer.parseInt(csvRecord.get("p")));
	                cropData.setK(Integer.parseInt(csvRecord.get("k")));
	                cropData.setPH(Double.parseDouble(csvRecord.get("pH")));
	                cropData.setSoilMoisture(Integer.parseInt(csvRecord.get("soilMoisture")));

	                cropDataList.add(cropData);
	            }
	        }

	        return cropDataList;
	    } 
		
}
