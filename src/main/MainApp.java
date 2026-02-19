package main;

import model.Student;
import service.GradeService;
import util.FileUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GradeService service = new GradeService();
        ArrayList<Student> students = FileUtil.loadStudents();

        while (true) {
            System.out.println("\n--- Student Grade Tracker ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student Marks");
            System.out.println("3. Delete Student");
            System.out.println("4. View Report");
            System.out.println("5. Save & Exit");
            int choice = readIntInRange(sc, "Choose option: ", 1, 5);

            if (choice == 1) {
                String name = readNonEmptyText(sc, "Enter student name: ");
                Student student;
                try {
                    student = new Student(name);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid student name: " + e.getMessage());
                    continue;
                }

                int n = readIntInRange(sc, "Enter number of subjects: ", 1, 20);
                for (int i = 0; i < n; i++) {
                    int mark = readIntInRange(sc, "Enter mark " + (i + 1) + " (0-100): ", 0, 100);
                    student.addMark(mark);
                }

                students.add(student);
                System.out.println("Student added successfully!");

            } else if (choice == 2) {
                editStudent(sc, students);

            } else if (choice == 3) {
                deleteStudent(sc, students);

            } else if (choice == 4) {
                service.displayReport(students);

            } else if (choice == 5) {
                FileUtil.saveStudents(students);
                System.out.println("Data saved. Exiting...");
                break;

            } else {
                System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }

    private static String readNonEmptyText(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void editStudent(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to edit.");
            return;
        }

        int index = selectStudent(sc, students, "Select student number to edit: ");
        if (index < 0) {
            return;
        }

        Student selected = students.get(index);
        selected.clearMarks();
        int n = readIntInRange(sc, "Enter number of subjects: ", 1, 20);
        for (int i = 0; i < n; i++) {
            int mark = readIntInRange(sc, "Enter mark " + (i + 1) + " (0-100): ", 0, 100);
            selected.addMark(mark);
        }
        System.out.println("Student marks updated.");
    }

    private static void deleteStudent(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to delete.");
            return;
        }

        int index = selectStudent(sc, students, "Select student number to delete: ");
        if (index < 0) {
            return;
        }

        Student removed = students.remove(index);
        System.out.println("Deleted student: " + removed.getName());
    }

    private static int selectStudent(Scanner sc, ArrayList<Student> students, String prompt) {
        System.out.println("\nStudents:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }

        return readIntInRange(sc, prompt, 1, students.size()) - 1;
    }
}
