package edu.penzgtu;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AreaCalculator {
    public static List<Double> calculate(int numberOfThreads) throws ExecutionException, InterruptedException {
        int minBound = 0;
        int maxBound = 2500;
//        int numIntervals = maxBound - minBound;
        int numIntervals = 1_000_000_000;
        double intervalWidth = (maxBound - minBound) / (double) numberOfThreads;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Future<List<Double>>[] futureArray = new Future[numberOfThreads];

        // Передача потокам данных
        for (int i = 0; i < numberOfThreads; i++) {
            double start = minBound + i * intervalWidth; // начало чанка для одного потока
            double end = start + intervalWidth; // конец чанка
            futureArray[i] = executorService.submit(new AreaCalculationThread(start, end, numIntervals / numberOfThreads));
        }

        double totalRectanglesArea = 0;
        double totalTrapezoidsArea = 0;
        double totalSimpsonArea = 0;
        for (Future<List<Double>> future : futureArray) {
            List<Double> areas = future.get();
            totalRectanglesArea += areas.get(0);
            totalTrapezoidsArea += areas.get(1);
            totalSimpsonArea += areas.get(2);
        }

        executorService.shutdown();
        return List.of(totalRectanglesArea, totalTrapezoidsArea, totalSimpsonArea);
    }
}
