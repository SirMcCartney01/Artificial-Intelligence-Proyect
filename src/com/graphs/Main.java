package com.graphs;

import javax.swing.UIManager;

import com.graphs.windows.MainWindow;

public class Main {

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Error: Failed to load Look and Feel");
            e.printStackTrace();
        }

        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
