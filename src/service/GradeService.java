package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    public double calculateAverage(Student student) {
        List<Integer> marks = student.getMarks();
        if (marks.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return sum / (double) marks.size();
    }

    public int getHighest(Student student) {
        List<Integer> marks = student.getMarks();
        if (marks.isEmpty()) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int m : marks) {
            if (m > max) max = m;
        }
        return max;
    }

    public int getLowest(Student student) {
        List<Integer> marks = student.getMarks();
        if (marks.isEmpty()) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int m : marks) {
            if (m < min) min = m;
        }
        return min;
    }

    public void displayReport(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\nNo students found. Add students to view reports.");
            return;
        }

        for (Student s : students) {
            System.out.println("\nStudent Name: " + s.getName());
            System.out.println("Marks: " + s.getMarks());
            if (s.getMarks().isEmpty()) {
                System.out.println("Average: N/A");
                System.out.println("Grade: N/A");
                System.out.println("Highest: N/A");
                System.out.println("Lowest: N/A");
            } else {
                double average = calculateAverage(s);
                System.out.printf("Average: %.2f%n", average);
                System.out.println("Grade: " + getLetterGrade(average));
                System.out.println("Highest: " + getHighest(s));
                System.out.println("Lowest: " + getLowest(s));
            }
        }
    }

    public String getLetterGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }
}
