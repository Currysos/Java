package com;

import java.util.Arrays;

public class ai {
    int[] cellValues;
    int currentCellValue, fruitCellValue;
    int[] tailCellValues;

    public void updateCycle(){
        //init variables
        updateVariables();
        int maxTailValue = tailCellValues[tailCellValues.length - 1];
        int minTailValue = tailCellValues[0];
        lookAtCellsAround();
        System.out.println("Min tail value: " + minTailValue +
                "\nMax tail value: " + maxTailValue);

        int[] possibleCellValues = new int[cellValues.length];
        System.arraycopy(cellValues, 0, possibleCellValues, 0, cellValues.length);

        //remove all elements that is in tail length
        for (int i = 0; i < possibleCellValues.length; i++){
            if (minTailValue <= possibleCellValues[i] && possibleCellValues[i] <= maxTailValue) {
                possibleCellValues = removeElementAt(possibleCellValues, i);
                i--;
            }
        }

        //remove all elements that hits something
        for (int i = 0; i < possibleCellValues.length; i++){
            if(game.frameInterface.getCellFromValue(possibleCellValues[i]) == null) {
                possibleCellValues = removeElementAt(possibleCellValues, i);
                i--;
            }
        }

        //remove elements that hits walls
        for (int i = 0; i < possibleCellValues.length; i++){
            if(possibleCellValues[i] == Integer.MAX_VALUE){
                possibleCellValues = removeElementAt(possibleCellValues, i);
                i--;
            }
        }

        //find closest value to apple

        //go there
        if(possibleCellValues.length > 0) {
            int closest = closestValue(possibleCellValues, fruitCellValue);
            System.out.println("Fruit cell value: " + fruitCellValue);
            System.out.println("Closest value: " + closest);
            System.out.println("Possible cell values count: " + (possibleCellValues.length));
            for (int number: possibleCellValues) {
                System.out.println(number);
            }
            calculateRotation(closest);
        } else {
            System.out.println("Cant find any possible ways. Following pattern");
            calculateRotation(currentCellValue + 1);
        }
    }

    int[] removeElementAt(int[] arr, int index){
        int[] out = new int[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++){
            if(i == index){
                continue;
            }
            out[k++] = arr[i];
        }
        return out;
    }
    int closestValue(int[] arr, int target){
        int idx = 0;
        int dist = Math.abs(arr[0] - target);

        for (int i = 1; i< arr.length; i++) {
            int cdist = Math.abs(arr[i] - target);

            if (cdist < dist) {
                idx = i;
                dist = cdist;
            }
        }

        return arr[idx];
    }

    int highestIndex(int[] arr){
        int high = arr[0];
        int highIndex = 0;
        for (int i = 1; i < arr.length; i++){
            if (high < arr[i]) {
                high = arr[i];
                highIndex = i;
            }
        }

        return highIndex;
    }
    int minIndex(int[] arr) {
        int min = arr[0];
        int smallestIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if(min > arr[i]){
                min = arr[i];
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    void updateVariables(){
        //init variables
        currentCellValue = game.frameInterface.getCell(game.SNAKE.getPosHorizontal(), game.SNAKE.getPosVertical()).getCycleValue();
        fruitCellValue = game.frameInterface.fruitCell.getCycleValue();
        tailCellValues = new int[game.SNAKE.getTailLength()];
        for (int i = 0; i < tailCellValues.length; i++) {
            tailCellValues[i] = game.SNAKE.tails.get(i).getTailCellValue();
        }
        Arrays.sort(tailCellValues);
    }

    void calculateRotation(int lookForValue){
        if(cellValues[0] == lookForValue){
            moveUp();
        }
        if(cellValues[1] == lookForValue){
            moveRight();
        }
        if(cellValues[2] == lookForValue){
            moveDown();
        }
        if(cellValues[3] == lookForValue){
            moveLeft();
        }
    }

    void lookAtCellsAround(){
        cellValues = initIntArray();
        if(game.SNAKE.getPosVertical() != 0){
            cellValues[0] = game.frameInterface.getCell(game.SNAKE.getPosHorizontal(), game.SNAKE.getPosVertical() - 1).getCycleValue();
        }
        if(game.SNAKE.getPosHorizontal() !=  game.gridSizeHorizontal - 1){
            cellValues[1] = game.frameInterface.getCell(game.SNAKE.getPosHorizontal() + 1, game.SNAKE.getPosVertical()).getCycleValue();
        }
        if(game.SNAKE.getPosVertical() != game.gridSizeVertical - 1){
            cellValues[2] = game.frameInterface.getCell(game.SNAKE.getPosHorizontal(), game.SNAKE.getPosVertical() + 1).getCycleValue();
        }
        if(game.SNAKE.getPosHorizontal() != 0){
            cellValues[3] = game.frameInterface.getCell(game.SNAKE.getPosHorizontal() - 1, game.SNAKE.getPosVertical()).getCycleValue();
        }

        System.out.println("\nUP: " + cellValues[0] +
                "\nRIGHT: " + cellValues[1] +
                "\nDOWN: " + cellValues[2] +
                "\nLEFT: " + cellValues[3]);
    }

    int[] initIntArray(){
        int[] arr = new int[4];
        for (int i = 0; i < 4; i++){
            arr[i] = Integer.MAX_VALUE;
        }
        return arr;
    }

    void moveUp(){
        game.SNAKE.rotate(1);
        System.out.println("Turn up");
    }
    void moveRight(){
        game.SNAKE.rotate(2);
        System.out.println("Turn Right");
    }
    void moveDown(){
        game.SNAKE.rotate(3);
        System.out.println("Turn Down");
    }
    void moveLeft(){
        game.SNAKE.rotate(4);
        System.out.println("Turn Left");
    }
}
