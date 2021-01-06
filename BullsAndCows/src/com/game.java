package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class game {
    static JTextField playerInput;
    static JTextArea playerOutput;
    static JTextArea gameOutput;
    static JFrame FRAME;
    static String hiddenNumber;
    static int guesses = 0;

    public static void main(String[] args) {
        FRAME = setupFrame();
        FRAME.setVisible(true);
        hiddenNumber = buildStringNumber();
        System.out.println(hiddenNumber);

        System.out.println(playerInput.getText());
    }

    static void pressedGuess(){
        guesses++;
        System.out.println("Going to next part of game");
        String yourGuess = playerInput.getText();
        System.out.println("Guess: " + yourGuess);

        char[] guessChars = yourGuess.toCharArray();
        char[] hiddenNumberChars = hiddenNumber.toCharArray();

        int numberOfBulls = 0;
        int numberOfCows = 0;

        //Checking for bulls and cows
        for (int i = 0; i < guessChars.length; i++){
            if(guessChars[i] == hiddenNumberChars[i]){
                numberOfBulls++;
            }else{
                if(contains(hiddenNumberChars, guessChars[i])){
                    numberOfCows++;
                }
            }
        }

        String outText = "Bulls: " + numberOfBulls + ", Cows: " + numberOfCows;

        //Updating UI
        playerOutput.setText(playerOutput.getText() + yourGuess + "\n");
        gameOutput.setText(gameOutput.getText() + outText + "\n");

        //Checking if guess was correct
        if(yourGuess.equals(hiddenNumber)) {
            //Game has ended
            endGame();
        }
    }

    static void endGame(){
        System.out.println("You won!!");

        int answer = JOptionPane.showConfirmDialog(FRAME, "YOU WON!!\nScore: " + guesses + "\nPlay Again?");
        if(answer == JOptionPane.YES_OPTION){
            //Play again
            resetGame();
        } else if (answer == JOptionPane.NO_OPTION){
            //Close
            System.exit(0);
        }
    }

    static void resetGame(){
        gameOutput.setText("");
        playerInput.setText("Your guess here");
        playerOutput.setText("");
        guesses = 0;
        hiddenNumber = buildStringNumber();
        System.out.flush();
    }

    static JFrame setupFrame(){
        JFrame f = new JFrame();

        playerInput = new JTextField("Your guess here");
        f.add(playerInput, BorderLayout.NORTH);


        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pressedGuess();
            }
        });
        f.add(guessButton, BorderLayout.SOUTH);

        gameOutput = new JTextArea();
        f.add(gameOutput, BorderLayout.CENTER);

        playerOutput = new JTextArea();
        f.add(playerOutput, BorderLayout.EAST);


        f.setSize(400, 500);
        return f;
    }
    static String buildStringNumber(){
        Random rn = new Random();

        int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        //Shuffle array
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rn.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++){
            sb.append(array[i]);
        }
        return String.valueOf(sb);
    }
    static boolean contains(final char[] array, final char v) {
        boolean result = false;
        for(char i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }
}
