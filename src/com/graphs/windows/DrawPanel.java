package com.graphs.windows;

import javax.swing.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.util.List;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    private List<Ellipse2D> ellipses, polygonNodes;
    private List<Integer> xLinesCoordinates, yLinesCoordinates;
    private List<Polygon> polygons;
    private int counter, clicks, numPolygons;
    private int xBefore, yBefore;
    private int firstArrayPosition, secondArrayPosition;
    private int xAux, yAux;
    private boolean dragged, failure;

    private Ellipse2D polygonNode;
    private Polygon polygon;

    private JPopupMenu panelPopupMenu;

    private static final int DIMENSION = 30;
    private static boolean POLYGON_MODE = false;

    public DrawPanel() {
        super();
        this.ellipses = new ArrayList<>();
        this.xLinesCoordinates = new ArrayList<>();
        this.yLinesCoordinates = new ArrayList<>();
        this.polygonNodes = new ArrayList<>();
        this.polygons = new ArrayList<>();
        this.counter = 0;
        this.clicks = 0;
        this.xBefore = 0;
        this.yBefore = 0;
        this.firstArrayPosition = 0;
        this.secondArrayPosition = 0;
        this.xAux = 100;
        this.yAux = 100;
        this.dragged = false;
        this.polygonNode = null;
        this.polygon = null;
        this.failure = false;
        this.numPolygons = 0;
        initPanel(this);
    }

    private void initPanel(JPanel panel) {
        panel.setBackground(Color.DARK_GRAY);

        panelPopupMenu = new JPopupMenu();
        JMenuItem nodeModeMenuItem = new JMenuItem("Editar nodos");
        JMenuItem createPolygonMenuItem = new JMenuItem("Crear y editar poligono");
        JMenuItem fillPolygonMenuItem = new JMenuItem("Rellenar poligono");
        JMenuItem cleanPolygonMenuItem = new JMenuItem("Limpiar Poligonos");

        createPolygonMenuItem.addActionListener(e -> {
            POLYGON_MODE = true;
            System.out.println("Modo poligono");
        });

        nodeModeMenuItem.addActionListener(e -> {
            POLYGON_MODE = false;
            System.out.println("Modo nodos");
        });

        cleanPolygonMenuItem.addActionListener(e -> {
            clear();
            panel.repaint();
        });

        fillPolygonMenuItem.addActionListener(e -> {
            polygon = new Polygon();

            //if(!failure){
                for (Ellipse2D node : polygonNodes) {
                    polygonNode = node;
                    polygon.addPoint((int) polygonNode.getX(), (int) polygonNode.getY());

                }
                numPolygons++;
                polygons.add(polygon);

                polygon = null;
                polygonNodes = null;
                polygonNodes = new ArrayList<>();
            //}

            repaint();
         });

        panelPopupMenu.add(createPolygonMenuItem);
        panelPopupMenu.add(fillPolygonMenuItem);
        panelPopupMenu.add(cleanPolygonMenuItem);
        panelPopupMenu.add(nodeModeMenuItem);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panelMousePressed(e);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panelMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                panelMouseMoved(e);
            }
        });
    }

    private void panelMousePressed(MouseEvent e) {
        final int button = e.getButton();

        if (!POLYGON_MODE) {
            if (button == MouseEvent.BUTTON1) {
                if (isPositionCorrect(e)) {
                    if (arrayPosition(e) != -1) {
                        if (clicks == 2) { // Change 1 if needed
                            secondArrayPosition = arrayPosition(e);
                            clicks = 0;
                            if(firstArrayPosition != secondArrayPosition){
                                xLinesCoordinates.add(firstArrayPosition);
                                yLinesCoordinates.add(secondArrayPosition);
                            }
                            repaint();
                        } else {
                            firstArrayPosition = arrayPosition(e);
                            clicks++;
                        }
                    }else clicks = 0;
                } else {
                    if(existPolygon(e.getPoint()) == -1){
                        clicks = 0;
                        xAux = e.getX();
                        yAux = e.getY();
                        ellipses.add(new Ellipse2D.Double(xAux, yAux, DIMENSION, DIMENSION));
                    }
                }
            } else
                setComponentPopupMenu(panelPopupMenu);
        } else {
            if (button == MouseEvent.BUTTON1) {
                if(existPolygon(e.getPoint()) == -1 && !isPositionCorrect(e)){
                    polygonNodes.add(new Ellipse2D.Double(e.getX(), e.getY(), 20, 20));
                }
            }
        }
        if(existPolygon(e.getPoint()) == -1){
            failure = true;

        } else {
            failure = false;
            //JOptionPane.showMessageDialog(null,"Hay algo");
        }



        repaint();
    }

    private void panelMouseDragged(MouseEvent e) {
        Ellipse2D currentEllipse;
        int num = existPolygon(e.getPoint());

        if(POLYGON_MODE) {
            if (existPolygon(e.getPoint()) != -1 ) { //&& !isPositionCorrect(e)
                double x = e.getX();
                double y = e.getY();

                if (polygons.get(num).getBounds().getX() > x)
                    x = polygons.get(num).getBounds().getX() - x;
                else
                    x = x - polygons.get(num).getBounds().getX();

                if (polygons.get(num).getBounds().getY() > y)
                    y = polygons.get(num).getBounds().getY() - y;
                else
                    y = y - polygons.get(num).getBounds().getY();

                polygons.get(num).translate((int) x - (polygons.get(num).getBounds().width / 2), (int) y - (polygons.get(num).getBounds().height / 2));
            }
        }else{
            if (!dragged) {
                    if (isPositionCorrect(e) ) { //&& existPolygon(e.getPoint()) == -1
                        xBefore = e.getX();
                        yBefore = e.getY();
                        dragged = true;
                    }
                } else {
                    currentEllipse = new Ellipse2D.Double((ellipses.get(counter).getX() + e.getX()) - xBefore, (ellipses.get(counter).getY()
                            + e.getY()) - yBefore, DIMENSION, DIMENSION);
                    ellipses.set(counter, currentEllipse);
                    xBefore = e.getX();
                    yBefore = e.getY();
                }
        }

        repaint();
    }

    private void panelMouseMoved(MouseEvent e) {
        dragged = false;
        if (existPolygon(e.getPoint()) != -1 || isPositionCorrect(e))
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        else
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private boolean isPositionCorrect(MouseEvent e) {
        int x1 = e.getX(), y1 = e.getY();

        for (int i= 0; i < ellipses.size(); i++) {
            if((x1 > ellipses.get(i).getX()) && (x1 < ellipses.get(i).getX() + DIMENSION) && (y1 > ellipses.get(i).getY()) &&
                    (y1 < ellipses.get(i).getY() + DIMENSION)) {
                counter = i;
                return true;
            }
        }

        return false;
    }

    private int arrayPosition(MouseEvent e) {
        int x1 = e.getX(), y1 = e.getY();

        for (int i= 0; i < ellipses.size(); i++) {
            if((x1 > ellipses.get(i).getX()) && (x1 < ellipses.get(i).getX() + DIMENSION) &&
                    (y1 > ellipses.get(i).getY()) && (y1 < ellipses.get(i).getY() + DIMENSION)) {
                counter = i;
                return i;
            }
        }

        return -1;
    }

    private void drawArrowHead(Graphics2D g2, Point start, Point finish, Color color) {
        double phi = Math.toRadians(30);
        final int size = 10;
        double dx = start.x - finish.x;
        double dy = start.y - finish.y;
        double theta = Math.atan2(dy, dx);
        double x1, y1, rho = theta + phi;

        g2.setPaint(color);

        for (int j = 0; j < 2; j++) {
            x1 = start.x - size * Math.cos(rho);
            y1 = start.y - size * Math.sin(rho);
            g2.draw(new Line2D.Double(start.x, start.y, x1, y1));
            rho = theta - phi;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ellipses
        for (int i = 0; i < ellipses.size(); i++) {
            g2.setColor(new Color(117, 35, 37));
            g2.setStroke(new BasicStroke(2));
            g2.fill(ellipses.get(i));
            g2.setColor(Color.BLACK);
            g2.drawOval((int) ellipses.get(i).getX(), (int) ellipses.get(i).getY(), DIMENSION, DIMENSION);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 12));
            g2.drawString(String.valueOf(i), (int) ellipses.get(i).getX(), (int) ellipses.get(i).getY());
        }

        // Lines
        for (int i = 0; i < xLinesCoordinates.size(); i++) {
            double lineX1 = ellipses.get(xLinesCoordinates.get(i)).getCenterX();
            double lineY1 = ellipses.get(xLinesCoordinates.get(i)).getCenterY();
            double lineX2 = ellipses.get(yLinesCoordinates.get(i)).getCenterX();
            double lineY2 = ellipses.get(yLinesCoordinates.get(i)).getCenterY();

            // Directed edge
            Line2D.Double line = new Line2D.Double(lineX1, lineY1, lineX2, lineY2);
            if (MainWindow.GRAPH_KIND == 1) { // Directed
                Point startLine = new Point((int) line.getX1(), (int) line.getY1());
                Point finishLine = new Point((int) line.getX2(), (int) line.getY2());
                g2.setColor(Color.BLACK);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(2));
                g2.draw(line);
                drawArrowHead(g2, finishLine, startLine, Color.BLACK);
            }
            // Undirected edge
            else {
                g2.setColor(Color.BLACK);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(3));
                g2.draw(line);
            }

            // Value string
            int xAux = ((int) line.getBounds().getX() + (int) line.getX2())/2;
            int yAux = ((int) line.getBounds().getY() + (int) line.getY2())/2;

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 12));
            g2.drawString(String.valueOf(line.getBounds().getWidth()), xAux, yAux);


        }

        // Polygon nodes
        g2.setColor(Color.BLACK);
        for (Ellipse2D node : polygonNodes)
            g2.fill(node);


        // Shape
        g2.setColor(Color.BLACK);
        for (Polygon p : polygons)
            g2.fill(p);
    }

    private int existPolygon(Point p) {
        for (int i = 0; i < numPolygons; i++) {
            if (polygons.get(i).contains(p))
                return i;
        }
        return -1;
    }

    private void clear(){
        numPolygons = 0;
        polygon = null;
        polygons = new ArrayList<>();
    }

}
