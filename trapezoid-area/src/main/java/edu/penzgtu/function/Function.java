package edu.penzgtu.function;

// Функция, образующая кривую
public class Function {
    public static double getY(double x) {
        return -Math.pow((x - 1500), 2) / 4000 + 1000;
    }
}
