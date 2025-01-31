import java.io.*;
import java.util.Scanner;

public class FileOperationsDemo {

    public static void main(String[] args) {
        // Define the file path
        String fileName = "example.txt";
        
        // Writing to the file
        writeToFile(fileName);
        
        // Reading from the file
        readFromFile(fileName);
        
        // Modifying the file by appending data
        modifyFile(fileName);
        
        // Reading the modified file
        System.out.println("\nReading the modified file:");
        readFromFile(fileName);
    }

    /**
     * This method demonstrates how to write data to a text file.
     * It will overwrite the file if it already exists.
     */
    public static void writeToFile(String fileName) {
        try {
            // Creating a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("This is the initial text in the file.");
            writer.newLine();  // Adds a new line
            writer.write("Welcome to file operations demo.");
            writer.close();  // Close the writer to save the content

            System.out.println("Successfully wrote to the file: " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    /**
     * This method demonstrates how to read data from a text file.
     
     */
    public static void readFromFile(String fileName) {
        try {
            // Creating a Scanner to read the file
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            
            System.out.println("\nReading from file: " + fileName);
            
            // Reading each line from the file
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);  // Print the line
            }
            
            reader.close();  // Close the reader
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * This method demonstrates how to modify a text file by appending new content.
     */
    public static void modifyFile(String fileName) {
        try {
            // Creating a BufferedWriter to append to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.newLine();  // Adds a new line before appending
            writer.write("This is the appended content in the file.");
            writer.close();  // Close the writer to save the changes

            System.out.println("\nSuccessfully modified the file by appending new content.");
        } catch (IOException e) {
            System.err.println("An error occurred while modifying the file.");
            e.printStackTrace();
        }
    }
}
