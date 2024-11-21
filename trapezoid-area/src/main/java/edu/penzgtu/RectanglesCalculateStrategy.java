package edu.penzgtu;

// Метод прямоугольников
public class RectanglesCalculateStrategy implements CalculateStrategy {
    @Override
    public double calculate(double x, double y, double step) {
        return Math.abs(y * step);
    }
}
