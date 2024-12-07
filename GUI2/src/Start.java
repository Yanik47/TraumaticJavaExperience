
//package zadania.newZad9;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Start extends JFrame {
    JTextField aText;
    JTextField bText;

    double a;
    double b;

    List<Point> pointList;

    public Start(String title) {
        super(title);

        pointList = new ArrayList<>();
        a = 0;
        b = 0;
    }

    public static void main(String[] args) {
        Start gui = new Start("Java - Zadanie 09");

        gui.startedSettings();
    }

    public void startedSettings() {
        this.setSize(800, 1000);
        this.setLayout(new BorderLayout());

        createFooter();
        createPaintPanel();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createFooter() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JButton button = new JButton();
        button.setText("Enter pkt");
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    String content = new String(Files.readAllBytes(selectedFile.toPath()));
                    String[] lines = content.split("\n");
                    int numberOfLines = Integer.parseInt(lines[0].trim().split("\\s+")[0]);

                    Point.maxX = 1;
                    Point.minX = -1;
                    Point.maxY = 1;
                    Point.minY = -1;
                    pointList.clear();

                    for (int i = 1; i <= numberOfLines; i++) {
                        String[] parts = lines[i].split(" ");
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1]);

                        if(x > Point.maxX)
                            Point.maxX = x;
                        else if (x < Point.minX)
                            Point.minX = x;

                        if(y > Point.maxY)
                            Point.maxY = y;
                        else if(y < Point.minY)
                            Point.minY = y;

                        Point point = new Point(x, y);
                        pointList.add(point);
                    }

                    calculateCoefficients();

                    Point.minX--;
                    Point.maxX++;
                    Point.maxY++;
                    Point.minY--;

                    this.repaint();

                } catch (IOException k) {
                    k.printStackTrace();
                }
            }
        });

        this.aText = new JTextField();
        this.bText = new JTextField();

        this.aText.setEnabled(false);
        this.bText.setEnabled(false);

        panel.add(button);
        panel.add(this.aText);
        panel.add(this.bText);

        this.add(panel, BorderLayout.SOUTH);
    }

    private void createPaintPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if(pointList.isEmpty())
                    return;

                double startX = this.getLocation().getX();
                double startY = this.getLocation().getY();

                double partX = this.getSize().getWidth() / (Point.maxX + Math.abs(Point.minX));
                double partY = this.getSize().getHeight() / (Point.maxY + Math.abs(Point.minY));

                g.setColor(Color.black);

                // Draw Y-axis
                int x1 = (int) (startX + Math.abs(Point.minX) * partX);
                int y1 = (int) startY;
                int x2 = x1;
                int y2 = y1 + this.getSize().height;

                g.drawLine(x1, y1, x2, y2);

                // Draw X-axis
                x1 = (int) startX;
                y1 = (int) (startY + Point.maxY * partY);
                x2 = x1 + this.getSize().width;
                y2 = y1;
                g.drawLine(x1, y1, x2, y2);

                g.setColor(Color.red);

                // Draw Line
                x1 = (int) (startX + (Point.maxX + Math.abs(Point.minX)) * partX);
                int y = (int) (b * Point.maxX + a);
                y1 = (int) (startY + (Point.maxY - y) * partY);

                x2 = (int) startX;
                y = (int) (b * Point.minX + a);
                y2 = (int) (startY + (Point.maxY - y) * partY);
                g.drawLine(x1, y1, x2, y2);

                g.setColor(Color.blue);

                // Draw Points
                for(Point point: pointList) {
                    int xCircle = (int) (startX + (Math.abs(Point.minX) + point.x()) * partX);
                    int yCircle = (int) (startY + (Point.maxY - point.y()) * partY);

                    g.fillOval(xCircle, yCircle, 7, 7);
                }
            }
        };

        this.add(panel, BorderLayout.CENTER);
    }

    private void calculateCoefficients() {
        int n = pointList.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (Point point : pointList) {
            double x = point.x();
            double y = point.y();

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += Math.pow(x, 2);
        }

        double w = n * sumX2 - Math.pow(sumX, 2);
        double b = (n * sumXY - sumX * sumY) / w;
        double a = (sumX2 * sumY - sumX * sumXY) / w;

        this.a = a;
        this.b = b;

        this.aText.setText("a = " + a);
        this.bText.setText("b = " + b);
    }
}

record Point(double x, double y) {
    static double maxX = 1;
    static double minX = -1;
    static double maxY = 1;
    static double minY = -1;
}
