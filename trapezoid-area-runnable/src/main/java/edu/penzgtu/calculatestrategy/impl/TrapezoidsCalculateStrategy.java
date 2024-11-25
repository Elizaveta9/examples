package edu.penzgtu.calculatestrategy.impl;

import edu.penzgtu.calculatestrategy.CalculateStrategy;
import edu.penzgtu.function.Function;

// Метод трапеций
public class TrapezoidsCalculateStrategy implements CalculateStrategy {
    @Override
    public double calculate(double x, double y, double step) {
        double yNext = Function.getY(x + step);
        return Math.abs((y + yNext) * step / 2);
    }
}
