package com;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game {
    static int gridSize;
    static snake SNAKE;
    static FrameInterface frameInterface;
    static Timer timer;

    static int speed;

    public static void main(String[] args) {
        gridSize = 50;
        speed = 250;
        init();
    }
    static void init(){
        SNAKE = new snake(gridSize / 2, gridSize / 2, 1);
        frameInterface = new FrameInterface(gridSize);
        //Start Sequence
        updateTimer(0);
    }

    static void keyWasPressed(KeyEvent e){
        switch (e.getKeyChar()){
            case 'a':
                SNAKE.rotate("LEFT");
                break;
            case 'd':
                SNAKE.rotate("RIGHT");
                break;
            case 'w':
                timer.cancel();
                updateTimer(-10);
                break;
            case 's':
                timer.cancel();
                updateTimer(10);
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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                game.update();
            }
        }, speed, speed);
    }

    static void endGame(String condition){
        timer.cancel();
        //Exit sequence
        if(condition.equals("LOSS")) {
            System.out.println("GAME OVER");

            int answer = JOptionPane.showConfirmDialog(frameInterface.frame, "GAME OVER" +
                    "\nScore: " + (SNAKE.getTailLength() + 1) + " out of " + (gridSize * gridSize) +
                    "\nPlay Again?");
            if (answer == JOptionPane.YES_OPTION) {
                //Play again
                resetGame();
            } else if (answer == JOptionPane.NO_OPTION) {
                //Close
                System.exit(0);
            }
        }else if (condition.equals("WIN")){
            System.out.println("GAME OVER, YOU WON");

            int answer = JOptionPane.showConfirmDialog(frameInterface.frame, "YOU WON" +
                    "\nYou got a full score of " + (gridSize * gridSize) +
                    "\nPlay Again?");
            if (answer == JOptionPane.YES_OPTION) {
                //Play again
                resetGame();
            } else if (answer == JOptionPane.NO_OPTION) {
                //Close
                System.exit(0);
            }
        }
    }

    static void resetGame(){
        System.out.println("Resetting game");
        frameInterface.frame.dispatchEvent(new WindowEvent(frameInterface.frame, WindowEvent.WINDOW_CLOSING));
        init();
    }

    static void update(){
        //clear cells
        frameInterface.clearCells();
        //Move snake
        SNAKE.moveSnake();
        if(SNAKE.getPosHorizontal() >= gridSize ||
                SNAKE.getPosVertical() >= gridSize ||
                SNAKE.getPosHorizontal() < 0 ||
                SNAKE.getPosVertical() < 0){
            endGame("LOSS");
        }

        //Ate fruit
        if(frameInterface.getCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical()).getContent().equals("FRUIT")){
            SNAKE.addTail = true;
            frameInterface.updateFruit();
        }

        //Update ui
        frameInterface.updateCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical(), "HEAD");
        for (int i = 0; i < SNAKE.getTailLength(); i++){
            frameInterface.updateCell(SNAKE.getTails().get(i).getPosHorizontal(), SNAKE.getTails().get(i).getPosVertical(), "TAIL");
        }

        //Hit itself
        if(frameInterface.getCell(SNAKE.getPosHorizontal(), SNAKE.getPosVertical()).getContent().equals("TAIL")) {
            endGame("LOSS");
        }

        //Got max points
        if(SNAKE.getTailLength() + 1 == gridSize * gridSize) {
            endGame("WIN");
        }
    }
}

class FrameInterface extends JFrame implements KeyListener {
    JFrame frame;
    int size;
    cell[][] cells;

    FrameInterface(int _size){
        size = _size;
        frame = new JFrame("Snake");
        frame.setSize(15 * size, 15 * size);
        frame.setLayout(new GridLayout(size, size));
        frame.setFocusable(true);
        frame.addKeyListener(this);

        cells = new cell[size][size];
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                JButton currentButton = new JButton();
                currentButton.setForeground(Color.GRAY);
                currentButton.setBackground(Color.BLACK);
                currentButton.setFocusPainted(false);
                frame.add(currentButton);
                cells[x][y] = new cell(currentButton);
            }
        }
        //fruit things
        updateFruit();

        frame.setVisible(true);
    }

    public void updateCell(int horizontalPos, int verticalPos, String _content){
        cells[verticalPos][horizontalPos].updateCell(_content);
    }
    public cell getCell(int horizontalPos, int verticalPos){
        return cells[verticalPos][horizontalPos];
    }

    public void clearCells(){
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                if(!cells[x][y].getContent().equals("FRUIT")) {
                    cells[x][y].updateCell("EMPTY");
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyWasPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateFruit(){
        ArrayList<cell> emptyCells = new ArrayList<>();
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                if(cells[x][y].getContent().equals("EMPTY")){
                    emptyCells.add(cells[x][y]);
                }
            }
        }
        Random rand = new Random();
        emptyCells.get(rand.nextInt(emptyCells.size())).updateCell("FRUIT");
    }
}
class cell {
    JButton cellBackground;
    String content;

    public cell(JButton cellBackground) {
        this.cellBackground = cellBackground;
        this.content = "EMPTY";
    }

    public String getContent() {
        return content;
    }

    public void updateCell(String _content){
        this.content = _content;
        switch (content) {
            case "HEAD":
                cellBackground.setBackground(Color.GREEN);
                break;
            case "TAIL":
                cellBackground.setBackground(Color.BLUE);
                break;
            case "FRUIT":
                cellBackground.setBackground(Color.RED);
                break;
            case "EMPTY":
                cellBackground.setBackground(Color.WHITE);
                break;
            default:
                break;

        }
    }
}
