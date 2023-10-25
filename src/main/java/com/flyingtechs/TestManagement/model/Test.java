package com.flyingtechs.TestManagement.model;

import com.flyingtechs.studentManagement.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Student_Test")
@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    private String testDescription;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "test_type_id")
    private TestType testType;

    private int totalMarks;

    private Date testDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}