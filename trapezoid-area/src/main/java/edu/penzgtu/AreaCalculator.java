package edu.penzgtu;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AreaCalculator {
    public static Double calculate(int numberOfThreads, CalculateStrategy calculateStrategy) throws ExecutionException, InterruptedException {
        int minBound = 0; // Левая граница диапазона
        int maxBound = 2500; // Правая граница диапазона
        int numberOfIntervals = 1_000_000_000; // Количество интервалов между границами
        double intervalWidth = (maxBound - minBound) / (double) numberOfThreads; // Ширина интервала

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Future<Double>[] futureArray = new Future[numberOfThreads];

        // Деление графика на области. На каждый поток своя область
        for (int i = 0; i < numberOfThreads; i++) {
            double start = minBound + i * intervalWidth; // Начало области для одного потока
            double end = start + intervalWidth; // Конец области
            futureArray[i] = executorService.submit(new AreaCalculationThread(start, end, numberOfIntervals / numberOfThreads, calculateStrategy));
        }

        // Суммирование полученных данных
        double totalArea = 0;
        for (Future<Double> future : futureArray) {
            Double area = future.get(); // Получение из потока List'a с площадями отведенной ему области
            totalArea += area;
        }

        executorService.shutdown();
        return totalArea;
    }
}
