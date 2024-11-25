package edu.penzgtu.areacalculator;

import edu.penzgtu.calculatestrategy.CalculateStrategy;
import edu.penzgtu.function.Function;

public class AreaCalculationRunnable implements Runnable {

    private double start; // Начальное значение
    private double end; // Конечное значение
    private int numberOfIntervals; // Количество интервалов на данной области
    private CalculateStrategy calculateStrategy;
    private double area = 0; // Площадь области на данном потоке

    public AreaCalculationRunnable(double start, double end, int numberOfIntervals, CalculateStrategy calculateStrategy) {
        this.start = start;
        this.end = end;
        this.numberOfIntervals = numberOfIntervals;
        this.calculateStrategy = calculateStrategy;
    }

    public double getArea() {
        return area;
    }

    // Логика потока
    @Override
    public void run() {
        double step = (end - start) / numberOfIntervals;

        for (int i = 0; i < numberOfIntervals; i++) {
            double x = start + i * step;
            double y = Function.getY(x);

            double currentArea = calculateStrategy.calculate(x, y, step);
            area += currentArea;
        }
    }
}
