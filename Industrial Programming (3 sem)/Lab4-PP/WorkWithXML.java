package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class WorkWithXML {
    private static Node getLanguage(Document doc, String NameOfGoods, String TransportationMethod, String Weight) {
        Element client = doc.createElement("Client");
        client.appendChild(getLanguageElements(doc, client, "NameOfGoods", NameOfGoods));
        client.appendChild(getLanguageElements(doc, client, "TransportationMethod", TransportationMethod));
        client.appendChild(getLanguageElements(doc, client, "Weight", Weight));
        return client;
    }

    private static Node getLanguageElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static ArrayList<Company> ReadFromFileXML(String filename) throws FileNotFoundException, XMLStreamException {
        ArrayList<String> r = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(filename));
        while(reader.hasNext()) {
            if(reader.next() == XMLStreamConstants.CHARACTERS) {
                String tmp = reader.getText().replaceAll("  ", "").replaceAll("\n", "");
                if(tmp != "") {
                    r.add(tmp);
                }
            }
        }

        ArrayList<Company> result = new ArrayList<>();
        for(int i = 0; i < r.size(); i += 3) {
            result.add(new Company(r.get(i), r.get(i + 1), r.get(i + 2)));
        }
        return result;
    }

    public static void WriteInFileXML(ArrayList<Company> Clients) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element element = document.createElement("Clients");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("out_file.xml"));

        document.appendChild(element);
        element.appendChild(WorkWithXML.getLanguage(document, Clients.get(0).getNameOfGoods(), Clients.get(0).getTransportationMethod(), Clients.get(0).getWeight()));
        for(int i = 1; i < Clients.size(); ++i) {
            element.appendChild(WorkWithXML.getLanguage(document, Clients.get(i).getNameOfGoods(), Clients.get(i).getTransportationMethod(), Clients.get(i).getWeight()));
        }
        transformer.transform(source, result);
    }
}
