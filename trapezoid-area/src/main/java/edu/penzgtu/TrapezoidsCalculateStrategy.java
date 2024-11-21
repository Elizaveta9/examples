package edu.penzgtu;

// Метод трапеций
public class TrapezoidsCalculateStrategy implements CalculateStrategy {
    @Override
    public double calculate(double x, double y, double step) {
        double yNext = Function.getY(x + step);
        return Math.abs((y + yNext) * step / 2);
    }
}
