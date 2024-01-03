package com.course.test;

import com.course.test.logic.ClassTeacher;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class ClassContainerController {
    private final ResourceLoader resourceLoader;
    public ClassContainerController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RestController
    public class YourController {

        @GetMapping("api/group")
        public ResponseEntity<Map<String, ClassTeacher>> retrieveAllClasses() {

            return new ResponseEntity<>(TestApplication.classContainer.getContainer(), HttpStatus.OK);
        }

        @PostMapping("api/group")
        public ResponseEntity<String> addNewClass(@RequestParam String groupName, @RequestParam int maxTeachers) {

                TestApplication.classContainer.addClass(groupName,maxTeachers);
            return new ResponseEntity<>("Nowa klasa została dodana", HttpStatus.CREATED);
        }
        // usuwa klase
        // (async function deletePhoto(id) { await fetch('http://localhost:8080/api/group/' +id , {method: "DELETE"})})("Trzecia klasa");
        @DeleteMapping("api/group/{id}")
        public void delete(@PathVariable String id){
            ClassTeacher classTeacher = TestApplication.classContainer.removeClass(id);
            if(classTeacher == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }
        @GetMapping("api/group/{id}")
        public ResponseEntity<ClassTeacher> get(@PathVariable String id){
            ClassTeacher classTeacher = TestApplication.classContainer.getContainer().get(id);
            if(classTeacher == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(classTeacher, HttpStatus.OK);
        }

        @GetMapping("api/group/{id}/fill")
        public ResponseEntity<Double> getfill(@PathVariable String id){
            ClassTeacher classTeacher = TestApplication.classContainer.getContainer().get(id);
            if(classTeacher == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(classTeacher.getFill(), HttpStatus.OK);
        }
        @PostMapping("api/rating")
        public ResponseEntity<String> postRating(@RequestParam String ocena, @RequestParam String nazwaKlasy){
            ClassTeacher classTeacher = TestApplication.classContainer.getContainer().get(nazwaKlasy);
            if(classTeacher == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            classTeacher.addOcena(Integer.parseInt(ocena));
            return new ResponseEntity<>("Dodano ocene", HttpStatus.CREATED);

        }




    }




}
