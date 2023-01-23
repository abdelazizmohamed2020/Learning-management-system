package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;
import org.example.entites.Courses;
import org.example.entites.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentFunctions {
    final static Path path = Paths.get("src/main/resources");
    final static Path txt = path.resolve("students.txt");
    final static Path csv = path.resolve("students.csv");
    final static Charset utf8 = Charset.forName("UTF-8");
    static String file=null;
   final static String location = "src/main/resources/studentCourseDetails.json";

    public static void convertXmlToCsv(File xmlFile, File csvFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        NodeList nodes = document.getDocumentElement().getChildNodes();

        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile));

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList childNodes = element.getChildNodes();
                StringBuilder csvLine = new StringBuilder();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        csvLine.append(childElement.getTextContent());
                        csvLine.append(",");
                    }
                }
                csvWriter.write(csvLine.toString());
                csvWriter.newLine();
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static void  convertTxtToCsv(File textFile) throws IOException {
        String data = FileUtils.readFileToString(textFile);
        data = data.replace("#", ",");
        data = data.replace("$", "\n");
        String[] newDate = data.split("\n");
        FileUtils.writeStringToFile(textFile, data);

        String finalData = "";
        for (int i = 0; i < newDate.length; i++) {
            if (i == 0) {
                finalData += ("id," + newDate[i]);
            } else {
                finalData += ("&" + i + "," + newDate[i]);
            }
        }
        finalData = finalData.replace("&", "\n");
        FileUtils.writeStringToFile(textFile, finalData);

        try (
                final Scanner scanner = new Scanner(Files.newBufferedReader(txt, utf8));
                final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, utf8, StandardOpenOption.CREATE_NEW))) {

            while (scanner.hasNextLine()) {
                pw.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printAllStudents(){

        try (
                final Scanner scanner = new Scanner(Files.newBufferedReader(txt, utf8));
                final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, utf8, StandardOpenOption.CREATE_NEW))) {
            System.out.println("-------------------------------");
            System.out.println("Current Student List");
            System.out.println("-------------------------------");

            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void printStudentsData(String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int id = 0;

            System.out.println("id  "+"    name       "+"    Grade    "+"         email         "+"                 address      "+"                   region              "+"        country");
            while (line != null) {
                if (id == 0) {

                    line = reader.readLine();
                    id++;
                    continue;
                }
                String[] values = line.split(",");
                String formattedLine = String.format("%-3s %-20s %-5s %-39s %-30s %-30s %s", values[0], values[1], values[2],values[3], values[4], values[5], values[5]);
                System.out.println(formattedLine);
                line = reader.readLine();
                id++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//                System.out.println(values[0] + ",  " + values[1] + ",    " + values[2] + ",     " + values[3] + ",     " + values[4] + ",    " + values[5] + ",     " +  values[5]);
//                line = reader.readLine();
//                id++;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static String convertFileIntoString(String file)throws Exception {
        String result;
        result = new String(Files.readAllBytes(Paths.get(file)));
        return result;
    }

    public static List<Student> convert(String csvFile) throws Exception {
        List<Student> myObjects = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Student myObject = new Student(data[0], data[1], data[2],data[3],data[4],data[5],data[6]);
            myObjects.add(myObject);
        }
        csvReader.close();
        return myObjects;
    }

    public static List<Courses> convertCourses(String csvFile) throws Exception {
        List<Courses> myObjects = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Courses myObject = new Courses(data[0], data[1], data[2],data[3],data[4],data[5]);
            myObjects.add(myObject);
        }
        csvReader.close();
        return myObjects;
    }

    public static void studentEnrolledOnCourses(int studentId) throws Exception {

        file = StudentFunctions.convertFileIntoString(location);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.readValue(file, ObjectNode.class);
        List<Student> myObjects = convert("src/main/resources/students.csv");
        List<Courses> courses = convertCourses("src/main/resources/newCourses.csv");
        Student s;
        s = myObjects.get(studentId);
        node.get("1");
        if (node.has(String.valueOf(studentId))) {

            System.out.println("====================================================================================");
            System.out.println("Student Details page");
            System.out.println("====================================================================================");

            System.out.print("Name:  " + s.getName() + "    " + "Grade: " + s.getGrade() + "    " + " Email: " + s.getEmail());
            System.out.println();

            System.out.println("Enrolled courses.");
            for (JsonNode cou : node.get(String.valueOf(studentId))) {
                Courses c;
                c = courses.get(cou.asInt() - 1);

                System.out.println(c.getId() + "          " + c.getCourseName() + "        " + c.getInstructor() + "    " + c.getCourseDuration() + "        " + c.getCourseTime() + "                           " + c.getLocation());
            }
            System.out.println("====================================================================================");

        } else {

            System.out.println("====================================================================================");
            System.out.println("Student Details page");
            System.out.println("====================================================================================");

            System.out.print("Name:  " + s.getName() + "    " + "Grade: " + s.getGrade() + "    " + " Email: " + s.getEmail());
            System.out.println();
            System.out.println("This student hasn't enrolled in any course");
        }
    }

    public static void addValueToArrayInJSONFile(String filePath, String arrayName, int value) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode array = root.get(arrayName);
            ((ArrayNode) array).add(value);
            objectMapper.writeValue(Paths.get(filePath).toFile(), root);
            System.out.println("COURSE ADDED SUCCESFULLY");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteValueFromArrayInJSONFile(String filePath, String arrayName, int value) {
        try {

            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode array = root.get(arrayName);
            if(array.size()>1) {
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i).asInt() == value ) {
                        ((ArrayNode) array).remove(i);
                        System.out.println("COURSE DELETED SUCCESFULLY");
                        break;
                    }
                }
            }else {
                System.out.println("YOU MUST HAVE ONE COURSE OR MORE");
                printUIOfLMS();
            }

            objectMapper.writeValue(Paths.get(filePath).toFile(), root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void replaceValueInArrayInJSONFile(String filePath, String arrayName, int oldValue, int newValue) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode array = root.get(arrayName);

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).asInt() == oldValue) {
                    {
                        ((ArrayNode) array).set(i, new IntNode(newValue));
                        break;
                    }
                }
            }
            objectMapper.writeValue(Paths.get(filePath).toFile(), root);
            System.out.println("COURSE "+oldValue + " REPLACED WITH " +newValue+" SUCCESSFULLY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printUIOfLMS() throws Exception {
        System.out.println("Please choose from the following: ");
        System.out.println("a - Enroll in a course ");
        System.out.println("d - Unenrollfrom an existing course ");
        System.out.println("r - Replacing an existing course ");
        System.out.println("b - Back to the main page ");

        Scanner action = new Scanner(System.in);
        System.out.print("Please select the required action: ");
        String chosen= action.nextLine();

        String file;
        final  String location = "src/main/resources/studentCourseDetails.json";

        if(chosen.equals("a")){

            Scanner student = new Scanner(System.in);
            System.out.print("Enter id of student ->  ");
            int sId= student.nextInt();
            Scanner course = new Scanner(System.in);
            System.out.print("Enter id of course ->  ");
            int cId= course.nextInt();

            List<Courses> courses = convertCourses("src/main/resources/newCourses.csv");

            file = convertFileIntoString(location);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.readValue(file, ObjectNode.class);
            if (node.has(String.valueOf(sId))) {
                boolean isadded =false ;
                if (node.get(String.valueOf(sId)).size() >= 6) {
                    System.out.println("YOU CAN'T ENROLLE ON MORE THAN 6 COURSES");

                }else {

                    for (JsonNode cou : node.get(String.valueOf(sId))) {
                        Courses c = courses.get(cou.asInt() - 1);

                        if (String.valueOf(cId).equals(c.getId())) {
                            System.out.println("YOU ENROLED ON THIS COURSE BEFORE");
                            isadded=true;
                            break;
                        }
                    }

                    if(!isadded) {
                        String filePath = "src/main/resources/studentCourseDetails.json";
                        String arrayName = String.valueOf(sId);
                        int value = cId;

                        StudentFunctions.addValueToArrayInJSONFile(filePath, arrayName, value);
                    }
                }
            }

        } else if (chosen.equals("d")) {
            String filePath = "src/main/resources/studentCourseDetails.json";
            Scanner student = new Scanner(System.in);
            System.out.print("Enter id of student ->  ");
            int sId= student.nextInt();
            String arrayName = String.valueOf(sId);

            Scanner course = new Scanner(System.in);
            System.out.print("Enter id of course you want to Unenrollfrom ->  ");
            int cId= course.nextInt();

            int value = cId;

            deleteValueFromArrayInJSONFile(filePath, arrayName, value);

        }else if (chosen.equals("r")) {

            String filePath = "src/main/resources/studentCourseDetails.json";
            Scanner student = new Scanner(System.in);
            System.out.print("Enter id of student ->  ");
            int sId= student.nextInt();
            String arrayName = String.valueOf(sId);
            Scanner course = new Scanner(System.in);
            System.out.print("Enter id of course 1->  ");
            int cId= course.nextInt();
            int value = cId;
            Scanner course2 = new Scanner(System.in);
        System.out.print("Enter id of course 2->  ");
        int cId2= course2.nextInt();
        int newValue = cId2;

        replaceValueInArrayInJSONFile(filePath, arrayName, value, newValue);

        }else if (chosen.equals("b")) {
            System.out.print("ok, this is the main list 2->  ");
            printUIOfLMS();
        }
    }
}
