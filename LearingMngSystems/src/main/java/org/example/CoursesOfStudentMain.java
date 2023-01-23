package org.example;

import org.example.service.StudentFunctions;
import java.io.File;
import java.util.Scanner;


public class CoursesOfStudentMain {

    public static void main(String[] args) throws Exception {

        File xmlFile = new File("src/main/resources/courses.xml");
        File csvFile = new File("src/main/resources/newCourses.csv");
        File textFile = new File("src/main/resources/students.txt");

        StudentFunctions.convertTxtToCsv(textFile);
        StudentFunctions.convertXmlToCsv(xmlFile, csvFile);
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Enter id of student ->  ");

        int studentId = sc.nextInt();
        if (studentId > 100 || studentId <= 0) {
            System.out.println("Invalid Student ID");
        } else {
            StudentFunctions.studentEnrolledOnCourses(studentId);
        }
    }
}