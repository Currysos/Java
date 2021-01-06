package com.UPG_5;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Clock {
    static int[] numberValues = {6,2,5,5,4,5,6,3,7,6};
    static long iterationCounter = 0;
    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        System.out.println("Elförbrukning N ?");
        long N = Long.parseLong(input.next());

        String clock = "000000";
        int cases = 0;
        int[] clockEnergyCases = new int[86400];

        //Get all energy levels
        for (int i = 0; i < 86400; i++){
            clock = SplitToComponentTimes(i);
            clockEnergyCases[i] = GetEnergyFromClock(clock);

            iterationCounter++;
        }

        for (int i = 0; i < clockEnergyCases.length; i++){
            int currentEnergy = 0;
            for (int x = i; x < clockEnergyCases.length; x++){
                iterationCounter++;
                currentEnergy += clockEnergyCases[x];

                if(currentEnergy == N){
                    cases++;
                    break;
                }

                if(currentEnergy > N){
                    break;
                }
            }
        }

        System.out.println(cases);
        System.out.println("Iterations : " + iterationCounter);
    }

    static String SplitToComponentTimes(int totalSecs)
    {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d%02d%02d", hours, minutes, seconds);
    }

    static int GetEnergyFromClock (String clock){
        int energy = 0;

        for (char currentSegment:
             clock.toCharArray()) {
            energy += numberValues[Character.getNumericValue(currentSegment)];
        }

        return energy;
    }

    static void PrintArray(String[] arr){
        for (String str:
             arr) {
            System.out.println(str);
        }
    }
}
