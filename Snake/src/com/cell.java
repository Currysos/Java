package com;

import javax.swing.*;
import java.awt.*;

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
