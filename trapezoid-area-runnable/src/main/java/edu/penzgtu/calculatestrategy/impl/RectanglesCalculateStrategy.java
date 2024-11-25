package edu.penzgtu.calculatestrategy.impl;

import edu.penzgtu.calculatestrategy.CalculateStrategy;

// Метод прямоугольников
public class RectanglesCalculateStrategy implements CalculateStrategy {
    @Override
    public double calculate(double x, double y, double step) {
        return Math.abs(y * step);
    }
}
