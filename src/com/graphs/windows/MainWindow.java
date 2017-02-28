package com.graphs.windows;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;

public class MainWindow extends JFrame {

    // 0 -> Undirected  1 -> Directed
    public static int GRAPH_KIND = 1;

    public MainWindow() {
        super("Grafos");
        initComponents(this);
    }

    private void initComponents(JFrame frame) {
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JMenuBar mainMenu = new JMenuBar();
        JMenu graphMenu = new JMenu("Tipo de Grafo");
        JMenu algorithmsMenu = new JMenu("Algoritmos");
        JRadioButtonMenuItem directedMenuItem = new JRadioButtonMenuItem("Dirigido", true);
        JRadioButtonMenuItem undirectedMenuItem = new JRadioButtonMenuItem("No Dirigido");
        JMenuItem dijkstraMenuItem = new JMenuItem("Dijkstra");
        JMenuItem floydMenuItem = new JMenuItem("Floyd");
        JMenuItem warshallMenuItem = new JMenuItem("Warshall");
        JMenuItem primMenuItem = new JMenuItem("Prim");
        JMenuItem kruskalMenuItem = new JMenuItem("Kruskal");

        DrawPanel drawPanel = new DrawPanel();

        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(directedMenuItem);
        menuGroup.add(undirectedMenuItem);

        algorithmsMenu.add(dijkstraMenuItem);
        algorithmsMenu.add(floydMenuItem);
        algorithmsMenu.add(warshallMenuItem);
        algorithmsMenu.addSeparator();
        algorithmsMenu.add(primMenuItem);
        algorithmsMenu.add(kruskalMenuItem);

        graphMenu.add(directedMenuItem);
        graphMenu.add(undirectedMenuItem);

        mainMenu.add(graphMenu);
        mainMenu.add(algorithmsMenu);

        frame.setJMenuBar(mainMenu);

        undirectedMenuItem.addActionListener(e -> GRAPH_KIND = 0);
        directedMenuItem.addActionListener(e -> GRAPH_KIND = 1);
        primMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Perdone profe no lo hicimos :(",
                                 "Error", JOptionPane.ERROR_MESSAGE));

        container.add(drawPanel, BorderLayout.CENTER);
    }
}
