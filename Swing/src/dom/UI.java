package dom;

import javax.swing.*;

public class UI {
    public static void main(String[] args) {
        CarRental();
    }

    static void CarRental(){
        int NumberOfDays;
        double DayPrice;
        double TotPrice;
        String InData;

        InData = JOptionPane.showInputDialog("Antal dagar?");
        NumberOfDays = Integer.parseInt(InData);

        InData = JOptionPane.showInputDialog("Pris per dag?");
        DayPrice = Double.parseDouble(InData);

        TotPrice = DayPrice * NumberOfDays;
        JOptionPane.showMessageDialog(null , "Totalt pris: " + TotPrice + "kr");
    }

    static void Greetings2(){
        String name;
        String greetings;
        name = JOptionPane.showInputDialog("Vad heter du?");
        greetings = "Välkommen " + name;
        JOptionPane.showMessageDialog(null, greetings);
        System.exit(0);
    }

    static void Greetings1(){
        String name;
        String greetings;
        name = JOptionPane.showInputDialog("Vad heter du?");
        greetings = "Välkommen " + name;
        System.out.println(greetings);
    }
}
