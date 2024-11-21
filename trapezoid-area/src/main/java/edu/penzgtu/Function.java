package edu.penzgtu;

// Функция, образующая кривую
public class Function {
    public static double getY(double x) {
        return -Math.pow((x - 1500), 2) / 4000 + 1000;
    }
}
