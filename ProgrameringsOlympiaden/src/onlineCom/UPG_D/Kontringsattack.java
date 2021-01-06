package onlineCom.UPG_D;

import java.util.Scanner;

public class Kontringsattack {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long numberOfChairs = input.nextLong();
        String chairs = input.next();
        input.close();

        char[] chairsArray = chairs.toCharArray();
        long minLeangth = numberOfChairs;
        boolean hasFoundChair = false;
        long spaceCount = 0;

        for (long i = 0; i < numberOfChairs; i++){
            if(chairsArray[(int) i] == '.' && !hasFoundChair){
                hasFoundChair = true;
                spaceCount = 0;
            } else if (chairsArray[(int) i] == '.' && hasFoundChair) {
                if(spaceCount < minLeangth) {
                    minLeangth = spaceCount;
                }
                spaceCount = 0;
            }
            spaceCount++;
        }

        System.out.println(minLeangth);
    }
}
