package edu.penzgtu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AreaCalculationThread implements Callable<List<Double>> {

    private double start; // Начальное значение
    private double end; // Конечное значение
    private int numberOfIntervals; // Количество интервалов на данной области

    public AreaCalculationThread(double start, double end, int numberOfIntervals) {
        this.start = start;
        this.end = end;
        this.numberOfIntervals = numberOfIntervals;
    }

    // Функция, образующая кривую
    private double function(double x) {
        return -Math.pow((x - 1500), 2) / 4000 + 1000;
    }

    // Логика потока
    @Override
    public List<Double> call() {
        List<Double> areas = new ArrayList<>();
        double step = (end - start) / numberOfIntervals;

        double localRectanglesArea = 0; // Площадь области на данном потоке (метод прямоугольников)
        double localTrapezoidsArea = 0; // Площадь области на данном потоке (метод трапеций)
        double localSimpsonArea = 0; // Площадь области на данном потоке (метод Симпсона)

        for (int i = 0; i < numberOfIntervals; i++) {
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
