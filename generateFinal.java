package assginment1;

import java.io.*;

public class generateFinal {
    public static void main(String[] args) {
        File inputFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/input.txt");
        File icFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/IC.TXT");
        File stFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/ST.TXT");
        File ltFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/LT.TXT");
        File finalFile = new File("/home/mmcoe/eclipse-workspace/assginment1/src/assginment1/FINAL.TXT");

        try (
            BufferedReader icReader = new BufferedReader(new FileReader(icFile));
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter finalWriter = new BufferedWriter(new FileWriter(finalFile))
        ) {
            String icLine;
            String inputLine;

            int currentAddress = 0;

           
            String firstLine = inputReader.readLine();
            if (firstLine != null && firstLine.startsWith("START")) {
                String[] startParts = firstLine.trim().split("\\s+");
                if (startParts.length == 2 && isNumeric(startParts[1])) {
                    currentAddress = Integer.parseInt(startParts[1]);
                }
            }

            
            if ((icLine = icReader.readLine()) != null) {
                finalWriter.write(icLine + " " + currentAddress);
                finalWriter.newLine();
                currentAddress++;
            }

            
            while ((icLine = icReader.readLine()) != null && (inputLine = inputReader.readLine()) != null) {
                StringBuilder resultLine = new StringBuilder();
                resultLine.append(icLine); 

                String[] tokens = inputLine.trim().split("\\s+");
                for (String token : tokens) {
                    if (token.startsWith("=")) {
                        int index = findIndex(ltFile, token);
                        if (index != -1) {
                            resultLine.append("(L,").append(index).append(")");
                            break;
                        }
                    } else if (isAlpha(token)) {
                        int index = findIndex(stFile, token);
                        if (index != -1) {
                            resultLine.append("(S,").append(index).append(")");
                            break;
                        }
                    }
                }

                resultLine.append(" ").append(currentAddress);
                currentAddress++;

                finalWriter.write(resultLine.toString());
                finalWriter.newLine();
            }

            System.out.println("FINAL.TXT generated with address counting from START.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int findIndex(File file, String token) {
        int index = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                index++;
                line = line.replaceAll("[()\"]", ""); // remove brackets and quotes
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(token)) {
                    return index;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + file.getName());
        }
        return -1;
    }

    public static boolean isAlpha(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
