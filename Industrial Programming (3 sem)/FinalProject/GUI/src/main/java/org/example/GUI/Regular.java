package org.example.gui;

import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

public class Regular extends Builder {

    protected static boolean hasPrecedence(char operator1, char operator2) {
        return (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-');
    }

    protected static double performOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+' -> {
                return operand1 + operand2;
            }
            case '-' -> {
                return operand1 - operand2;
            }
            case '*' -> {
                return operand1 * operand2;
            }
            case '/' -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return operand1 / operand2;
            }
        }
        throw new IllegalArgumentException("Unknown operator: " + operator);
    }

    double calculate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            String ch = Character.toString(expression.charAt(i));
            if (ch.equals(" ")) {
                i++;
                continue;
            } else if (ch.matches("\\d+(\\.\\d+)?") || ch.equals(".")) {
                StringBuilder numBuilder = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numBuilder.append(expression.charAt(i));
                    i++;
                }
                double number = Double.parseDouble(numBuilder.toString());
                numbers.push(number);
            } else if (ch.matches("[+\\-*/]")) {
                while (!operators.isEmpty() && !hasPrecedence(ch.charAt(0), operators.peek().charAt(0)) && !operators.peek().equals("(")) {
                    double operand2 = numbers.pop();
                    double operand1 = numbers.pop();
                    String op = operators.pop();
                    double result = performOperation(op.charAt(0), operand1, operand2);
                    numbers.push(result);
                }
                operators.push(ch);
                i++;
            } else if (ch.equals("(")) {
                operators.push(ch);
                i++;
            } else if (ch.equals(")")) {
                while (!operators.isEmpty() && operators.peek().charAt(0) != '(') {
                    double operand2 = numbers.pop();
                    double operand1 = numbers.pop();
                    String op = operators.pop();
                    double result = performOperation(op.charAt(0), operand1, operand2);
                    numbers.push(result);
                }
                operators.pop();
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        while (!operators.isEmpty()) {
            double operand2 = numbers.pop();
            double operand1 = numbers.pop();
            String op = operators.pop();
            double result = performOperation(op.charAt(0), operand1, operand2);
            numbers.push(result);
        }

        return numbers.pop();
    }

    @Override
    void ParseTxt(Settings InputFileSettings, Settings OutputFileSettings) throws Exception {
        String[] lines = InputFileSettings.txt_info.split("\n");
        Vector<String> vec = new Vector<>(Arrays.asList(lines));

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < vec.size(); i++) {
            vec.set(i, vec.get(i).replace(" ", ""));
            vec.set(i, vec.get(i).replace("\n", ""));
            vec.set(i, vec.get(i).replace("\r", ""));
            Regular p = new Regular();
            s.append(String.valueOf(p.calculate(vec.get(i)))).append("\n");
        }

        OutputFileSettings.txt_info = String.valueOf(s);
        OutputFileSettings.byte_info = Operations.StringToBytes(OutputFileSettings.txt_info);

    }
}