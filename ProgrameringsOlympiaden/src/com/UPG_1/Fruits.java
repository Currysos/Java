package com.UPG_1;

import java.util.Scanner;

public class Fruits {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Äpplen?");
        int apples = Integer.parseInt(input.next());
        System.out.println("Päron?");
        int pears = Integer.parseInt(input.next());

        input.close();

        int AxelRevenue = 7 * apples;
        int PetraRevenue = 13 * pears;

        if(AxelRevenue < PetraRevenue){
            System.out.println("Svar: Petra");
        }else if (AxelRevenue > PetraRevenue){
            System.out.println("Svar: Axel");
        }else{
            System.out.println("Svar: lika");
        }
    }
}
