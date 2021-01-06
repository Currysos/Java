package com.UPG_2;

import java.util.Scanner;

public class PersonalNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Ditt personnummer?");
        String personalNumber = input.next();

        input.close();

        String[] personalNumberDevided = buildStringArray(personalNumber);
        boolean over100;
        over100 = personalNumberDevided[3].equals("+");
        int year = Integer.parseInt(personalNumberDevided[0]);
        if(over100){
            if(year > 20){
                personalNumberDevided[0] = "18" + year;
            } else {
                personalNumberDevided[0] = "19" + year;
            }
        } else {
            if(year > 20){
                personalNumberDevided[0] = "19" + year;
            }else{
                personalNumberDevided[0] = "20" + year;
            }
        }

        String bigPersonalNumber = buildString(personalNumberDevided);

        System.out.println("Svar: " + bigPersonalNumber);
    }

    static String[] buildStringArray(String number){
        char[] numberChars = number.toCharArray();

        String type = String.valueOf(numberChars[6]);

        String lastNumbers = String.valueOf(numberChars[7]) +
                numberChars[8] +
                numberChars[9] +
                numberChars[10];
        String day = String.valueOf(numberChars[4]) +
                (numberChars[5]);
        String month = String.valueOf(numberChars[2]) +
                numberChars[3];
        String year = String.valueOf(numberChars[0]) +
                numberChars[1];
        return new String[]{year, month, day, type, lastNumbers};
    }

    static String buildString(String[] arr){
        StringBuilder out = new StringBuilder();
        for (String str:
             arr) {
            if(!str.equals("+") && !str.equals("-")){
                out.append(str);
            }
        }

        return out.toString();
    }
}
