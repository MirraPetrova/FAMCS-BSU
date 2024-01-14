package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStreamReader buf = new InputStreamReader(System.in);
        BufferedReader In = new BufferedReader(buf);

        ArrayList<Company> txt = new ArrayList<>(WorkWithTXT.ReadFromFileTXT("in_file.txt"));
        WorkWithTXT.WriteInFileTXT(txt);
        /*System.out.println("Enter method name for searching");
        String findMethod = In.readLine();
        var findClients= new CompanyList();
        var k = findClients.FindClientByMethod(findMethod);
        //System.out.println(k);*/


        ArrayList<Company> json = new ArrayList<>(WorkWithJSON.ReadFromFileJSON("in_file.json"));
        WorkWithJSON.WriteInFileJSON(json);
        
        ArrayList<Company> xml = new ArrayList<>(WorkWithXML.ReadFromFileXML("in_file.xml"));
        WorkWithXML.WriteInFileXML(xml);

        // Print using lambda
        System.out.println("--- Method 1 ---");
        for(Company p : txt) { System.out.println(p.getNameOfGoods() + " " + p.getTransportationMethod() + " " + p.getWeight()); }
        System.out.println("\n");

        System.out.println("--- Method 2 ---");
        txt.forEach(n -> System.out.println(n.getNameOfGoods() + " " + n.getTransportationMethod() + " " + n.getWeight()));
        System.out.println("\n");

        // Print using iterator
        System.out.println("--- Method 3 ---");
        Iterator<Company> iter = txt.iterator();
        while(iter.hasNext()) {
            Company t = iter.next();
            System.out.println(t.getNameOfGoods() + " " + t.getTransportationMethod() + " " + t.getWeight());
        }


        System.out.println("Enter amount of addings");
        int AmountAddings = Integer.parseInt(In.readLine());
        var clients = new CompanyList();
        for(int i=0;i<AmountAddings;i++) {
            System.out.println("Enter name of goods for adding");
            String newNameOfGoods = In.readLine();
            System.out.println("Enter transportation method for adding");
            String newTransportationMethod = In.readLine();
            System.out.println("And enter weight for adding");
            String newWeight = In.readLine();
            /*Company client = new Company(newNameOfGoods, newTransportationMethod, newWeight);
            try (FileWriter file = new FileWriter("out_file.json")) {
                file.write(client.toJSONObject().toJSONString());
                System.out.println("Successfully added to file.");
            }*/
        clients.Add(new Company(newNameOfGoods, newTransportationMethod, newWeight));}
        //clients.WriteToJsonFile("out_file.json");}
        clients.PrintClients();
        var s = clients.FilterByMethodName("Air");
        System.out.println();
        s.PrintClients();

        var res = clients.GroupByMethodName("Air");
        System.out.println(res);
    }
}