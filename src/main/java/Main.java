import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String pathFile = "data.xml";
        String fileJson = "data2.json";
        Document document = buildDocument(pathFile);
        List<Employee> employeeListPeople = buildNoteList(document);
        List<String> stringListJson = listToJson(employeeListPeople);
        writeString(stringListJson, fileJson);

    }

    private static List<String> listToJson(List<Employee> list) {
        List<String> listJson = new ArrayList<>();
        for (Employee employee : list) {
            String gson = new GsonBuilder().create().toJson(employee, Employee.class);
            listJson.add(gson);
        }
        return listJson;
    }

    private static void writeString(List<String> stringListJson, String fileJson) {
        try (FileWriter fileWriter = new FileWriter(fileJson)) {
            fileWriter.write(String.valueOf(stringListJson));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> buildNoteList(Document document) {
        List<Employee> employeeList = new ArrayList<>();
        NodeList nodeList = document
                .getFirstChild()
                .getChildNodes();

        Node node = null;
        long id = 0;
        String firstName = null;
        String lastName = null;
        String country = null;
        long age = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {

            if (nodeList.item(i).getNodeName().equals("employee")) {
                node = nodeList.item(i);
            }
            if (node == null) continue;
            NodeList nodeListPeople = node.getChildNodes();

            for (int j = 0; j < nodeListPeople.getLength(); j++) {

                if (nodeListPeople.item(j).getNodeName().equals("id")) {
                    id = Long.parseLong(nodeListPeople.item(j).getTextContent());
                }
                if (nodeListPeople.item(j).getNodeName().equals("firstName")) {
                    firstName = nodeListPeople.item(j).getTextContent();
                }
                if (nodeListPeople.item(j).getNodeName().equals("lastName")) {
                    lastName = nodeListPeople.item(j).getTextContent();
                }
                if (nodeListPeople.item(j).getNodeName().equals("country")) {
                    country = nodeListPeople.item(j).getTextContent();

                }
                if (nodeListPeople.item(j).getNodeName().equals("age")) {
                    age = Long.parseLong(nodeListPeople.item(j).getTextContent());
                }
            }
            Employee employee = new Employee(id, firstName, lastName, country, (int) age);
            employeeList.add(employee);
        }
        return employeeList;
    }

    private static Document buildDocument(String pathFile) {
        Document document = null;
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new File(pathFile));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return document;
    }
}
