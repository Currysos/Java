package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class FrameInterface extends JFrame implements KeyListener {
    JFrame frame;
    JPanel gamePanel, UIPanel;
    JLabel scoreLabel, currentControlModeLabel, speedLabel;
    int sizeHorizontal;
    int sizeVertical;
    cell[][] cells;
    cell fruitCell;

    FrameInterface(int _sizeHorizontal, int _sizeVertical){
        sizeHorizontal = _sizeHorizontal;
        sizeVertical = _sizeVertical;

        //------frame------
        frame = new JFrame("Snake");
        frame.setSize((int) (800 * (1.0f * sizeHorizontal/sizeVertical)), 800);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        //------Game panel------
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(sizeVertical, sizeHorizontal, 1, 1));

        //------UI Panel------
        UIPanel = new JPanel();
        UIPanel.setLayout(new GridLayout(1, 3));

        //------Text labels------
        scoreLabel = new JLabel("Score: " + game.SNAKE.getTailLength() + 1);
        scoreLabel.setHorizontalAlignment(0);
        scoreLabel.setVerticalAlignment(0);

        if(game.useAI){
            currentControlModeLabel = new JLabel("Control type: AI");
        } else {
            currentControlModeLabel = new JLabel("Control type: Player");
        }
        currentControlModeLabel.setHorizontalAlignment(0);
        currentControlModeLabel.setVerticalAlignment(0);

        speedLabel = new JLabel("Delay: " + game.speed);
        scoreLabel.setHorizontalAlignment(0);
        scoreLabel.setVerticalAlignment(0);

        UIPanel.add(scoreLabel);
        UIPanel.add(currentControlModeLabel);
        UIPanel.add(speedLabel);

        cells = new cell[sizeVertical][sizeHorizontal];
        for (int v = 0; v < sizeVertical; v++){
            for (int h = 0; h < sizeHorizontal; h++){
                JLabel currentLabel = new JLabel();
                currentLabel.setBackground(Color.BLACK);
                currentLabel.setOpaque(true);
                gamePanel.add(currentLabel);
                cells[v][h] = new cell(currentLabel);
            }
        }
        //fruit things
        clearCells();
        updateFruit();

        frame.add(UIPanel, BorderLayout.PAGE_START);
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setVisible(true);
        System.out.println("Done Initializing frame");
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
                cells[v][h].updateCell("EMPTY");
            }
        }
        System.out.println("Cleared all cells to empty");
    }

    public void setShowCycleValues(boolean show){
        for (int v = 0; v < sizeVertical; v++){
            for (int h = 0; h < sizeHorizontal; h++){
                cells[v][h].setShowCycleValue(show);
            }
        }

        System.out.println("Set show cycle values to: " + show);
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
        fruitCell = emptyCells.get(rand.nextInt(emptyCells.size()));
        fruitCell.updateCell("FRUIT");
        System.out.println("Added fruit to cell: " + fruitCell.getCycleValue());
    }

    public void updateScoreLabel(int newScore){
        scoreLabel.setText("Score: " + newScore);
        System.out.println("Set score label to: " + newScore);
    }
    public void updateSpeedLabel(int newSpeed){
        speedLabel.setText("Delay: " + newSpeed);
        System.out.println("Set speed label to: " + newSpeed);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        game.keyWasPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
