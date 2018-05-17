package package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Cartesian {

    List<Node> points;
    List<Edge> lines;
    CartesianFrame frame;

    public Cartesian(List<Node> nodes, List<Edge> edges) {

        this.points = nodes;
        this.lines = edges;

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame = new CartesianFrame();
                frame.showUI();
                //Draw nodes points
                for (int i = 0; i < points.size(); i++) {
                    frame.panel.drawPoint(points.get(i));
                }
                for (int i = 0; i < lines.size(); i++) {
                    frame.panel.drawLine(lines.get(i));
                }
            }
        });
    }

}

class CartesianFrame extends JFrame {

    CartesianPanel panel;

    public CartesianFrame() {
        panel = new CartesianPanel();

        this.add(panel);
    }

    public void showUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cartesian");
        setSize(700, 700);
        setVisible(true);
    }
}

class CartesianPanel extends JPanel {
    // x-axis coord constants

    public static final int X_AXIS_FIRST_X_COORD = 50;
    public static final int X_AXIS_SECOND_X_COORD = 600;
    public static final int X_AXIS_Y_COORD = 600;

    // y-axis coord constants
    public static final int Y_AXIS_FIRST_Y_COORD = 50;
    public static final int Y_AXIS_SECOND_Y_COORD = 600;
    public static final int Y_AXIS_X_COORD = 50;

    //arrows of axis
    public static final int FIRST_LENGTH = 10;
    public static final int SECOND_LENGTH = 5;

    // size of start coordinate lenght
    public static final int ORIGIN_COORDINATE_LENGTH = 6;

    // distance of coordinate strings from axis
    public static final int AXIS_STRING_DISTANCE = 20;

    public int xLength;
    public int yLength;

    public static List<Point> points = new ArrayList<>();
    public static List<Edge> lines = new ArrayList<>();

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // x-axis
        g2.drawLine(X_AXIS_FIRST_X_COORD, X_AXIS_Y_COORD,
                X_AXIS_SECOND_X_COORD, X_AXIS_Y_COORD);
        // y-axis
        g2.drawLine(Y_AXIS_X_COORD, Y_AXIS_FIRST_Y_COORD,
                Y_AXIS_X_COORD, Y_AXIS_SECOND_Y_COORD);

        // draw origin Point
        g2.fillOval(X_AXIS_FIRST_X_COORD - (ORIGIN_COORDINATE_LENGTH / 2),
                Y_AXIS_SECOND_Y_COORD - (ORIGIN_COORDINATE_LENGTH / 2),
                ORIGIN_COORDINATE_LENGTH, ORIGIN_COORDINATE_LENGTH);

        // label axes
        g2.drawString("X", X_AXIS_SECOND_X_COORD - AXIS_STRING_DISTANCE / 2,
                X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
        g2.drawString("Y", Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
                Y_AXIS_FIRST_Y_COORD + AXIS_STRING_DISTANCE / 2);
        g2.drawString("(0, 0)", X_AXIS_FIRST_X_COORD - AXIS_STRING_DISTANCE,
                Y_AXIS_SECOND_Y_COORD + AXIS_STRING_DISTANCE);

        // numerate axis
        int xCoordNumbers = 10;
        int yCoordNumbers = 10;
        xLength = (X_AXIS_SECOND_X_COORD - X_AXIS_FIRST_X_COORD)
                / xCoordNumbers;
        yLength = (Y_AXIS_SECOND_Y_COORD - Y_AXIS_FIRST_Y_COORD)
                / yCoordNumbers;

        // draw x-axis numbers
        for (int i = 1; i <= xCoordNumbers; i++) {
            g2.drawLine(X_AXIS_FIRST_X_COORD + (i * xLength),
                    X_AXIS_Y_COORD - SECOND_LENGTH,
                    X_AXIS_FIRST_X_COORD + (i * xLength),
                    X_AXIS_Y_COORD + SECOND_LENGTH);
            g2.drawString(Integer.toString(i * 10),
                    X_AXIS_FIRST_X_COORD + (i * xLength) - 3,
                    X_AXIS_Y_COORD + AXIS_STRING_DISTANCE);
        }

        //draw y-axis numbers
        for (int i = 1; i <= yCoordNumbers; i++) {
            g2.drawLine(Y_AXIS_X_COORD - SECOND_LENGTH,
                    Y_AXIS_SECOND_Y_COORD - (i * yLength),
                    Y_AXIS_X_COORD + SECOND_LENGTH,
                    Y_AXIS_SECOND_Y_COORD - (i * yLength));
            g2.drawString(Integer.toString(i * 10),
                    Y_AXIS_X_COORD - AXIS_STRING_DISTANCE,
                    Y_AXIS_SECOND_Y_COORD - (i * yLength));
        }

        points.forEach(p -> drawPointOnPanel(p, g));
        lines.forEach(l -> drawLineOnPanel(l, g));
    }

    public void drawPoint(Point point) {
        points.add(point);
        repaint();
    }

    private void drawPointOnPanel(Point point, Graphics g) {
        final int pointDiameter = 5;
        final int x = X_AXIS_FIRST_X_COORD + (point.x * xLength / 10) - pointDiameter / 2;
        final int y = Y_AXIS_SECOND_Y_COORD - (point.y * yLength / 10) - pointDiameter / 2;
        g.setColor(Color.red);
        g.fillOval(x, y, pointDiameter, pointDiameter);

        g.drawString(point.toString(), x, y);
    }

    public void drawLine(Edge line) {
        lines.add(line);
        repaint();
    }

    private void drawLineOnPanel(Edge line, Graphics g) {
        final int x1 = X_AXIS_FIRST_X_COORD + (line.getSource().x * xLength / 10);
        final int y1 = Y_AXIS_SECOND_Y_COORD - (line.getSource().y * yLength / 10);
        final int x2 = X_AXIS_FIRST_X_COORD + (line.getDestination().x * xLength / 10);
        final int y2 = Y_AXIS_SECOND_Y_COORD - (line.getDestination().y * yLength / 10);
        g.setColor(Color.blue);
        g.drawLine(x1, y1, x2, y2);
    }

}
