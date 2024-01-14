package org.example;

import java.util.*;
import java.lang.String;

public class Main {


    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Settings InputFileSettings = new Settings();
        Settings OutputFileSettings = new Settings();

        try {
            Console.SetInputFileSettings(input, InputFileSettings);
            Console.ReadInputFile(InputFileSettings, input);

            Console.ParseMathematicalExpressions(InputFileSettings, OutputFileSettings, input);

            Console.SetOutputFileSettings(input, OutputFileSettings);
            Console.WriteOutputFile(OutputFileSettings, input);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}