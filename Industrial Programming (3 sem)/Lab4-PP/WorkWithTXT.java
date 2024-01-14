package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class WorkWithTXT {
    public static ArrayList<Company> ReadFromFileTXT(String filename) {
        ArrayList<Company> Clients = new ArrayList<>();
        try(FileReader reader = new FileReader(filename)) {
            Scanner scanner = new Scanner(reader);
            while(scanner.hasNextLine()) {
                Clients.add(new Company(scanner.nextLine(), scanner.nextLine(), scanner.nextLine()));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Clients;
    }

    public static void WriteInFileTXT(ArrayList<Company> Clients) {
        try(FileWriter writer = new FileWriter("out_file.txt")) {
            for(int i = 0; i < Clients.size(); ++i) {
                writer.write(Clients.get(i).getNameOfGoods() + " " + Clients.get(i).getTransportationMethod() + " " + Clients.get(i).getWeight() + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}