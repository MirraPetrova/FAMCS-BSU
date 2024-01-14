package org.example;

import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Formatter;
import java.math.BigInteger;
import java.math.BigDecimal;
public class MainFormat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter x: ");
        //double x = scanner.nextDouble();
        double x = 25;
        System.out.print("Enter k: ");
        int k = scanner.nextInt();

        BigDecimal e = BigDecimal.valueOf(10).pow(-k);
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term = BigDecimal.ONE;

        BigInteger fact = BigInteger.ONE;
        int n = 1;
        while (term.abs().compareTo(e) >= 0) {
            sum = sum.add(term);
            fact = fact.multiply(BigInteger.valueOf(2 * n - 1)).multiply(BigInteger.valueOf(2 * n));
            term = BigDecimal.valueOf(x).pow(2 * n - 1).divide(new BigDecimal(fact), k + 1, RoundingMode.HALF_UP);
            n++;
        }

        System.out.println("Sinh(" + x + ") = " + sum);

        double standardResult = Math.sinh(x);
        System.out.println("Standard result: " + standardResult);

        System.out.printf("%nFormatted output:%n");

        System.out.printf("Sinh(%f) = %+.5f%n", x, sum);

        Formatter formatter = new Formatter(System.out);

        System.out.println("Enter the Decimal Value: ");
        BigInteger intValue = scanner.nextBigInteger();


        formatter.format("Octal(8): %o%n", intValue);
        formatter.format("Hexadecimal(16): %x%n", intValue);


        double number = 12.8;
        int minWidth = 15;
        int precision = 2;

        System.out.printf("Число: %%" + minWidth + "." + (precision + 1) + "f%n", number);


        double negativeValue = -123.4;
        double doubleValue = 4.3;
        System.out.println(doubleValue);
        formatter.format("Отрицательное число с флагом '+' и точностью: %+10.2f%n", negativeValue);
        formatter.format("Число с флагом '0': %010.2f%n", doubleValue);
        formatter.format("Число с флагом '#': %.4e%n", doubleValue);

        formatter.close();
    }
}
