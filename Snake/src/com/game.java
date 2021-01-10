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
    static int gridSizeHorizontal;
    static int gridSizeVertical;
    static snake SNAKE;
    static FrameInterface frameInterface;
    static Timer timer;

    static int speed;

    public static void main(String[] args) {
        gridSizeHorizontal = 70;
        gridSizeVertical = 50;
        speed = 200;
        init();
    }
    static void init(){
        SNAKE = new snake(gridSizeHorizontal / 2, gridSizeVertical - 1, 1);
        frameInterface = new FrameInterface(gridSizeHorizontal, gridSizeVertical);
        //Start Sequence
        updateTimer(0);
    }

    static void keyWasPressed(KeyEvent e){
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
                    "\nScore: " + (SNAKE.getTailLength() + 1) + " out of " + (gridSizeHorizontal * gridSizeVertical) +
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
                    "\nYou got a full score of " + (gridSizeHorizontal * gridSizeVertical) +
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
        frameInterface.closeFrame();
        init();
    }

    static void update(){
        //clear cells
        frameInterface.clearCells();
        //Move snake
        SNAKE.moveSnake();
        if(SNAKE.getPosHorizontal() >= gridSizeHorizontal ||
                SNAKE.getPosVertical() >= gridSizeVertical ||
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
        if(SNAKE.getTailLength() + 1 == gridSizeHorizontal * gridSizeVertical) {
            endGame("WIN");
        }
    }
}

class FrameInterface extends JFrame implements KeyListener {
    JFrame frame;
    int sizeHorizontal;
    int sizeVertical;
    cell[][] cells;

    FrameInterface(int _sizeHorizontal, int _sizeVertical){
        sizeHorizontal = _sizeHorizontal;
        sizeVertical = _sizeVertical;
        frame = new JFrame("Snake");
        frame.setSize(25 * sizeHorizontal, 25 * sizeVertical);
        frame.setLayout(new GridLayout(sizeVertical, sizeHorizontal, 1, 1));
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cells = new cell[sizeVertical][sizeHorizontal];
        for (int v = 0; v < sizeVertical; v++){
            for (int h = 0; h < sizeHorizontal; h++){
                JLabel currentLabel = new JLabel();
                currentLabel.setBackground(Color.BLACK);
                currentLabel.setOpaque(true);
                frame.add(currentLabel);
                cells[v][h] = new cell(currentLabel);
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
        for (int v = 0; v < sizeVertical; v++){
            for (int h = 0; h < sizeHorizontal; h++){
                if(!cells[v][h].getContent().equals("FRUIT")) {
                    cells[v][h].updateCell("EMPTY");
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
        for (int v = 0; v < sizeVertical; v++){
            for (int h = 0; h < sizeHorizontal; h++){
                if(cells[v][h].getContent().equals("EMPTY")){
                    emptyCells.add(cells[v][h]);
                }
            }
        }
        Random rand = new Random();
        emptyCells.get(rand.nextInt(emptyCells.size())).updateCell("FRUIT");
    }

    public void closeFrame(){
        frame.setVisible(false);
    }
}
class cell {
    JLabel cellBackground;
    String content;

    public cell(JLabel cellBackground) {
        this.cellBackground = cellBackground;
        this.content = "EMPTY";
    }

    public String getContent() {
        return content;
    }

    public void updateCell(String _content){
        this.content = _content;
        switch (content) {
            case "TAIL":
                cellBackground.setBackground(Color.getHSBColor((float) 200 / 360, 1, 1));
                break;
            case "HEAD":
                cellBackground.setBackground(Color.getHSBColor((float) 180 / 360, 1, 1));
                break;
            case "FRUIT":
                cellBackground.setBackground(Color.getHSBColor((float) 20 / 360, 1, 1));
                break;
            case "EMPTY":
                cellBackground.setBackground(Color.getHSBColor((float) 80 / 360, 1, 1));
                break;
            default:
                break;

        }
    }
}
