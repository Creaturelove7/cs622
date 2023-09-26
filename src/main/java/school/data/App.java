package school.data;

import java.io.*;

public class App 
{
	public static void main(String[] args) {
		// Define the name of output
        String outputFile = "merged.json";
        // Define the name of input
        String[] inputFiles = {"1.json", "2.json", "3.json"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("["); // the start [

            boolean first = true;

            for (String inputFile : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    // Read every line and add it in to new file
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!first) {
                            writer.write(","); // add ","
                        } else {
                            first = false;
                        }
                        writer.newLine(); // Add new line
                        writer.write(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            writer.newLine();
            writer.write("]"); // The end ]
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     // The key to search for in the JSON file
        String targetKey = "name";
        // The name of the JSON file to search
        String fileName = "merged.json";

        // Open the file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Search for the key in the current line and print its value
                int index = line.indexOf("\"" + targetKey + "\"");
                if (index != -1) {
                    int colonIndex = line.indexOf(":", index);
                    int commaIndex = line.indexOf(",", colonIndex);
                    int endIndex = (commaIndex != -1) ? commaIndex : line.indexOf("}", colonIndex);
                    if (colonIndex != -1 && endIndex != -1) {
                        String value = line.substring(colonIndex + 1, endIndex).trim();
                        System.out.println(targetKey + ": " + value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
}









