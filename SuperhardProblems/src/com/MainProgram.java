package com;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainProgram {
    public static void main(String[] args){
        //CheckPalindromeTest();

        PhoneticPalindromeRunner();
        System.out.println("Exiting program");
    }

//    static void CheckPalindromeTest(){
//        Palindrome palindrome = new Palindrome();
//
//        boolean isPalindrome = palindrome.CheckIfPalindrome("apa");
//
//        System.out.println(isPalindrome);
//    }

    //A runner function for the Phonetic palindrome test
    static void PhoneticPalindromeRunner(){
        Scanner inputScanner = new Scanner(System.in);
        String wordToCheck = inputScanner.nextLine();

        if (wordToCheck.equals("")) {
            wordToCheck = "You're caught Talk Roy";
        }
        boolean result = PhoneticPalindrome.CheckIfPhoneticPalindrome(wordToCheck.toUpperCase());
        System.out.println(result);

        System.out.println("Was the answer right? \nType 'yes' or 'no'");
        String answer = inputScanner.nextLine();

        //If the program answered wrong according to the user
        if (answer.equals("no")){
            String rootPath = new File("").getAbsolutePath();
            //Get saved data
            String dataFilePath = rootPath.concat("\\src\\com\\Data.txt");
            DataClass[] DATA = GetData(dataFilePath);

            //Getting data from datafile
            String percentageBorderKey = "CURRENT_PERCENTAGE_BORDER";
            DataClass percentageBorderDataPack = FindDataWithKey(DATA, percentageBorderKey);

            assert percentageBorderDataPack != null;
            double currentPercentageBorder = Double.parseDouble(percentageBorderDataPack.getValue());

            if(result){
                currentPercentageBorder += 0.1;
            }else{
                currentPercentageBorder -= 0.1;
            }

            percentageBorderDataPack.setValue(String.valueOf(currentPercentageBorder));
            DATA = SetDataWithKey(DATA, percentageBorderKey, percentageBorderDataPack);

            SaveData(dataFilePath, DATA);
        }else{
            System.out.println("GREAT!");
        }

        inputScanner.close();
    }

    //Data handler functions
    static DataClass[] GetData(String dataPath){
        String[] dataLines = ReadFileLines(Path.of(dataPath));
        DataClass[] data = new DataClass[dataLines.length];

        for (int i = 0; i < data.length; i++) {
            String[] currentData = dataLines[i].split(":");
            data[i] = new DataClass(currentData[0], currentData[1], currentData[2]);
        }

        return data;
    }
    static String[] ReadFileLines (Path fileDir) {
        String[] LinesArr = new String[0];

        try (Stream<String> lines = Files.lines(fileDir, Charset.defaultCharset())) {
            LinesArr = lines.toArray(String[]::new);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return LinesArr;
    }
    static DataClass FindDataWithKey(DataClass[] DATA, String key){
        for (DataClass currentData : DATA) {
            if(currentData.getKey().equals(key)){
                return currentData;
            }
        }
        return null;
    }
    static DataClass[] SetDataWithKey (DataClass[] DATA, String key, DataClass newData){
        for (int i = 0; i < DATA.length; i++) {
            if (DATA[i].getKey().equals(key)) {
                DATA[i] = newData;
            }
        }
        return DATA;
    }
    static void SaveData(String dataPath, DataClass[] data){
        try {
            FileWriter myWriter = new FileWriter(dataPath);

            for (DataClass currentData : data) {
                String whatToWrite = currentData.getKey() + ":" + currentData.getValue() + ":" + currentData.getDataType();
                myWriter.write(whatToWrite);
            }

            myWriter.close();
            System.out.println("Successfully updated data.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
