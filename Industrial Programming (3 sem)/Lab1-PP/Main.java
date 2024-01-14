package org.example;
//package BigNumbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter x: ");
        //double x = scanner.nextDouble();
        double x = 25;
        System.out.print("Enter k: ");
        int k = scanner.nextInt();
        double epsilon = Math.pow(10, -k);
        TaylorSeries taylorSeries = new TaylorSeries();
        double result = taylorSeries.calculate(x, epsilon);
        System.out.printf("Result: %.6f\n", result);
    }


}