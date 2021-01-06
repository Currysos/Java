package com;

//Read file

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

//working with arrays and lists
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PronunciationWord {
    private String Word;
    private List<String> Pronunciation;

    public PronunciationWord(String word, List<String> pro) {
        Word = word;
        Pronunciation = pro;
    }

    public String getWord() {
        return Word;
    }
    public void setWord(String word) {
        Word = word;
    }

    public List<String> getPronunciation() {
        return Pronunciation;
    }
    public void setPronunciation(List<String> pronunciation) {
        Pronunciation = pronunciation;
    }

    public PronunciationWord Reverse(){
        StringBuilder reversedWord = new StringBuilder();
        for (int i = Word.length() - 1; i >= 0; i--) {
            reversedWord.append(Word.charAt(i));
        }

        List<String> reversedPro = new ArrayList<>(Collections.emptyList());
        for (int i = Pronunciation.size() - 1; i >= 0; i--){
            reversedPro.add(Pronunciation.get(i));
        }
        return new PronunciationWord(reversedWord.toString(), reversedPro);
    }
    public String write(){
        StringBuilder out = new StringBuilder(new StringBuilder(Word + " "));

        for (String currentPro:
             Pronunciation) {
            out.append(" ").append(currentPro);
        }

        return out.toString();
    }
    public PronunciationWord removeNumbers(){
        List<String> newPronunciation = new ArrayList<>(Collections.emptyList());

        for (String currentPronunciation : Pronunciation) {
            newPronunciation.add(currentPronunciation.replaceAll("[0-9]", ""));
        }
        return new PronunciationWord(Word, newPronunciation);
    }
}
class DataClass{
    private String Key;
    private String Value;
    private String DataType;

    public DataClass(String key, String value, String dataType) {
        Key = key;
        Value = value;
        DataType = dataType;
    }

    public String getKey() {
        return Key;
    }
    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }

    public String getDataType() {
        return DataType;
    }
    public void setDataType(String dataType) {
        DataType = dataType;
    }
}

public class PhoneticPalindrome {
    static boolean CheckIfPhoneticPalindrome(String word) {
        //Get relative path
        String rootPath = new File("").getAbsolutePath();
        String wordFilePath = rootPath.concat("\\src\\com\\EnglishWords.txt");

        //Get saved data
        String dataFilePath = rootPath.concat("\\src\\com\\Data.txt");
        DataClass[] DATA = GetData(dataFilePath);

        //Get the lines
        String[] LINES = ReadFileLines(Path.of(wordFilePath));

        //Create an array with pronunciation words
        PronunciationWord[] WORDS = new PronunciationWord[LINES.length];

        //Converting to Pronunciation words
        for (int i = 0; i < LINES.length; i++) {
            WORDS[i] = ConvertToProWord(LINES[i]);
        }

        //Split the words in input and create an array with prowords
        String[] words = word.split(" ");
        PronunciationWord[] PROWORDS = new PronunciationWord[words.length];

        //Find all the words
        for (int i = 0; i < words.length; i++) {
            PROWORDS[i] = GetProWord(words[i], WORDS);
            if(PROWORDS[i] == null){
                System.err.println("There is no such word as: " + words[i]);
                return false;
            }
            else{
                System.out.println("Found the word: " + words[i]);
            }
        }

        //Create an array with reversed prowords and add them
        PronunciationWord[] REVERSEDPROWORDS = new PronunciationWord[PROWORDS.length];
        int a = PROWORDS.length - 1;
        for (int i = 0; i < PROWORDS.length; i++){
            REVERSEDPROWORDS[i] = PROWORDS[a].Reverse();
            a--;
        }

        //Make words into one
        PronunciationWord PROWORD = new PronunciationWord("", Collections.emptyList());
        for (PronunciationWord currentProWord : PROWORDS){
            PROWORD.setWord(PROWORD.getWord() + currentProWord.getWord());
            PROWORD.setPronunciation(Stream.concat(PROWORD.getPronunciation().stream(), currentProWord.getPronunciation().stream()).collect(Collectors.toList()));
        }
        PronunciationWord REVERSEDPROWORD = new PronunciationWord("", Collections.emptyList());
        for (PronunciationWord currentProWord : REVERSEDPROWORDS){
            REVERSEDPROWORD.setWord(REVERSEDPROWORD.getWord() + currentProWord.getWord());
            REVERSEDPROWORD.setPronunciation(Stream.concat(REVERSEDPROWORD.getPronunciation().stream(), currentProWord.getPronunciation().stream()).collect(Collectors.toList()));
        }

        System.out.println("--------------------");
        //Check if word is palindrome or phonetic palindrome
        if (PROWORD.getWord().equals(REVERSEDPROWORD.getWord()) && PROWORD.getPronunciation().equals(REVERSEDPROWORD.getPronunciation())){
            System.out.println(PROWORD.write() + " :: is a palindrome and a 100% phonetic palindrome");
            return true;
        } else if (PROWORD.getPronunciation().equals(REVERSEDPROWORD.getPronunciation())){
            System.out.println(PROWORD.write() + " :: is a 100% phonetic palindrome");
            return true;
        } else if (PROWORD.getWord().equals(REVERSEDPROWORD.getWord())){
            System.out.println(PROWORD.write() + " :: is a palindrome but not a phonetic palindrome");
        }

        //take away numbers
        PronunciationWord STRIPPEDPROWORD = PROWORD.removeNumbers();
        PronunciationWord STRIPPEDREVERSEDPROWORD = REVERSEDPROWORD.removeNumbers();

        //Check again
        if (STRIPPEDPROWORD.getPronunciation().equals(STRIPPEDREVERSEDPROWORD.getPronunciation())){
            System.out.println(STRIPPEDPROWORD.write() + " :: is a phonetic palindrome if you have a broken accent");
            return true;
        }

        //Calculating points to determine output
        int points = 0;
        int maxPoints = 0;
        for (int i = 0; i < STRIPPEDPROWORD.getPronunciation().size(); i++){
            for (char currentChar : STRIPPEDPROWORD.getPronunciation().get(i).toCharArray()) {
                maxPoints += 1;
                if (STRIPPEDREVERSEDPROWORD.getPronunciation().get(i).contains(String.valueOf(currentChar))) {
                    points += 1;
                    continue;
                }

                if (i == 0) {
                    if (STRIPPEDREVERSEDPROWORD.getPronunciation().get(i + 1).contains(String.valueOf(currentChar))){
                        points += 1;
                    }
                }else if (i == STRIPPEDPROWORD.getPronunciation().size() - 1) {
                    if (STRIPPEDREVERSEDPROWORD.getPronunciation().get(i - 1).contains(String.valueOf(currentChar))){
                        points += 1;
                    }
                }else{
                    if(STRIPPEDREVERSEDPROWORD.getPronunciation().get(i - 1).contains(String.valueOf(currentChar)) ||
                            STRIPPEDREVERSEDPROWORD.getPronunciation().get(i + 1).contains(String.valueOf(currentChar))) {
                        points += 1;
                    }
                }
            }
        }
        System.out.println(STRIPPEDPROWORD.write());
        System.out.println(STRIPPEDREVERSEDPROWORD.write());
        System.out.println("Got " + Integer.toString(points) + " points out of " + Integer.toString(maxPoints) + " points");
        double percentage = (float) points / maxPoints;

        //Getting data from datafile
        String percentageBorderKey = "CURRENT_PERCENTAGE_BORDER";
        DataClass percentageBorderDataPack = FindDataWithKey(DATA, percentageBorderKey);

        //Checking if datafile got an answer. If not make it default
        if(percentageBorderDataPack == null){
            percentageBorderDataPack = new DataClass("DEFAULT", "0.5", "DOUBLE");
        }
        double percentageBorder = Double.parseDouble(percentageBorderDataPack.getValue());

        //Calculating outcome
        if (percentage >= percentageBorder) {
            System.out.println(word + " is probably a phonetic palindrome");
            return true;
        } else {
            System.out.println(word + " is probably NOT a phonetic palindrome");
            return false;
        }
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
    static PronunciationWord ConvertToProWord(String currentWord){
        String[] wordAndProSplit = currentWord.split("  ");
        String realWord = wordAndProSplit[0];
        String[] pros = wordAndProSplit[1].split(" ");

        return new PronunciationWord(realWord, Arrays.asList(pros));
    }
    static PronunciationWord GetProWord(String compareWord, PronunciationWord[] allWords){
        for (PronunciationWord currentWord : allWords) {
            if (currentWord.getWord().equals(compareWord)){
                return currentWord;
            }
        }
        return null;
    }

    static DataClass[] GetData(String dataPath){
        String[] dataLines = ReadFileLines(Path.of(dataPath));
        DataClass[] data = new DataClass[dataLines.length];

        for (int i = 0; i < data.length; i++) {
            String[] currentData = dataLines[i].split(":");
            data[i] = new DataClass(currentData[0], currentData[1], currentData[2]);
        }

        return data;
    }
    static DataClass FindDataWithKey(DataClass[] DATA, String key){
        for (DataClass currentData : DATA) {
            if(currentData.getKey().equals(key)){
                return currentData;
            }
        }
        return null;
    }
}


