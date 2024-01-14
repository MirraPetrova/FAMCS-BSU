package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
public class Company implements Comparable<Company>{
    public String NameOfGoods;
    public String TransportationMethod;
    public String Weight;

    /*Company(String NameOfGoods, String TransportationMethod, String Weight) {
        this.NameOfGoods = NameOfGoods;
        this.TransportationMethod = TransportationMethod;
        this.Weight = Weight;
    }*/

    public String getWeight() {
        return Weight;
    }

    public String getNameOfGoods() {
        return NameOfGoods;
    }

    public String getTransportationMethod() {
        return TransportationMethod;
    }

    public void setNameOfGoods(String nameOfGoods) {
        NameOfGoods = nameOfGoods;
    }

    public void setTransportationMethod(String transportationMethod) {
        TransportationMethod = transportationMethod;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
    public Company(String nameOfGoods, String transportationMethod, String weight) {
        NameOfGoods = nameOfGoods;
        TransportationMethod = transportationMethod;
        Weight = weight;
    }

    @Override
    public int compareTo(Company company) {
        return NameOfGoods.compareTo(company.NameOfGoods);
    }
    @Override
    public String toString() {
        return NameOfGoods+TransportationMethod+Weight;
    }
    static class GoodByMethodComparator implements Comparator<Company> {
        @Override
        public int compare(Company client1, Company client2) {
            return client1.TransportationMethod.compareTo(client2.TransportationMethod);
        }
    }
    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
    public JSONObject toJSONObject() {
        JSONObject clientJson = new JSONObject();
        clientJson.put("NameOfGoods", this.NameOfGoods);
        clientJson.put("TransportationMethod", this.TransportationMethod);
        clientJson.put("Weight", this.Weight);
        return clientJson;
    }

}