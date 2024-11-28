package edu.penzgtu;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        // Проверка аргументов
        if (args.length < 2) {
            System.out.println("Enter <filePath> <numberOfThreads> as arguments.");
            return;
        }
        String filePath = args[0];
        int numberOfThreads;
        try {
            numberOfThreads = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of threads: " + args[1]);
            return;
        }

        // Энтропия
        try {
            System.out.println("Started analysing file " + filePath);
            long startTime = System.nanoTime();
            double entropy = Entropy.calculateFileEntropy(filePath, numberOfThreads);
            long endTime = System.nanoTime();
            System.out.printf("Entropy: %.4f\n", entropy);
            System.out.println("Execution time (ms): " + (endTime - startTime) / 1000000);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
}
