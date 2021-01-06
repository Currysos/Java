package com;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Renovering {
    public static void main(String[] args) {
        //Get in data
        Scanner input = new Scanner(System.in);
        System.out.println("Behöver antal?");
        int needed = Integer.parseInt(input.next());
        List<Integer> neededLengths = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < needed; i++){
            System.out.println("Behöver längd (" + (i + 1) + ") ?");
            neededLengths.add(Integer.parseInt(input.next()));
        }

        System.out.println("Har antal?");
        int have = Integer.parseInt(input.next());
        int[] haveLengths = new int[have];
        for (int i = 0; i < have; i++){
            System.out.println("Har längd (" + (i + 1) + ") ?");
            haveLengths[i] = Integer.parseInt(input.next());
        }
        Arrays.sort(haveLengths);
        input.close();

        int willNeedToBuyCount = needed - have;

        for (int currentLeangth:
             haveLengths) {

        }
    }

    static void PrintArray(int[] arr){
        for (int number:
             arr) {
            System.out.println(number);
        }
    }
}
