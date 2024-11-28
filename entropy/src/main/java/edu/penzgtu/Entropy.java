package edu.penzgtu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

public class Entropy {
    public static double calculateFileEntropy(String filePath, int numberOfThreads) throws IOException, ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Map<Byte, Integer> byteFrequencies = new ConcurrentHashMap<>();
            Future<Map<Byte, Integer>>[] futuresMap = new Future[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++) {
                futuresMap[i] = executor.submit(new ReadFileThread(fis, byteFrequencies));
            }

            // Подсчет общего количества байт
            int totalBytes = 0;
            for (Future<Map<Byte, Integer>> future : futuresMap) {
                Map<Byte, Integer> frequencies = future.get();
                totalBytes += frequencies.values().stream().mapToInt(Integer::intValue).sum();
            }

            // Вычисление энтропии
            double entropy = 0.0;
            for (int frequency : byteFrequencies.values()) {
                double probability = (double) frequency / totalBytes;
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
            return entropy;
        } finally {
            executor.shutdown();
        }
    }
}

