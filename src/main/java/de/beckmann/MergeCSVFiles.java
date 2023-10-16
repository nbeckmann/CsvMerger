package de.beckmann;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeCSVFiles {
    private static final String SEPARATOR = ";";
    private static final String LINEBREAK = "\n";

    public static void main(String[] args) {

        List<String> fileNames = fileNames(args);
        String outputFileName = outputFile(args);

        Map<String, String> mergedData = new HashMap<>();

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(SEPARATOR);
                    if (parts.length >= 2) {
                        String id = parts[0];
                        String data = parts[1];
                        mergedData.merge(id, data, (existingData, newData) -> existingData + SEPARATOR + newData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (Map.Entry<String, String> entry : mergedData.entrySet()) {
                writer.write(entry.getKey() + SEPARATOR + entry.getValue() + LINEBREAK);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Merged data saved to " + outputFileName);
    }

    private static List<String> fileNames(String[] args) {
        List<String> fileNames = Arrays.asList("C:\\Users\\nicob\\Documents\\result1.csv",
                "C:\\Users\\nicob\\Documents\\result2.csv", "C:\\Users\\nicob\\Documents\\result3.csv");

        return fileNames;
    }

    private static String outputFile(String[] args) {
        return "C:\\Users\\nicob\\Documents\\merged.csv";
    }
}
