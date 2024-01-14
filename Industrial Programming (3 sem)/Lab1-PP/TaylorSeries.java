package org.example;

public class TaylorSeries {
    public double calculate(double x, double epsilon) {
        double sum = x;
        double term = x;
        int n = 1;
        while (Math.abs(term) >= epsilon) {
            term *= x * x / ((2 * n) * (2 * n + 1));
            sum += term;
            n++;
        }
        return sum;
    }
}
