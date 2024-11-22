package edu.penzgtu.calculatestrategy.impl;

import edu.penzgtu.calculatestrategy.CalculateStrategy;
import edu.penzgtu.function.Function;

// Метод Симпсона
public class SimpsonCalculateStrategy implements CalculateStrategy {
    @Override
    public double calculate(double x, double y, double step) {
        double yNext = Function.getY(x + step);
        double yMid = Function.getY((x + x + step) / 2);
        return Math.abs((y + 4 * yMid + yNext) * step / 6);
    }
}
