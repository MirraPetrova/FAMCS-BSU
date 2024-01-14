package org.example.gui;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class MatchParserByStack extends Builder {
    private final HashMap<String, Double> variables;

    public MatchParserByStack() {
        variables = new HashMap<String, Double>();
    }

    public void setVariable(String variableName, Double variableValue) {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) throws Exception {
        Double result = 0.0;
        if (!variables.containsKey(variableName)) {
            throw new Exception("Error: Try get unexists variable '" + variableName + "'");
        } else {
            result = variables.get(variableName);
        }
        return result;
    }

    private Total PlusMinus(String s) throws Exception {
        Total current = MulDiv(s);
        double acc = current.acc;

        while (!current.rest.isEmpty()) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = MulDiv(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Total(acc, current.rest);
    }

    private Total Bracket(String s) throws Exception {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Total r = PlusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new Exception("Error: not close bracket");
            }
            return r;
        }
        return FunctionVariable(s);
    }

    private Total FunctionVariable(String s) throws Exception {
        StringBuilder f = new StringBuilder();
        int i = 0;
        while (i < s.length() && (Character.isLetter(s.charAt(i)) || (Character.isDigit(s.charAt(i)) && i > 0))) {
            f.append(s.charAt(i));
            i++;
        }
        if (!f.isEmpty()) {
            if (s.length() > i && s.charAt(i) == '(') {
                Total r = Bracket(s.substring(f.length()));
                return processFunction(f.toString(), r);
            } else {
                return new Total(getVariable(f.toString()), s.substring(f.length()));
            }
        }
        return Num(s);
    }

    private Total MulDiv(String s) throws Exception {
        Total current = Bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.isEmpty()) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Total right = Bracket(next);

            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }

            current = new Total(acc, right.rest);
        }
    }

    private Total Num(String s) throws Exception {
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            s = s.substring(1);
        }
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            if (s.charAt(i) == '.' && ++dot_cnt > 1) {
                throw new Exception("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if (i == 0) {
            throw new Exception("can't get valid number in '" + s + "'");
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if (negative) dPart = -dPart;
        String restPart = s.substring(i);

        return new Total(dPart, restPart);
    }

    private Total processFunction(String func, Total r) throws Exception {
        switch (func) {
            case "sin" -> {
                return new Total(Math.sin(Math.toRadians(r.acc)), r.rest);
            }
            case "cos" -> {
                return new Total(Math.cos(Math.toRadians(r.acc)), r.rest);
            }
            case "tan" -> {
                return new Total(Math.tan(Math.toRadians(r.acc)), r.rest);
            }
            default -> throw new Exception("function '" + func + "' is not defined");
        }
    }

    public double Parse(String s) throws Exception {
        Total result = PlusMinus(s);
        if (!result.rest.isEmpty()) {
            throw new Exception("Error: can't fully parse. Rest: " + result.rest);
        }
        return result.acc;
    }

    @Override
    public void ParseTxt(Settings InputFileSettings, Settings OutputFileSettings) throws Exception {
        String[] lines = InputFileSettings.txt_info.split("\n");
        Vector<String> vec = new Vector<>(Arrays.asList(lines));

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < vec.size(); i++) {
            vec.set(i, vec.get(i).replace(" ", ""));
            vec.set(i, vec.get(i).replace("\n", ""));
            vec.set(i, vec.get(i).replace("\r", ""));
            MatchParserByStack p = new MatchParserByStack();
            s.append(String.valueOf(p.Parse(vec.get(i)))).append("\n");
        }

        OutputFileSettings.txt_info = String.valueOf(s);
        OutputFileSettings.byte_info = Operations.StringToBytes(OutputFileSettings.txt_info);
    }

}