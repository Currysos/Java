package com;
import java.util.Arrays;

public class StudsChiffer {
    public static final String message = "Programeringslektion";
    public static final int N = 10, M = 16;
    public static final int R = 0, C = 1;

    public static void main(String[] args) {
        String encryptedMessage = Encrypt(N, M);
        System.out.println("Message: " + message);
        System.out.println("Encrypted message: " + encryptedMessage);
    }

    //Todo change to string
    static String Encrypt(int N, int M){
        char[][] grid = InitializeArray(N, M);

        int[] currentPosition = {0, 0};
        int[] direction = {1, 1};

        grid[currentPosition[R]][currentPosition[C]] = StudsChiffer.message.toCharArray()[0];
        char[] messageChars = StudsChiffer.message.substring(1).toCharArray();
        for (int i = 0; i < messageChars.length; i++) {
            if (currentPosition[R] == 0){
                direction[R] = 1;
            }
            if (currentPosition[R] == N - 1){
                direction[R] = -1;
            }
            if (currentPosition[C] == 0){
                direction[C] = 1;
            }
            if (currentPosition[C] == M - 1){
                direction[C] = -1;
            }

            int[] nextPosition = {currentPosition[R] + direction[R],currentPosition[C] + direction[C]};
            if (grid[nextPosition[R]][nextPosition[C]] == '¤'){
                grid[nextPosition[R]][nextPosition[C]] = messageChars[i];
                currentPosition = nextPosition;
            } else {
                currentPosition = nextPosition;
                i--;
            }
        }
        PrintGrid(grid);

        return ReadGrid(grid);
    }
    static char[][] InitializeArray(int n, int m){
        char[][] grid = new char[n][m];
        for (char[] row : grid) {
            Arrays.fill(row, '¤');
        }
        return grid;
    }
    static void PrintGrid(char[][] grid){
        for (int n = 0; n < grid.length; n++){
            for (int m = 0; m < grid[n].length; m++){
                System.out.print(String.valueOf(grid[n][m]) + "  ");
            }
            System.out.println("");
        }
    }
    static String ReadGrid(char[][] grid){
        StringBuilder encryptedMessage = new StringBuilder();
        for (int n = 0; n < grid.length; n++){
            for (int m = 0; m < grid[n].length; m++){
                if (grid[n][m] != '¤'){
                    encryptedMessage.append(grid[n][m]);
                }
            }
        }

        return encryptedMessage.toString();
    }
}
