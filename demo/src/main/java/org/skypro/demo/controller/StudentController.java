package org.skypro.demo.controller;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id,
                          @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping("/age")
    public List<Student> getStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max
    ) {
        return studentService.getStudentsByAgeBetween(min, max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/count")
    public long getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/avg-age")
    public Double getStudentsAverageAge() {
        return studentService.getStudentsAverageAge();
    }

    @GetMapping("/last-5")
    public List<Student> getLast5Students() {
        return studentService.getLast5Students();
    }

    @GetMapping("/names/starting-with-a")
    public List<String> getStudentsNamesStartingWithA() {
        return studentService.getStudentsNamesStartingWithA();
    }

    @GetMapping("/average-age/find-all")
    public Double getAverageAgeUsingFindAll() {
        return studentService.calculateAverageAgeUsingFindAll();
    }

    @GetMapping("/sum")
    public long getFastSum() {
        return java.util.stream.LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    @GetMapping("/print-parallel")
    public String printStudentsParallel() throws InterruptedException {

        List<Student> students = studentService.getAll(); // берём всех

        if (students.size() < 6) {
            return "Need at least 6 students in DB. Now: " + students.size();
        }

        System.out.println(Thread.currentThread().getName() + " -> " + students.get(0).getName());
        System.out.println(Thread.currentThread().getName() + " -> " + students.get(1).getName());

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " -> " + students.get(2).getName());
            System.out.println(Thread.currentThread().getName() + " -> " + students.get(3).getName());
        }, "parallel-thread-1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " -> " + students.get(4).getName());
            System.out.println(Thread.currentThread().getName() + " -> " + students.get(5).getName());
        }, "parallel-thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        return "Printed 6 students in console (main + 2 parallel threads)";
    }

    private synchronized void printSync(String name) {
        System.out.println(Thread.currentThread().getName() + " -> " + name);
    }

    @GetMapping("/print-synchronized")
    public String printStudentsSynchronized() throws InterruptedException {

        List<Student> students = studentService.getAll();

        if (students.size() < 6) {
            return "Need at least 6 students in DB. Now: " + students.size();
        }

        printSync(students.get(0).getName());
        printSync(students.get(1).getName());

        Thread t1 = new Thread(() -> {
            printSync(students.get(2).getName());
            printSync(students.get(3).getName());
        }, "parallel-thread-1");

        Thread t2 = new Thread(() -> {
            printSync(students.get(4).getName());
            printSync(students.get(5).getName());
        }, "parallel-thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        return "Printed 6 students in console (synchronized print)";
    }



}
