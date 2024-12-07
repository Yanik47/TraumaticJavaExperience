/**
 * Narzedzie testujace ChartMaker, xData to wartosci punktow na osi OX, yData, to wartosci punktow na osi OY
 * metoda drawChart() rysuje wykres
 */
public class TestChartMaker {
    public static void main(String[] args) {
        float[] xData = {1.0f, 2.0f, 3.0f};
        float[] yData = {ChartMaker.parseInch(1.0f), ChartMaker.parseCM(2.0f), ChartMaker.parseCM(5.0f)};

        ChartMaker.drawChart(xData, yData);
    }
}