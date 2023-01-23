package org.example;

import org.example.service.StudentFunctions;
import java.io.File;


public class ListStudentMain {

    public static void main(String[] args) throws Exception {


        File textFile = new File("src/main/resources/students.txt");

        String text = "src/main/resources/students.csv";

        StudentFunctions.convertTxtToCsv(textFile);

        StudentFunctions.printStudentsData(text);

    }
}