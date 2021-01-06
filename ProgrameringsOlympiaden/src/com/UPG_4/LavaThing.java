package com.UPG_4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class LavaThing {

    public static void main(String[] args) {

        //Getting input
        Scanner input = new Scanner(System.in);

        System.out.println("N ?");
        int numberOfIslands = Integer.parseInt(input.next());

        System.out.println("K ?");
        int paddleDurability = Integer.parseInt(input.next());

        System.out.println("H ?");
        long hexaPrefix = Long.parseLong(input.next());

        long[] distances = new long[numberOfIslands - 1];

        for (int i = 0; i < numberOfIslands - 1; i++){
            System.out.println("d" + (i + 1) + " ?");
            distances[i] = Long.parseLong(input.next()) * hexaPrefix;
        }
        input.close();

        //starting real code



        //Find paddle count
//        long paddlesUsedForLastPitch = 0;
//        for (int i = distances.length - 1; i >= 0; --i){
//            long lastPitch = paddlesUsedForLastPitch * (paddleDurability - 1);
//            long rest = distances[i] - lastPitch;
//            long currentPaddlesCount = (long) Math.ceil((float)rest / paddleDurability);
//            //System.out.println(currentPaddlesCount);
//            if(rest > 0) {
//                paddlesUsedForLastPitch += currentPaddlesCount;
//            }
//        }


        //System.out.println("Svar: " + paddlesUsedForLastPitch);
    }
}
