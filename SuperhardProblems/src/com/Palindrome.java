package com;

public class Palindrome {
    static boolean CheckIfPalindrome(String word){
        String CheckWord = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            CheckWord += word.charAt(i);
        }
        if(CheckWord.equals(word)){
            return true;
        } else {
            return false;
        }
    }
}
