/**
 * klasa służąca do tworzenia wykresów
 *
 * W razie pojawienia sie bledu z JFreeChart nalezy recznie dodac zainstalowany z JAR
 * JFreeChart do classpath
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ChartMaker {

    /**
     *
     * parsowanie punktow
     * @param inch
     * return punkty z cali
     */
    static float parseInch(float inch) {
        return inch * 72;
    }

    /**
     * parsowanie punktow
     *
     * @param cm
     * @return punkty z cm
     */
    static float parseCM(float cm) {
        return cm * 28.346456693f;
    }

    /**
     * Metoda do rysowania wykresow. Petla dodaje dane do wykresu. Wewnatrz JFreeChart chart znajduja sie dane,
     * ktorymi odznaczamy osi, oraz nazwe wykresu. za wyswietlenie odpowiada glowny komponent JRAME z SWING.
     * W JFRAME tworzymy okienko, ustalamy jego rozmiary i inne komponenty.
     * @param xData wartosci x
     * @param yData wartosci y
     *
     */
    public static void drawChart(float[] xData, float[] yData) {
        XYSeries series = new XYSeries("Wartosci pawrsowane");

        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres",
                "X",
                "Y",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

