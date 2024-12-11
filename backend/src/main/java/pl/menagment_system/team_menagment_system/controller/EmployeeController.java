package pl.menagment_system.team_menagment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getAllEmployees(@PathVariable int teamId) {
        List<Employee> employees = employeeRepository.findByTeamId(teamId);
        if (employees == null || employees.isEmpty()) {
            return ResponseEntity.status(404).body("No employees found for team ID " + teamId + ".");
        }
        return ResponseEntity.ok(employees);
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

    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        System.out.println(employee);
        int rowsAffected = employeeRepository.save(employee);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("New employee has been added successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to add the new employee.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(404).body("Employee with ID " + id + " not found.");
        }

        Employee existingEmployee = optionalEmployee.get();

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "firstName":
                    existingEmployee.setFirstName((String) value);
                    break;
                case "lastName":
                    existingEmployee.setLastName((String) value);
                    break;
                case "email":
                    existingEmployee.setEmail((String) value);
                    break;
                case "phone":
                    existingEmployee.setPhone((String) value);
                    break;
                case "hireDate":
                    existingEmployee.setHireDate((Date) value);
                    break;
                case "role":
                    existingEmployee.setRole((String) value);
                    break;
                case "teamId":
                    existingEmployee.setTeamId((Integer) value);
                    break;
                default:
                    return ResponseEntity.status(400).body("Failed to update the employee with ID" + id + ". Body element not recognized. ");
            }
        }

        int rowsAffected = employeeRepository.update(existingEmployee);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Employee with ID " + id + " has been updated.");
        } else {
            return ResponseEntity.status(400).body("Failed to update the employee with ID" + id + ".");
        }
    }
}