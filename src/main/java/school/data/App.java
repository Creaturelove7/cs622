package school.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class App 
{
	private static final Map<String, LocalDateTime> memory = new HashMap<>();
	 private static final Map<String, Integer> keywordCount = new HashMap<>(); // For counting the frequency

	public static void main(String[] args) {
//		// Define the name of output
//        String outputFile = "merged.json";
//        // Define the name of input
//        String[] inputFiles = {"D:\\Database\\1.json", "D:\\Database\\2.json", "D:\\Database\\3.json"};
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
//            writer.write("["); // the start [
//
//            boolean first = true;
//
//            for (String inputFile : inputFiles) {
//                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
//                    // Read every line and add it in to new file
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        if (!first) {
//                            writer.write(","); // add ","
//                        } else {
//                            first = false;
//                        }
//                        writer.newLine(); // Add new line
//                        writer.write(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            writer.newLine();
//            writer.write("]"); // The end ]
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        

		
		
       //Quick and comprehensive output version with Jackson
		File jsonFile = new File("D:\\Database\\merged.json");
        ArrayList<String> input = new ArrayList<>();
        input.add("creator");
        input.add("baseball");
		for(String keyword: input) {
			//Add key and time stamp to memory
			storeSearchKeyword(keyword);
			try {
	            JsonFactory factory = new JsonFactory();
	            JsonParser parser = factory.createParser(jsonFile);

	            String name = "";
	            String category = "";
	            int fundingAmount = 0;
	            boolean isMatched = false;

	            while (!parser.isClosed()) {
	            	
	                JsonToken jsonToken = parser.nextToken();

	                if (jsonToken == null) break;

	                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
	                    String fieldName = parser.getCurrentName();
	                    jsonToken = parser.nextToken();

	                    switch (fieldName) {
	                        case "name":
	                            name = parser.getText();
	                            if (name.contains(keyword)) isMatched = true;
	                            break;
	                        case "category":
	                            category = parser.getText();
	                            if (category.contains(keyword)) isMatched = true;
	                            break;
	                        case "fundingAmount":
	                            fundingAmount = parser.getIntValue();
	                            break;
	                    }
	                }

	                if (jsonToken.isStructEnd()) {
	                    if (isMatched) {
	                    	
	                        System.out.println("Project Name: " + name);
	                        System.out.println("Category: " + category);
	                        System.out.println("Funding Amount: " + fundingAmount);
	                        System.out.println();
	                        isMatched = false;
	                    }
	                    name = "";
	                    category = "";
	                    fundingAmount = 0;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
		// Output the memory to the console
		outputMemory();
		// Output the keyword search frequency
        outputKeywordFrequency();
	}
	//Store memory function
	 private static void storeSearchKeyword(String keyword) {
	        memory.put(keyword, LocalDateTime.now());
	        keywordCount.put(keyword, keywordCount.getOrDefault(keyword, 0) + 1); // update the count
	    }
	 //Output memory function
	 private static void outputMemory() {
	        for (Map.Entry<String, LocalDateTime> entry : memory.entrySet()) {
	            System.out.println("Keyword: " + entry.getKey() + ", Timestamp: " + entry.getValue());
	        }
	    }
	// New method to output keyword frequency
	    private static void outputKeywordFrequency() {
	        for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
	            System.out.println("Keyword: " + entry.getKey() + ", Searched: " + entry.getValue() + " times");
	        }
	    }
}









