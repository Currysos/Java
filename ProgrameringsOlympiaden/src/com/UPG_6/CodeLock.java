package com.UPG_6;

import java.util.Scanner;

public class CodeLock {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Antal skivor N ?");
        int N = Integer.parseInt(input.next());

        System.out.println("Antal segmen M ?");
        int M = Integer.parseInt(input.next());

        String[] disks = new String[N];
        for (int i = 0; i < N; i++){
            System.out.println("Skiva " + i + " ?");
            disks[i] = input.next();
        }

        int possibleUnlocks = 0;
        for (int i = 0; i < N; i++){


        }
        System.out.println(possibleUnlocks);
    }

    static String RotateDisk (String disk){
        char[] chars = disk.toCharArray();
        int diskLength = chars.length;

        StringBuilder out = new StringBuilder();
        out.append(chars[diskLength - 1]);
        for (int i = 0; i < diskLength - 1; i++){
            out.append(chars[i]);
        }
        return out.toString();
    }

    static boolean CheckIfUnlocked (String[] arr){
        for (int i = 0; i < arr[0].toCharArray().length; i++){
            int points = 0;
            for (int x = 0; x < arr.length - 1; ++x){
                if(arr[x].toCharArray()[i] == '.'){
                    points++;
                }
            }

            if(points == arr.length - 1){
                return true;
            }
        }
        
        return false;
    }
}
