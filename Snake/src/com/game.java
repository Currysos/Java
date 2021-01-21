package com;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class game {
    static int gridSizeHorizontal;
    static int gridSizeVertical;
    static snake SNAKE;
    static FrameInterface frameInterface;
    static Timer timer;

    static ai AI;

    static int speed;

    static final boolean useAI = false;
    static boolean gameIsRunning = true, showCellValues = false, superSpeed = false;

    //TODO make options panel
    public static void main(String[] args) {
        gridSizeHorizontal = 50;
        gridSizeVertical = 50;
        speed = 200;
        init();
    }
    static void init(){
        SNAKE = new snake(2, 0, 2);
        frameInterface = new FrameInterface(gridSizeHorizontal, gridSizeVertical);
        new hamiltonianCycle(gridSizeHorizontal, gridSizeVertical, frameInterface);
        if (useAI) { AI = new ai(); }

        //Start Sequence
        updateTimer(0);
        if(superSpeed){
            engageSuperSpeed();
        }

    }

    static void engageSuperSpeed() {
        timer.cancel();
        while (superSpeed) {
            AI.updateCycle();
            update();
        }
    }

    static void keyWasPressed(KeyEvent e){
        System.out.println("Pressed: " + e.getKeyChar());
        switch (e.getKeyChar()){
            case 'w':
                SNAKE.rotate(1);
                break;
            case 'd':
                SNAKE.rotate(2);
                break;
            case 's':
                SNAKE.rotate(3);
                break;
            case 'a':
                SNAKE.rotate(4);
                break;
            case 'r':
                timer.cancel();
                updateTimer(-10);
                break;
            case 'f':
                timer.cancel();
                updateTimer(10);
                break;
            case 'p':
                if(gameIsRunning) {
                    timer.cancel();
                    gameIsRunning = false;
                } else {
                    updateTimer(0);
                    gameIsRunning = true;
                }
                break;
            case 'c':
                showCellValues = !showCellValues;
                frameInterface.setShowCycleValues(showCellValues);
                break;
            case ' ':
                superSpeed = !superSpeed;
                if(superSpeed){
                    engageSuperSpeed();
                } else {
                    updateTimer(0);
                }
                break;
            default:
                break;
        }
    }

    static void updateTimer(int speedChange){
        speed += speedChange;
        if(speed < 1){
            speed = 1;
        }
        frameInterface.updateSpeedLabel(speed);
        System.out.println("Updated delay to: " + speed + "ms between updates");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                if (useAI) { AI.updateCycle(); }
                game.update();
            }
        }, speed, speed);
    }

    static void endGame(String condition){
        if(!superSpeed){
            timer.cancel();
        }
        superSpeed = false;
        //Exit sequence
        if(condition.equals("LOSS")) {
            System.out.println("GAME OVER");

            int answer = JOptionPane.showConfirmDialog(frameInterface.frame, "\nScore: " + (SNAKE.getTailLength() + 1) + " out of " + (gridSizeHorizontal * gridSizeVertical) +
                                "\nQuit?", "GAME OVER", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (condition.equals("WIN")) {
            System.out.println("GAME OVER, YOU WON");

            int answer = JOptionPane.showConfirmDialog(frameInterface.frame, "\nYou got a full score of " + (gridSizeHorizontal * gridSizeVertical) +
                    "\nQuit?", "YOU WON", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    static void update(){
        int[] lastTailPos = {SNAKE.getTails().get(SNAKE.getTailLength() - 1).getPosHorizontal(), SNAKE.getTails().get(SNAKE.getTailLength() - 1).getPosVertical()};
        int[] lastHeadPos = {SNAKE.getPosHorizontal(), SNAKE.getPosVertical()};

        //Move snake
        SNAKE.moveSnake();

        if(SNAKE.getPosHorizontal() >= gridSizeHorizontal ||
                SNAKE.getPosVertical() >= gridSizeVertical ||
                SNAKE.getPosHorizontal() < 0 ||
                SNAKE.getPosVertical() < 0){
            endGame("LOSS");
        }

        boolean ateFruit = false;
        //Ate fruit
        if(frameInterface.getCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical()).getContent().equals("FRUIT")){
            SNAKE.addTail = true;
            frameInterface.updateScoreLabel(SNAKE.getTailLength() + 2);
            ateFruit = true;

        }

        //Hit itself
        if(frameInterface.getCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical()).getContent().equals("TAIL")) {
            endGame("LOSS");
        }

        //Update ui
        frameInterface.updateCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical(), "HEAD");
        frameInterface.updateCell(lastHeadPos[0], lastHeadPos[1], "TAIL");
        frameInterface.updateCell(lastTailPos[0], lastTailPos[1], "EMPTY");

        //if ate fruit, spawn new fruit
        if(ateFruit) { frameInterface.updateFruit(); }

        //Got max points
        if(SNAKE.getTailLength() + 1 == gridSizeHorizontal * gridSizeVertical) {
            endGame("WIN");
        }
    }
}
