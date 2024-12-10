package pl.menagment_system.team_menagment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        int rowsAffected = employeeRepository.deleteById(id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Employee with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Employee with ID " + id + " not found.");
        }
    }
}