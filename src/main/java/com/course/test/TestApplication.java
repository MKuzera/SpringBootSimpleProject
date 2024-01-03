package com.course.test;

import com.course.test.logic.ClassContainer;
import com.course.test.logic.Teacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestApplication {
	// "Baza danych"
	public static ClassContainer classContainer;


    public static void main(String[] args) {
		classContainer = new ClassContainer();
		classContainer.addStartData();
		SpringApplication.run(TestApplication.class, args);
	}

	public static List<Teacher> getAllTeachers() {
		return classContainer.getAllTeachers();
	}
}
