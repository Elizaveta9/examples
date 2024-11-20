package edu.penzgtu;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Enter <numberOfThreads> as arguments.");
            return;
        }
        int numberOfThreads;
        try {
            numberOfThreads = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of threads");
            return;
        }
        long startTime = System.nanoTime();

        double area = 0;
        try {
            area = AreaCalculator.calculate(numberOfThreads);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        System.out.printf("Area: %f\n", area);
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1000000);
    }
}
