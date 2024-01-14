package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class CompanyList implements Iterable<Company>, Cloneable{
    public static ArrayList<Company> Clients;
    public CompanyList() {
        Clients = new ArrayList<>();
    }
    public void SortByMethod() {
        Collections.sort(Clients, new Company.GoodByMethodComparator());
    }
    public void SortByName() {
        Collections.sort(Clients);
    }
    public int BinarySearchByName(String name) {
        return Collections.binarySearch(Clients, new Company(name, null,null), Company::compareTo);
    }
    public void Add(Company b) {
        Clients.add(b);
    }
    public CompanyList FindClientByMethod(String methodName) {
        CompanyList clientsByMethod = new CompanyList();
        for (var client : Clients) {
            if (client.TransportationMethod.equals(methodName)) {
                clientsByMethod.Add(client);
            }
        }
        return clientsByMethod;
    }
    public Company Get(int index) {
        return Clients.get(index);
    }
    @Override
    public Iterator<Company> iterator() {
        return new CompanyListIterator();
    }
    public void WriteToJsonFile(String jsonFileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(Paths.get(jsonFileName));
        gson.toJson(this, writer);
        writer.close();
    }

    public void ReadFromJsonFile(String jsonFileName) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(jsonFileName));
        var clientList = gson.fromJson(reader, CompanyList.class);
        Clients = CompanyList.Clients;
        reader.close();
    }
    public CompanyList FilterByMethodName(String name) {
        var stream = Clients.stream();
        var res = stream.filter(client -> client.TransportationMethod.equals(name));

        var filteredClients = new CompanyList();
        Collections.addAll(filteredClients.Clients, res.toArray(Company[]::new));
        return filteredClients;
    }
    public void PrintClients() {
        var stream = Clients.stream();
        stream.forEach(System.out::println);
    }
    public Map<String, List<Company>> GroupByMethodName(String name) {
        var stream = Clients.stream();
        var res = stream.collect(Collectors.groupingBy(b -> b.TransportationMethod));
        return res;
    }
    public class CompanyListIterator implements Iterator<Company> {
        Company nextClient;
        int currentIndex;
        public CompanyListIterator() {
            nextClient = Clients.get(0);
            currentIndex = 0;
        }
        @Override
        public boolean hasNext() {
            return currentIndex < Clients.size();
        }

        @Override
        public Company next() {
            Company result = nextClient;
            if (++currentIndex < Clients.size()) {
                nextClient = Clients.get(currentIndex);
            }
            return result;
        }
    }
}
