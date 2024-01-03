package com.course.test.logic;
import java.util.*;

public class ClassContainer {
    Map<String, ClassTeacher> container = new HashMap<>();
    public  Map<String, ClassTeacher> getContainer(){
        return container;
    }
    public void addClass(String groupName,Integer capacity){
        ClassTeacher classTeacher = new ClassTeacher(groupName, capacity);
        container.put(groupName,classTeacher);
    }
    public void addClass(ClassTeacher classTeacher){
        container.put(classTeacher.getGroupName(), classTeacher);
    }
    public Teacher removeTeacher(int id){
        Collection<ClassTeacher> values = container.values();
        Teacher teacher;
        for (ClassTeacher cl:values) {
            try {
                teacher = cl.searchById(id);
                cl.removeTeacher(teacher);
                return teacher;

            } catch (Exception e) {

            }

        }
        return null;

    }
    public Teacher findTeacher(int id){
        Collection<ClassTeacher> values = container.values();
        Teacher teacher;
        for (ClassTeacher cl:values) {
            try {
                teacher = cl.searchById(id);
                return teacher;

            } catch (Exception e) {

            }

        }
        return null;

    }

    public List<Teacher> getAllTeachers(){
        Collection<ClassTeacher> values = container.values();
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher;
        for (ClassTeacher cl:values) {
            for (Teacher t: cl.getTeacherList() ) {
                teachers.add(t);
            }

        }
        return teachers;
    }
    public void addStartData(){

        addClass("Pierwsza klasa",2);
        addClass("Druga klasa",5);
        addClass("Trzecia klasa",5);


        Teacher teacher1 = new Teacher("Mateusz","Kuzera",new Date(2003,4,9),TeacherCondition.OBECNY,5000.0);
        Teacher teacher2 = new Teacher("Kacper","Kowalczyk",new Date(2002,2,4),TeacherCondition.CHORY,6000.0);
        Teacher teacher3 = new Teacher("Wiktor","Nowak",new Date(2001,3,19),TeacherCondition.OBECNY,8000.0);
        Teacher teacher4 = new Teacher("Ala","Osysko",new Date(2000,10,29),TeacherCondition.DELEGACJA,2000.0);
        Teacher teacher5 = new Teacher("Piotr","Ostry",new Date(2003,2,5),TeacherCondition.OBECNY,3000.0);

        try {

            container.get("Pierwsza klasa").addTeacher(teacher1);
            container.get("Pierwsza klasa").addTeacher(teacher2);
            container.get("Druga klasa").addTeacher(teacher3);
            container.get("Druga klasa").addTeacher(teacher4);
            container.get("Trzecia klasa").addTeacher(teacher5);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public ClassTeacher removeClass(String groupName){
        ClassTeacher classTeacher = container.get(groupName);
        container.remove(groupName);
        return classTeacher;
    }
    public ArrayList<String> findEmpty(){
        Iterator<Map.Entry<String, ClassTeacher>> iterator = container.entrySet().iterator();
        ArrayList<String> listOfEmptyClasses = new ArrayList<>();
        while(iterator.hasNext()){

            Map.Entry<String, ClassTeacher> entry = iterator.next();
            ClassTeacher value = entry.getValue();
            if(value.getTeacherList().isEmpty()){

                listOfEmptyClasses.add(entry.getKey());
            }
        }
        return listOfEmptyClasses;
    }
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        Iterator<Map.Entry<String, ClassTeacher>> iterator = container.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, ClassTeacher> entry = iterator.next();
            Double percentFull = (double) entry.getValue().getTeacherList().size() / entry.getValue().getMaxTeachers().doubleValue() * 100.0;
            s.append(entry.getKey() + " ");
            s.append(percentFull.toString() + "% Full\n");
        }
        return s.toString();
    }
}
