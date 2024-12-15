package pl.menagment_system.team_menagment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.dto.EmployeeRequestDTO;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;
import pl.menagment_system.team_menagment_system.services.EmployeeService;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing employee-related operations.
 * This class handles HTTP requests for operations such as retrieving all employees,
 * retrieving employees by team, adding new employees, deleting employees, and updating employees.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
        this.employeeService = new EmployeeService(employeeRepository);
    }

    /**
     * Retrieves all employees from the repository.
     *
     * @return a list of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
    /**
     * Retrieves all employees associated with a specified team ID.
     *
     * @param teamId the ID of the team for which to retrieve employees
     * @return a ResponseEntity containing the list of employees if found, or an error message if no employees are found for the given team ID
     */
    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getAllEmployees(@PathVariable int teamId) {
        List<Employee> employees = employeeRepository.findByTeamId(teamId);
        if (employees == null || employees.isEmpty()) {
            return ResponseEntity.status(404).body("No employees found for team ID " + teamId + ".");
        }
        return ResponseEntity.ok(employees);
    }

    /**
     * Deletes an employee based on the provided ID.
     *
     * @param id the ID of the employee to be deleted
     * @return a ResponseEntity containing a success message if the employee was deleted,
     *         or an error message if the employee with the given ID was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        int rowsAffected = employeeRepository.deleteById(id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Employee with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Employee with ID " + id + " not found.");
        }
    }

    /**
     * Adds a new employee to the system based on the provided EmployeeRequestDTO.
     * Handles validation and ensures email uniqueness before saving the employee.
     *
     * @param dto the EmployeeRequestDTO object containing details of the employee to be added
     * @return ResponseEntity containing a success message if the operation is successful,
     *         or an error message with an appropriate HTTP status code in case of failure
     */
    @PostMapping
    public ResponseEntity<String> addEmployee(@Validated(EmployeeRequestDTO.Create.class) @RequestBody EmployeeRequestDTO dto) {
        try {
            employeeService.validateEmailUniqueness(dto.getEmail());

            Employee employee = new Employee(
                    0, // ID will be auto-generated
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getPhone(),
                    dto.getHireDate(),
                    dto.getRole(),
                    dto.getTeamId()
            );

            // Save Employee
            employeeRepository.save(employee);

            return ResponseEntity.ok("New employee has been added successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Unexpected error occurred.");
        }
    }


    /**
     * Updates an existing employee's details based on the provided ID and the updated fields in the request body.
     * Only fields that are provided in the input will be updated.
     *
     * @param id The ID of the employee to be updated.
     * @param employeeRequestDTO The data transfer object containing the employee's updated details.
     * @return A ResponseEntity containing a message and an appropriate HTTP status code:
     *         - 200 OK if the update is successful.
     *         - 404 Not Found if the employee with the given ID does not exist.
     *         - 400 Bad Request if there is an issue with the input data.
     *         - 500 Internal Server Error if an unexpected error occurs.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable("id") int id,
            @RequestBody @Validated(EmployeeRequestDTO.Update.class) EmployeeRequestDTO employeeRequestDTO) {
        try {
            Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);

            if (existingEmployeeOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Employee not found");
            }
            Employee existingEmployee = existingEmployeeOptional.get();

            // Check each field and update only if provided
            if (employeeRequestDTO.getFirstName() != null) {
                existingEmployee.setFirstName(employeeRequestDTO.getFirstName());
            }
            if (employeeRequestDTO.getLastName() != null) {
                existingEmployee.setLastName(employeeRequestDTO.getLastName());
            }
            if (employeeRequestDTO.getEmail() != null) {
                if(!employeeRequestDTO.getEmail().equalsIgnoreCase(existingEmployee.getEmail())) {
                    employeeService.validateEmailUniqueness(employeeRequestDTO.getEmail()); // Validation
                }
                existingEmployee.setEmail(employeeRequestDTO.getEmail());
            }
            if (employeeRequestDTO.getPhone() != null) {
                existingEmployee.setPhone(employeeRequestDTO.getPhone());
            }
            if (employeeRequestDTO.getHireDate() != null) {
                existingEmployee.setHireDate(employeeRequestDTO.getHireDate());
            }
            if (employeeRequestDTO.getRole() != null) {
                existingEmployee.setRole(employeeRequestDTO.getRole());
            }
            if (employeeRequestDTO.getTeamId() != null) {
                existingEmployee.setTeamId(employeeRequestDTO.getTeamId());
            }

            // Save updated employee object
            employeeRepository.update(existingEmployee);

            return ResponseEntity.ok("Employee updated successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}