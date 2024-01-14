package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WorkWithJSON {
    public static ArrayList<Company> ReadFromFileJSON(String filename) {
        ArrayList<Company> Clients = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray list = (JSONArray) jsonParser.parse(new FileReader(filename));
            for(int i = 0; i < list.size(); ++i) {
                JSONObject object = (JSONObject) list.get(i);
                String NameOfGoods = (String) object.get("NameOfGoods");
                String TransportationMethod = (String) object.get("TransportationMethod");
                String Weight = (String) object.get("Weight");
                Company client = new Company(NameOfGoods, TransportationMethod, Weight);
                Clients.add(client);
            }
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            throw new RuntimeException(ex);
        }
        return Clients;
    }

    public static void WriteInFileJSON(ArrayList<Company> p) throws IOException {
        FileWriter writer = new FileWriter("out_file.json");
        JSONArray obj = new JSONArray();
        writer.write("[ ");
        for(int i = 0; i < p.size(); ++i) {
            if(i + 1 == p.size()) {
                JSONObject object = new JSONObject();
                object.put("NameOfGoods", p.get(i).getNameOfGoods());
                object.put("TransportationMethod", p.get(i).getTransportationMethod());
                object.put("Weight", p.get(i).getWeight());
                writer.write(object.toJSONString());
                break;
            }
            JSONObject object = new JSONObject();
            object.put("NameOfGoods", p.get(i).getNameOfGoods());
            object.put("TransportationMethod", p.get(i).getTransportationMethod());
            object.put("Weight", p.get(i).getWeight());
            writer.write(object.toJSONString() + ", ");
        }
        writer.write(" ]");
        writer.flush();
    }
}
