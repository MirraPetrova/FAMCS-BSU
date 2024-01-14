package org.example;

public class Main {
    public static void main(String[] args) throws Throwable {
        ClassBD Main = new ClassBD();
        Main.ReadFileInList("test1.txt");
        Main.ReadFileInMap("test1.txt");
        //Main.deleteName();
        Main.Sort();
        Main.WriteFileInXML();
        //ClassBD classBD = new ClassBD();
        //classBD.StartMethods();
    }
}
