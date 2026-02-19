package util;

import model.Student;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringJoiner;

public class FileUtil {

    private static Path findDataPath() {
        Path p = Paths.get(System.getProperty("user.dir"));
        while (p != null) {
            Path candidate = p.resolve("data").resolve("students.txt");
            if (Files.exists(candidate))
                return candidate;
            p = p.getParent();
        }
        return Paths.get(System.getProperty("user.dir")).resolve("data").resolve("students.txt");
    }

    public static void saveStudents(ArrayList<Student> students) {
        Path dataPath = findDataPath();
        try {
            if (dataPath.getParent() != null)
                Files.createDirectories(dataPath.getParent());
        } catch (IOException ignored) {
        }

        try (BufferedWriter bw = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8)) {
            for (Student s : students) {
                bw.write(toStorageLine(s));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data to " + dataPath + ": " + e.getMessage());
        }
    }

    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        Path dataPath = findDataPath();

        try (BufferedReader br = Files.newBufferedReader(dataPath, StandardCharsets.UTF_8)) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                try {
                    Student parsed = parseStorageLine(line);
                    if (parsed != null) {
                        students.add(parsed);
                    }
                } catch (RuntimeException e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data found at " + dataPath + ".");
        }

        return students;
    }

    public static String toStorageLine(Student student) {
        StringJoiner joiner = new StringJoiner(",");
        for (int mark : student.getMarks()) {
            joiner.add(String.valueOf(mark));
        }

        String safeName = student.getName().replace("|", "/");
        return "S|" + safeName + "|" + joiner;
    }

    public static Student parseStorageLine(String line) {
        if (line == null) {
            return null;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        if (trimmed.contains(":")) {
            String[] parts = trimmed.split(":", 2);
            String name = parts[0].trim();
            if (name.isEmpty()) {
                return null;
            }
            Student student = new Student(name);
            String marksPart = parts.length > 1 ? parts[1].replace("[", "").replace("]", "") : "";
            addMarksFromCsv(student, marksPart);
            return student;
        }

        if (trimmed.contains("|")) {
            String[] parts = trimmed.split("\\|");
            if (parts.length < 2) {
                return null;
            }
            String name = parts[1].trim();
            if (name.isEmpty()) {
                return null;
            }

            Student student = new Student(name);
            if (parts.length >= 3) {
                addMarksFromCsv(student, parts[2]);
            }
            return student;
        }

        return new Student(trimmed);
    }

    private static void addMarksFromCsv(Student student, String marksPart) {
        if (marksPart == null || marksPart.trim().isEmpty()) {
            return;
        }

        String[] marks = marksPart.split(",");
        for (String mark : marks) {
            if (mark.trim().isEmpty()) {
                continue;
            }
            student.addMark(Integer.parseInt(mark.trim()));
        }
    }
}
