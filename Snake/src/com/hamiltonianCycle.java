package com;

public class hamiltonianCycle {
    int gridSizeHorizontal;
    int gridSizeVertical;
    FrameInterface frameInterface;

    public hamiltonianCycle(int gridSizeHorizontal, int gridSizeVertical, FrameInterface frameInterface) {
        this.gridSizeHorizontal = gridSizeHorizontal;
        this.gridSizeVertical = gridSizeVertical;
        this.frameInterface = frameInterface;

        generateCycle();
    }

    void generateCycle(){
        int index = 0;
        int currentHorizontal;
        int currentVertical = 0;
        //first top row
        for (currentHorizontal = 0; currentHorizontal < gridSizeHorizontal; currentHorizontal++){
            frameInterface.getCell(currentHorizontal, currentVertical).setCycleValue(index);
            index++;
        }
        //rest
        for (currentHorizontal--; currentHorizontal >= 0; currentHorizontal--){
            for (currentVertical = 1; currentVertical < gridSizeVertical; currentVertical++){
                frameInterface.getCell(currentHorizontal, currentVertical).setCycleValue((index));
                index++;
            }
            currentHorizontal--;
            for (currentVertical--; currentVertical >= 1; currentVertical--){
                frameInterface.getCell(currentHorizontal, currentVertical).setCycleValue((index));
                index++;
            }
        }
    }
}
