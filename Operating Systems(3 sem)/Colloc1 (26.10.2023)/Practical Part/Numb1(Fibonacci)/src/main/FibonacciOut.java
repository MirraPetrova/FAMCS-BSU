package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FibonacciOut {

    public static List<Integer> FibonacciCalc(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive number");
        }

        List<Integer> FibonacciNumbs = new ArrayList<>();

        int previous = 0;
        int current = 1;

        FibonacciNumbs.add(previous);

        for (int i = 1; i < n; i++) {
            FibonacciNumbs.add(current);
            int next = previous + current;
            previous = current;
            current = next;
        }

        return FibonacciNumbs;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter n: ");
            int n = scanner.nextInt();
            List<Integer> FibonacciNumbs = FibonacciCalc(n);
            System.out.println("First " + n + " numbers Fibonacci: " + FibonacciNumbs);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
