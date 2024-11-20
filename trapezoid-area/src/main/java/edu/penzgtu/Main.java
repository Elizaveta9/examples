package edu.penzgtu;

import java.util.List;
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

        List<Double> areas;
        try {
            areas = AreaCalculator.calculate(numberOfThreads);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        System.out.printf("Area Rectangles: %f\n", areas.get(0));
        System.out.printf("Area Trapezoids: %f\n", areas.get(1));
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1000000);
    }
}
