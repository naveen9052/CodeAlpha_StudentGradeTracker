package test.service;

import model.Student;
import service.GradeService;

public class GradeServiceTest {
    public static void main(String[] args) {
        GradeService service = new GradeService();

        Student alice = new Student("Alice");
        alice.addMark(95);
        alice.addMark(85);
        alice.addMark(75);

        assert Math.abs(service.calculateAverage(alice) - 85.0) < 0.0001 : "Average should be 85.0";
        assert service.getHighest(alice) == 95 : "Highest should be 95";
        assert service.getLowest(alice) == 75 : "Lowest should be 75";
        assert "B".equals(service.getLetterGrade(service.calculateAverage(alice))) : "Grade should be B";

        Student empty = new Student("NoMarks");
        assert service.calculateAverage(empty) == 0.0 : "Empty average should be 0.0";
        assert service.getHighest(empty) == 0 : "Empty highest should be 0";
        assert service.getLowest(empty) == 0 : "Empty lowest should be 0";

        assert "A".equals(service.getLetterGrade(90)) : "90 should be A";
        assert "B".equals(service.getLetterGrade(80)) : "80 should be B";
        assert "C".equals(service.getLetterGrade(70)) : "70 should be C";
        assert "D".equals(service.getLetterGrade(60)) : "60 should be D";
        assert "F".equals(service.getLetterGrade(59.99)) : "Below 60 should be F";

        System.out.println("GradeServiceTest passed.");
    }
}
