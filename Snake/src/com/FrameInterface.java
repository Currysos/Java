package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

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

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
