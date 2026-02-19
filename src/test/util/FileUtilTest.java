package test.util;

import model.Student;
import util.FileUtil;

public class FileUtilTest {
    public static void main(String[] args) {
        Student student = new Student("Alice");
        student.addMark(88);
        student.addMark(92);

        String line = FileUtil.toStorageLine(student);
        assert "S|Alice|88,92".equals(line) : "Storage line should match new format";

        Student parsedNew = FileUtil.parseStorageLine("S|Bob|70,80,90");
        assert parsedNew != null : "Parsed student should not be null";
        assert "Bob".equals(parsedNew.getName()) : "Name should be Bob";
        assert parsedNew.getMarks().size() == 3 : "Bob should have 3 marks";
        assert parsedNew.getMarks().get(0) == 70 : "First mark should be 70";

        Student parsedOld = FileUtil.parseStorageLine("Charlie:[65, 75, 85]");
        assert parsedOld != null : "Parsed old format should not be null";
        assert "Charlie".equals(parsedOld.getName()) : "Name should be Charlie";
        assert parsedOld.getMarks().size() == 3 : "Charlie should have 3 marks";

        Student parsedNameOnly = FileUtil.parseStorageLine("Diana");
        assert parsedNameOnly != null : "Name-only line should parse";
        assert "Diana".equals(parsedNameOnly.getName()) : "Name should be Diana";
        assert parsedNameOnly.getMarks().isEmpty() : "Name-only student should have no marks";

        assert FileUtil.parseStorageLine("   ") == null : "Blank line should return null";

        System.out.println("FileUtilTest passed.");
    }
}
