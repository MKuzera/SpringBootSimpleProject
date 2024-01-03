package com.course.test;

import com.course.test.logic.ClassTeacher;
import com.course.test.logic.Teacher;
import com.course.test.logic.TeacherCondition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

@RestController
public class TeacherController {

    @PostMapping("api/teacher")
    public ResponseEntity<String> postRating(@RequestParam String imie,@RequestParam String nazwisko, @RequestParam String salary,@RequestParam String condition, @RequestParam String nazwaKlasy){
        ClassTeacher classTeacher = TestApplication.classContainer.getContainer().get(nazwaKlasy);
        if(classTeacher == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            Teacher teacher1 = new Teacher(imie, nazwisko , new Date(2024, 1, 1), TeacherCondition.valueOf(condition), Double.parseDouble( salary));
            classTeacher.addTeacher(teacher1);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }


        return new ResponseEntity<>("Dodano nauczyciela", HttpStatus.CREATED);

    }

    @GetMapping("api/teacher/{id}")
    public ResponseEntity<Teacher> postRating(@PathVariable String id){

        Teacher teacher = TestApplication.classContainer.findTeacher(Integer.parseInt(id));
        if(teacher == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

            return new ResponseEntity<>(teacher, HttpStatus.OK);

    }
// (async function deleteTecher(id) { await fetch('http://localhost:8080/api/teacher/' +id , {method: "DELETE"})})("5");
    @DeleteMapping("api/teacher/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        try {
            Teacher teacher = TestApplication.classContainer.removeTeacher(Integer.parseInt(id));
            if(teacher == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>("usunieto nauczyciela", HttpStatus.OK);
            }
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping(value = "/api/teacher/csv", produces = "text/csv")
    public ResponseEntity<String> exportTeachersToCsv() {
        List<Teacher> teachers = TestApplication.getAllTeachers();
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("Imie,Nazwisko,Rok urodzenia,Condtion,Wynagrodzenie\n");

        for (Teacher teacher : teachers) {
            stringWriter.append(escapeCsvField(teacher.getFirstName())).append(",");
            stringWriter.append(escapeCsvField(teacher.getLastName())).append(",");
            stringWriter.append(String.valueOf(teacher.getYearOfBirth())).append(",");
            stringWriter.append(escapeCsvField(teacher.getTeacherCondition().toString())).append(",");
            stringWriter.append(String.valueOf(teacher.getSalary())).append("\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teachers.csv");

        return new ResponseEntity<>(stringWriter.toString(), headers, HttpStatus.OK);
    }

    private String escapeCsvField(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        } else {
            return field;
        }
    }


}
