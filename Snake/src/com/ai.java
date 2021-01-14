package com;

import java.util.Arrays;

public class ai {
    int[] cellValues;
    int currentCellValue, fruitCellValue, tailValue, target = 3;
    boolean goingToFruit = false;

    public void updateCycle(){
        //init variables
        currentCellValue = game.frameInterface.getCell(game.SNAKE.getPosHorizontal(), game.SNAKE.getPosVertical()).getCycleValue();
        fruitCellValue = game.frameInterface.fruitCell.getCycleValue();

        if(currentCellValue == target){
            //hit the target
            if(target < fruitCellValue || !goingToFruit){
                target = fruitCellValue;
                goingToFruit = true;
            } else {
                target = (game.gridSizeHorizontal * game.gridSizeVertical) - 1;
                goingToFruit = false;
            }
        }

        tailValue = game.SNAKE.getTail(game.SNAKE.getTailLength() - 1).getTailCellValue();

        int[] bodyValues = new int[game.SNAKE.getTailLength() + 1];
        bodyValues[0] = currentCellValue;
        for (int i = 1; i < bodyValues.length - 1; i++){
            bodyValues[i] = game.SNAKE.getTail(i).getTailCellValue();
        }
        Arrays.sort(bodyValues);

        lookAtCellsAround();
        System.out.println("Tail value: " + tailValue);



        //See if we can go to closest
        int closest = closestValue(cellValues, target);
        System.out.println("Target: " + target);
        if(!(tailValue <= closest && closest <= bodyValues[bodyValues.length - 1]) && closest > currentCellValue && target > closest && target > tailValue) {
            System.out.println("Going for closest. Turning towards " + closest);
            calculateRotation(closest);
            return;
        }

        calculateRotation(currentCellValue + 1);

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
    void calculateRotation(int lookForValue){
        if(cellValues[0] == lookForValue){
            game.SNAKE.rotate(1);
            System.out.println("Turn up");
        }
        if(cellValues[1] == lookForValue){
            game.SNAKE.rotate(2);
            System.out.println("Turn Right");
        }
        if(cellValues[2] == lookForValue){
            game.SNAKE.rotate(3);
            System.out.println("Turn Down");
        }
        if(cellValues[3] == lookForValue){
            game.SNAKE.rotate(4);
            System.out.println("Turn Left");
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
}
