package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private final String name;
    private final ArrayList<Integer> marks;

    public Student(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.name = name.trim();
        this.marks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMarks() {
        return Collections.unmodifiableList(marks);
    }

    public void addMark(int mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100.");
        }
        marks.add(mark);
    }

    public void clearMarks() {
        marks.clear();
    }
}
