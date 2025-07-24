package assignment1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class assignment1 {
    public static void main(String[] args) {
        try {
            String directoryPath = "/home/spos/Desktop/assignment1/src/assignment1/";
            
       
            File inputFile = new File(directoryPath + "input.txt");
            if (inputFile.createNewFile()) {
                System.out.println("File created: " + inputFile.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + inputFile.getAbsolutePath());
            }

            try (PrintWriter writer = new PrintWriter(inputFile)) {
                writer.println("START 100");
                writer.println("ADD AREG X");
                writer.println("SUB BREG =2");
                writer.println("X DC 2");
                writer.println("END");
            }


            File f1 = new File(directoryPath + "ST.TXT");
            File f2 = new File(directoryPath + "LT.TXT");
            File f3 = new File(directoryPath + "IC.TXT");

            if (f1.createNewFile()) {
                System.out.println("File created: " + f1.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + f1.getAbsolutePath());
            }

            if (f2.createNewFile()) {
                System.out.println("File created: " + f2.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + f2.getAbsolutePath());
            }

            if (f3.createNewFile()) {
                System.out.println("File created: " + f3.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + f3.getAbsolutePath());
            }

        } catch (IOException e) {
            System.out.println("An error occurred during file creation.");
            e.printStackTrace();
        }
    }
}
