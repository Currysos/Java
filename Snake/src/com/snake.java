package com;

import java.util.ArrayList;

public class snake {
    ArrayList<tail> tails;
    int posVertical;
    int posHorizontal;
    boolean addTail;

    int dir; //1 = up, 2 = right; 3 = down, 4 = left

    public snake(int posHorizontal, int posVertical, int dir) {
        this.tails = new ArrayList<tail>();
        this.tails.add(new tail(posHorizontal - 1, posVertical));
        this.tails.add(new tail(posHorizontal - 2, posVertical));
        this.posVertical = posVertical;
        this.posHorizontal = posHorizontal;
        this.dir = dir;
        this.addTail = false;
    }
    public void setAddTail(boolean bool){
        addTail = bool;
    }
    public int getPosVertical() {
        return posVertical;
    }
    public int getPosHorizontal() {
        return posHorizontal;
    }

    public int getTailLength(){
        return tails.size();
    }
    public tail getTail(int tailIndex) { return tails.get(tailIndex); }

    public void moveSnake(){
        //Move and add tail
        for (int i = tails.size() - 1; i >= 0; i--){
            if (i == tails.size() - 1 && addTail) {
                tails.add(new tail(tails.get(tails.size() - 1).getPosHorizontal(), tails.get(tails.size() - 1).getPosVertical()));
            }
            if(i == 0){
                tails.get(i).setPosition(posHorizontal, posVertical);
            } else {
                tails.get(i).setPosition(tails.get(i - 1).getPosHorizontal(), tails.get(i - 1).getPosVertical());
            }

        }
        addTail = false;

        //Move Snake
        switch (dir){
            case 1:
                //Move Up
                posVertical--;
                break;
            case 2:
                posHorizontal++;
                //Move right
                break;
            case 3:
                posVertical++;
                //Move down
                break;
            case 4:
                posHorizontal--;
                //Move left
                break;
            default:
                break;
        }
    }

    public void rotate(int direction){
        switch (dir) {
            case 1:
                if(direction != 3){
                    dir = direction;
                }
                break;
            case 2:
                if(direction != 4){
                    dir = direction;
                }
                break;
            case 3:
                if(direction != 1){
                    dir = direction;
                }
                break;
            case 4:
                if(direction != 2){
                    dir = direction;
                }
                break;
        }
    }

    public ArrayList<tail> getTails() {
        return tails;
    }
}

class tail {
    int posHorizontal;
    int posVertical;

    public tail(int posHorizontal, int posVertical) {
        this.posHorizontal = posHorizontal;
        this.posVertical = posVertical;
    }
    public int getPosHorizontal() {
        return posHorizontal;
    }
    public int getPosVertical() {
        return posVertical;
    }
    public int getTailCellValue(){
        return game.frameInterface.getCell(posHorizontal, posVertical).getCycleValue();
    }

    public void setPosition(int horizontal, int vertical){
        posHorizontal = horizontal;
        posVertical = vertical;
    }
}
