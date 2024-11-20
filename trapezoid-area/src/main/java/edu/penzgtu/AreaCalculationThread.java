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
        double localSimpsonArea = 0;

        for (int i = 0; i < numIntervals; i++) {
            double x = start + i * step;
            double y = function(x);
            double yNext = function(x + step);

            // Метод прямоугольников
            double currentRectangleArea = Math.abs(y * step);
            localRectanglesArea += currentRectangleArea;

            // Метод трапеций
            double currentTrapezoidArea = Math.abs((y + yNext) * step / 2);
            localTrapezoidsArea += currentTrapezoidArea;

            // Метод Симпсона
            double yMid = function((x + x + step) / 2);
            double currentSimpsonArea = Math.abs((y + 4 * yMid + yNext) * step / 6);
            localSimpsonArea += currentSimpsonArea;
        }

        areas.add(localRectanglesArea);
        areas.add(localTrapezoidsArea);
        areas.add(localSimpsonArea);

        System.out.printf("Thread: I am counting [%f; %f) with step %f\n", start, end, step);
        return areas;
    }
}
