package assignment1;

import java.io.*;
import java.util.StringTokenizer;

public class readtokenize {
    public static void main(String[] args) {
    
        File inputFile = new File("/home/spos/Desktop/assignment1/src/assignment1/input.txt");
        File icFile = new File("/home/spos/Desktop/assignment1/src/assignment1/input.txtIC.TXT");

        String AD[] = {"START", "END"};
        String IS[] = {"ADD", "SUB", "MULT", "DIV", "MOVER", "MOVEM"};
        String RG[] = {"AREG", "BREG", "CREG", "DREG"};
        String DL[] = {"DS", "DC"};

        if (!inputFile.exists()) {
            System.out.println("input.txt does not exist. Please run CreateFiles first.");
            return;
        }

        System.out.println("Reading and tokenizing input.txt using StringTokenizer...");

        try (
            BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter icWriter = new BufferedWriter(new FileWriter(icFile))
        ) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    String code = getCode(token, AD, IS, RG, DL);

                    if (!code.isEmpty()) {
                        icWriter.write(code);
                        icWriter.newLine();
                        icWriter.flush();
                        printICFile(icFile);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading input.txt or writing IC.TXT.");
            e.printStackTrace();
        }
    }

 
    public static String getCode(String token, String[] AD, String[] IS, String[] RG, String[] DL) {
        for (int i = 0; i < AD.length; i++) {
            if (token.equals(AD[i])) {
                return "(AD," + i+1+ ")";
            }
        }
        for (int i = 0; i < IS.length; i++) {
            if (token.equals(IS[i])) {
                return "(IS," + i + ")";
            }
        }
        for (int i = 0; i < RG.length; i++) {
            if (token.equals(RG[i])) {
                return "(RG," + i+1 + ")";
            }
        }
        for (int i = 0; i < DL.length; i++) {
            if (token.equals(DL[i])) {
                return "(DL," +i+1 + ")";
            }
        }
        return "";
    }

    public static void printICFile(File icFile) {
        System.out.println("Current contents of IC.TXT:");
        try (BufferedReader reader = new BufferedReader(new FileReader(icFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading IC.TXT");
            e.printStackTrace();
        }
    }
}

