package edu.penzgtu;

import java.util.List;
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

        /*
            Подсчитанные площади
            0 - методом прямоугольников
            1 - методом трапеций
            2 - методом Симпсона
         */
        List<Double> areas;

        long startTime = System.nanoTime(); // Запуск таймера
        try {
            areas = AreaCalculator.calculate(numberOfThreads);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime(); // Конец таймера

        System.out.printf("Area Rectangles: %f\n", areas.get(0));
        System.out.printf("Area Trapezoids: %f\n", areas.get(1));
        System.out.printf("Area Simpson: %f\n", areas.get(2));
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1000000);
    }
}
