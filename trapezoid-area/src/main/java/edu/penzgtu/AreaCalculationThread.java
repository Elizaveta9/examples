package edu.penzgtu;

import java.util.concurrent.Callable;

public class AreaCalculationThread implements Callable<Double> {

    private double start;
    private double end;
    private int numIntervals;

    public AreaCalculationThread(double start, double end, int numIntervals) {
        this.start = start;
        this.end = end;
        this.numIntervals = numIntervals;
    }

    @Override
    public Double call() {
        double interval = (end - start) / numIntervals;
        double area = 0;
        for (int i = 0; i < numIntervals; i++) {
            double x = start + i * interval;
            double y = -Math.pow((x - 1500), 2) / 4000 + 1000;
            double currentRectangleArea = Math.abs(y * interval);
            area += currentRectangleArea;
        }
        System.out.printf("I am counting [%f; %f) with step %f\n", start, end, interval);
        return area;
    }
}
