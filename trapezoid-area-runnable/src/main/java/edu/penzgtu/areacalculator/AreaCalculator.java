package edu.penzgtu.areacalculator;

import edu.penzgtu.calculatestrategy.CalculateStrategy;

public class AreaCalculator {
    public static Double calculate(int numberOfThreads, CalculateStrategy calculateStrategy) throws InterruptedException {
        int minBound = 0; // Левая граница диапазона
        int maxBound = 2500; // Правая граница диапазона
        int numberOfIntervals = 1_000_000_000; // Количество интервалов между границами
        double intervalWidth = (maxBound - minBound) / (double) numberOfThreads; // Ширина интервала

        // Создание массивов потоков и их результатов
        Thread[] threads = new Thread[numberOfThreads];
        AreaCalculationRunnable[] runnables = new AreaCalculationRunnable[numberOfThreads];

        // Деление графика на области. На каждый поток своя область
        for (int i = 0; i < numberOfThreads; i++) {
            double start = minBound + i * intervalWidth; // Начало области для одного потока
            double end = start + intervalWidth; // Конец области
            runnables[i] = new AreaCalculationRunnable(start, end, numberOfIntervals / numberOfThreads, calculateStrategy);
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }

        // Ожидание завершения всех потоков
        for (Thread thread : threads) {
            thread.join();
        }

        // Суммирование полученных данных
        double totalArea = 0;
        for (AreaCalculationRunnable runnable : runnables) {
            totalArea += runnable.getArea();
        }

        return totalArea;
    }
}