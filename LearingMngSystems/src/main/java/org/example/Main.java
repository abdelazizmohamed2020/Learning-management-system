package org.example;
import org.example.service.StudentFunctions;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        File textFile = new File("src/main/resources/students.txt");
        String text = "src/main/resources/students.csv";

        System.out.println("Welcome to LMS");
        System.out.println("created by {Abdelaziz mohamed Abdelhafez _23/1/2023}");
        System.out.println("====================================================================================");
        System.out.println("Home page");
        System.out.println("====================================================================================");
        System.out.println("Student list:");

        File f = new File("src/main/resources/students.csv");
        if (!(f.exists() && !f.isDirectory())) {
            StudentFunctions.convertTxtToCsv(textFile);
        }

        StudentFunctions.printStudentsData(text);
        System.out.println("------------------------------------------------------------------------------------");

        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Please select the required student:  ");

        int studentId = sc.nextInt();
        if (studentId > 100 || studentId <= 0) {
            System.out.println("Invalid Student ID");
        } else {
            StudentFunctions.studentEnrolledOnCourses(studentId);
        }

        StudentFunctions.printUIOfLMS();

    }


}