package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AreaCalculationThread implements Callable<List<Double>> {

    private double start;
    private double end;
    private int numIntervals;

    public AreaCalculationThread(double start, double end, int numIntervals) {
        this.start = start;
        this.end = end;
        this.numIntervals = numIntervals;
    }

    private double function(double x) {
        return -Math.pow((x - 1500), 2) / 4000 + 1000;
    }

    @Override
    public List<Double> call() {
        List<Double> areas = new ArrayList<>();
        double step = (end - start) / numIntervals;
        double localRectanglesArea = 0;
        double localTrapezoidsArea = 0;

        for (int i = 0; i < numIntervals; i++) {
            double x = start + i * step;
            double y = function(x);

            // Метод прямоугольников
            double currentRectangleArea = Math.abs(y * step);
            localRectanglesArea += currentRectangleArea;

            // Метод трапеций
            double currentTrapezoidArea = Math.abs((y + function(x + 1)) * step / 2);
            localTrapezoidsArea += currentTrapezoidArea;
        }

        areas.add(localRectanglesArea);
        areas.add(localTrapezoidsArea);

        System.out.printf("I am counting [%f; %f) with step %f\n", start, end, step);
        return areas;
    }
}
