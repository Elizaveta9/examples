package edu.penzgtu;

import java.util.concurrent.Callable;

public class AreaCalculationThread implements Callable<Double> {

    private double start; // Начальное значение
    private double end; // Конечное значение
    private int numberOfIntervals; // Количество интервалов на данной области

    private CalculateStrategy calculateStrategy;

    public AreaCalculationThread(double start, double end, int numberOfIntervals, CalculateStrategy calculateStrategy) {
        this.start = start;
        this.end = end;
        this.numberOfIntervals = numberOfIntervals;
        this.calculateStrategy = calculateStrategy;
    }

    // Логика потока
    @Override
    public Double call() {
        double step = (end - start) / numberOfIntervals;

        double localArea = 0; // Площадь области на данном потоке

        for (int i = 0; i < numberOfIntervals; i++) {
            double x = start + i * step;
            double y = Function.getY(x);

            double currentArea = calculateStrategy.calculate(x, y, step);
            localArea += currentArea;
        }

        return localArea;
    }
}
