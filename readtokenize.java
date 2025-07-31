package assginment1;

import java.io.*;
import java.util.StringTokenizer;

public class readtokenize {

    public static void main(String[] args) {
        File inputFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/input.txt");
        File icFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/IC.TXT");
        File ltFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/LT.TXT");
        File stFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/ST.TXT");

        String[] AD = {"START", "END"};
        String[] IS = {"ADD", "SUB", "MULT", "DIV", "MOVER", "MOVEM"};
        String[] RG = {"AREG", "BREG", "CREG", "DREG"};
        String[] DL = {"DS", "DC"};

        int address = 0; 

        if (!inputFile.exists()) {
            System.out.println("input.txt does not exist. Please run CreateFiles first.");
            return;
        }

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

                    if (token.equals("START") && st.hasMoreTokens()) {
                        String nextToken = st.nextToken();
                        if (isNumeric(nextToken)) {
                            address = 100;
                            icWriter.write("(AD,01)");
                            icWriter.newLine();
                            icWriter.flush();
                            break;
                        }
                    }

                    if (!code.isEmpty()) {
                        icWriter.write(code);
                        icWriter.newLine();
                        icWriter.flush();
                    } else {
                        if (token.startsWith("=")) {
                            if (!isTokenInFile(ltFile, token)) {
                                int index = getNextIndex(ltFile);
                                appendToFileWithAddress(ltFile, index, token, address, true);
                                address++;
                            }
                        } else if (!isNumeric(token)) {
                            if (!isTokenInFile(stFile, token)) {
                                int index = getNextIndex(stFile);
                                appendToFileWithAddress(stFile, index, token, address, false);
                                address++;
                            }
                        }
                    }
                }
            }


            System.out.println("IC.TXT:");
            printFile(icFile);
            System.out.println("LT.TXT:");
            printFile(ltFile);
            System.out.println("ST.TXT:");
            printFile(stFile);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getCode(String token, String[] AD, String[] IS, String[] RG, String[] DL) {
        for (int i = 0; i < AD.length; i++) {
            if (token.equals(AD[i])) {
                return "(AD," + String.format("%02d", i + 1) + ")";
            }
        }
        for (int i = 0; i < IS.length; i++) {
            if (token.equals(IS[i])) {
                return "(IS," + i+1 + ")";
            }
        }
        for (int i = 0; i < RG.length; i++) {
            if (token.equals(RG[i])) {
                return "(RG," + String.format("%02d", i + 1) + ")";
            }
        }
        for (int i = 0; i < DL.length; i++) {
            if (token.equals(DL[i])) {
                return "(DL," + String.format("%02d", i + 1) + ")";
            }
        }
        return "";
    }

    public static boolean isTokenInFile(File file, String token) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(token)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // ignore
        }
        return false;
    }

    public static int getNextIndex(File file) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
          
        }
        return count;
    }

    public static void appendToFileWithAddress(File file, int index, String token, int address, boolean isLiteral) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isLiteral) {
                writer.write("(" + index + ",\"" + token + "\"," + address + ")");
            } else {
                writer.write("(" + index + "," + token + "," + address + ")");
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + file.getName());
            e.printStackTrace();
        }
    }

    public static void printFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------");
        } catch (IOException e) {
            System.out.println("Error reading " + file.getName());
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}

