package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ClassBD implements InterfBD{
    private static List<Student> studentsList;
    private static Map<String, Student> studMap;

    public void ReadFileInList(String path) throws IOException {
        studentsList = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(path));){
            while(buffer.ready()) {
                StringTokenizer tokenizer = new StringTokenizer(buffer.readLine().toString(), " ;_");
                String[] tmp = new String[5];
                Integer ind = 0;
                while(tokenizer.hasMoreTokens()) {
                    tmp[ind] = tokenizer.nextElement().toString();
                    ind++;
                }
                String StudentFirstName = tmp[1];
                String StudentSecondName = tmp[0];
                Double studentAmount = Double.valueOf(tmp[2]);
                String ID = tmp[3];
                String FileName = tmp[4];
                studentsList.add(new Student(StudentFirstName, StudentSecondName, studentAmount, ID, FileName));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ReadFileInMap(String path) throws IOException {
        ReadFileInList(path);
        studMap = new HashMap<String, Student>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(path));){
            while(buffer.ready()) {
                StringTokenizer tokenizer = new StringTokenizer(buffer.readLine().toString(), " ;_");
                String[] tmp = new String[5];
                Integer ind = 0;
                while (tokenizer.hasMoreTokens()) {
                    tmp[ind] = tokenizer.nextElement().toString();
                    ind++;
                }
                String StudentFirstName = tmp[1];
                String StudentSecondName = tmp[0];
                Double studentAmount = Double.valueOf(tmp[2]);
                String ID = tmp[3];
                String FileName = tmp[4];
                studMap.put(ID, new Student(StudentFirstName, StudentSecondName, studentAmount, "temp", FileName));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteName() throws IOException {
        for(int i = 0; i < studentsList.size(); i++) {
            boolean hasDigits = false;
            String word = new String(studentsList.get(i).studentLastName);
            String wordId = new String(studentsList.get(i).studentID);
            for(int j = 0; j < word.length() && !hasDigits; i++) {
                if(Character.isDigit(word.charAt(i))) {
                    hasDigits = true;
                }
            }
            if(hasDigits) {
                studMap.remove(wordId);
            }
        }
        WriteFile("rezdel.txt");
    }

    public void Sort() throws IOException {
        Collections.sort(studentsList, (student1, student2) -> student1.compareTo(student2));
        WriteFile("rezsort.txt");
    }

    public void WriteFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(Entry<String, Student> item : studMap.entrySet()) {
            writer.write(item.getKey() + " ");
            writer.write(item.getValue().toString() + "\n");
        }
        writer.close();
    }

    private static Node getLanguage(Document doc, String firstName, String lastName, String amount, String ID, String fileName) {
        Element line = doc.createElement("InfoOfStudent");
        line.appendChild(getLanguageElements(doc, line, "firstName", firstName));
        line.appendChild(getLanguageElements(doc, line, "lastName", lastName));
        line.appendChild(getLanguageElements(doc, line, "amount", amount));
        line.appendChild(getLanguageElements(doc, line, "ID", ID));
        line.appendChild(getLanguageElements(doc, line, "fileName", fileName));
        return line;
    }

    private static Node getLanguageElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public void WriteFileInXML() throws Throwable {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();// Создаем объект для построения документа
        Document document = builder.newDocument();

        Element element = document.createElement("Students");// Создаем корневой элемент "Students"

        TransformerFactory transformerFactory = TransformerFactory.newInstance();// Создаем фабрику трансформаторов для преобразования документа в файл
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//устанавливает свойство вывода INDENT в значение "yes", что приведет к тому, что XML-документ будет отформатирован с отступами для улучшения читаемости.

        DOMSource source = new DOMSource(document);// Создаем источник данных из документа
        StreamResult result = new StreamResult(new File("rezXML.xml"));

        document.appendChild(element);// Добавляем корневой элемент в конец документа
        for(int i = 0; i < studentsList.size(); ++i) {
            element.appendChild(ClassBD.getLanguage(document, studentsList.get(i).studentFirstName, studentsList.get(i).studentLastName, Double.toString(studentsList.get(i).studentAmount), studentsList.get(i).studentID, studentsList.get(i).fileName));
        }//вызовет метод getStudentFirstName() для объекта Student, который находится на позиции i в списке studentsList, и вернет имя этого студента
        transformer.transform(source, result);// Преобразуем документ в файл с помощью трансформатора
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public Map<String, Student> getStudMap() {
        return studMap;
    }
    @Override
    public void StartMethods() {

        try {
            ClassBD Main = new ClassBD();
            Main.ReadFileInList("test1.txt");
            Main.ReadFileInMap("test1.txt");
            Main.WriteFile("rezult1.txt");
            XMLParser parser = new XMLParser();
            //XMLParser.setOrders(ClassBD.getStudentsList());
            parser.WriteXMLFile("rezXML.xml");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
}}