package com;

public class Cubes {
    public static void main(String[] args) {
        int answer = NumberOfSmallCubes(7);
        System.out.println(answer);
    }

    static int NumberOfSmallCubes(int number) {
        int sum = 0;
        for (int i = 1; i <= number; i++) {
            sum += Math.pow(i, 3);
        }
        return sum;
    }
}
