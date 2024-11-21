package edu.penzgtu;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        // Обязательный аргумент - количество потоков
        if (args.length != 1) {
            System.out.println("Enter <numberOfThreads> as arguments.");
            return;
        }

        // Обработка введёного аргумента. Если это не число - ошибка
        int numberOfThreads;
        try {
            numberOfThreads = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of threads");
            return;
        }

        double area;
        long startTime;
        long endTime;

        try {
            // Подсчет методом прямоугольников
            startTime = System.nanoTime();
            area = AreaCalculator.calculate(numberOfThreads, new RectanglesCalculateStrategy());
            endTime = System.nanoTime();
            printInfo("rectangles", area, startTime, endTime);

            // Подсчет методом трапеций
            startTime = System.nanoTime();
            area = AreaCalculator.calculate(numberOfThreads, new TrapezoidsCalculateStrategy());
            endTime = System.nanoTime();
            printInfo("trapezoids", area, startTime, endTime);

            // Подсчет методом Симпсона
            startTime = System.nanoTime();
            area = AreaCalculator.calculate(numberOfThreads, new SimpsonCalculateStrategy());
            endTime = System.nanoTime();
            printInfo("simpson", area, startTime, endTime);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printInfo(String method, double area, long startTime, long endTime) {
        System.out.printf("Area (%s): %f\n", method, area);
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1000000);
    }
}
