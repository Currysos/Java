package onlineCom.UPG_A;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long numberOfChairs = input.nextLong();
        String chairs = input.next();
        input.close();

        char[] chairsArray = chairs.toCharArray();
        long minLeangth = numberOfChairs;
        long spaceCount = numberOfChairs;

        for (long i = 0; i < numberOfChairs; i++){
            System.out.println(spaceCount);
            if(chairsArray[(int) i] == '.') {
                if(spaceCount < minLeangth) {
                    minLeangth = spaceCount;
                }
                spaceCount = 0;
                continue;
            }
            spaceCount++;
        }

        System.out.println(minLeangth);
    }
}
