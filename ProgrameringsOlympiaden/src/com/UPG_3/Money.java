package com.UPG_3;

import java.math.BigInteger;
import java.util.Scanner;

public class Money {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Priset N ?");
        String N = input.next();

        input.close();

        int maxNumberOfDigits = N.length();
        long price = Long.parseLong(N);
        int numberOfBills = 0;

        while (price > 0){
            long currentNumber = buildDigit(maxNumberOfDigits);
            while (price - currentNumber >= 0){
                price -= currentNumber;
                numberOfBills++;
            }
            maxNumberOfDigits--;
        }

        System.out.println(numberOfBills);
    }

    static long buildDigit(int count){
        String number = "1".repeat(Math.max(0, count));
        return Long.parseLong(number);

//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < count; i++){
//            builder.append("1");
//        }
//        return Long.parseLong(builder.toString());
    }
}
