package de.beckmann;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MergeCSVFiles {
    public static void main(String[] args) {
        String[] fileNames = {"file1.csv", "file2.csv", "file3.csv"}; // Add your CSV file names here
        String outputFileName = "merged.csv"; // Output file name

        Map<String, String> mergedData = new HashMap<>();

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String id = parts[0];
                        String data = parts[1];
                        mergedData.merge(id, data, (existingData, newData) -> existingData + "," + newData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (Map.Entry<String, String> entry : mergedData.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Merged data saved to " + outputFileName);
    }
}
