package pl.menagment_system.team_menagment_system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.dto.EmployeeRequestDTO;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;
import pl.menagment_system.team_menagment_system.services.EmployeeService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller class for managing employee-related operations.
 * This class handles HTTP requests for operations such as retrieving all employees,
 * retrieving employees by team, adding new employees, deleting employees, and updating employees.
 */
@RestController
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
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
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
     * Updates an existing employee's details based on the provided updates.
     * The method permits partial updates for specific employee fields
     * and validates the provided fields against a predefined set of allowed keys.
     *
     * @param id the unique identifier of the employee to be updated
     * @param updates a map containing key-value pairs where keys match employee field names
     *                (e.g., "firstName", "lastName") and values are the updated field values
     * @return a ResponseEntity containing a status code and a message.
     *         Possible status codes include:
     *         - 200: Successful update.
     *         - 400: Invalid update request or unrecognized field.
     *         - 404: Employee not found for the provided ID.
     */
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