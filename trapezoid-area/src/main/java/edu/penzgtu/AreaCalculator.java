package edu.penzgtu;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AreaCalculator {
    public static List<Double> calculate(int numberOfThreads) throws ExecutionException, InterruptedException {
        int minBound = 0; // Левая граница диапазона
        int maxBound = 2500; // Правая граница диапазона
        int numberOfIntervals = 1_000_000_000; // Количество интервалов между границами
        double intervalWidth = (maxBound - minBound) / (double) numberOfThreads; // Ширина интервала

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Future<List<Double>>[] futureArray = new Future[numberOfThreads];

        // Деление графика на области. На каждый поток своя область
        for (int i = 0; i < numberOfThreads; i++) {
            double start = minBound + i * intervalWidth; // Начало области для одного потока
            double end = start + intervalWidth; // Конец области
            futureArray[i] = executorService.submit(new AreaCalculationThread(start, end, numberOfIntervals / numberOfThreads));
        }

        // Суммирование полученных данных
        double totalRectanglesArea = 0; // Площадь, полученная методом прямоугольников
        double totalTrapezoidsArea = 0; // Площадь, полученная методом трапеций
        double totalSimpsonArea = 0; // Площадь, полученная методом Симпсона
        for (Future<List<Double>> future : futureArray) {
            List<Double> areas = future.get(); // Получение из потока List'a с площадями отведенной ему области
            totalRectanglesArea += areas.get(0);
            totalTrapezoidsArea += areas.get(1);
            totalSimpsonArea += areas.get(2);
        }

        executorService.shutdown();
        return List.of(totalRectanglesArea, totalTrapezoidsArea, totalSimpsonArea);
    }
}
