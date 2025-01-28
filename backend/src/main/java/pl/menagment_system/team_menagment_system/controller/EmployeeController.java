package pl.menagment_system.team_menagment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.dto.EmployeeRequestDTO;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;
import pl.menagment_system.team_menagment_system.services.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller class for managing employee-related operations.
 * This class handles HTTP requests for operations such as retrieving all
 * employees,
 * retrieving employees by team, adding new employees, deleting employees, and
 * updating employees.
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
     * Retrieves all employees from the repository and returns a response containing
     * the result.
     * The response includes employees if found, or appropriate error messages in
     * case of failure.
     *
     * @return ResponseEntity containing a Map with the following keys:
     *         - "success": a boolean indicating whether the operation was
     *         successful
     *         - "message": a string describing the operation status
     *         - "data": a list of Employee objects if available, or an empty list
     *         otherwise.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Employee> employees = employeeRepository.findAll();

            if (employees.isEmpty()) {
                // Build not found error response
                response.put("success", true);
                response.put("message", "Nie znaleziono pracowników.");
                response.put("data", employees);
                return ResponseEntity.ok(response);
            }

            // Build success response
            response.put("success", true);
            response.put("message", "Pracownicy zostali pomyślnie pobrani.");
            response.put("data", employees);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            // Build unexpected error response
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Deletes an employee identified by the given ID from the repository.
     * If the employee is successfully deleted, the response will contain
     * the employee details and a success message. If not found, a 404 error
     * message is returned. In case of any unexected error, a 500 error response is
     * returned.
     *
     * @param id the unique identifier of the employee to be deleted
     * @return a ResponseEntity containing success information,
     *         the employee data if deletion is successful, or an error message
     *         otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Employee> deletedEmployee = employeeRepository.findById(id);
            String fullName = deletedEmployee.get().getFirstName() + " " + deletedEmployee.get().getLastName();
            int rowsAffected = employeeRepository.deleteById(id);

            if (rowsAffected > 0) {
                // Build success response
                response.put("message", "Pracownik " + fullName + " został usunięty.");
                response.put("success", true);
                response.put("data", deletedEmployee.get());
                return ResponseEntity.ok(response);
            } else {
                // Build not found error response
                response.put("message", "Nie znaleziono pracownika o identyfikatorze " + id + ".");
                response.put("success", false);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception ex) {
            // Build unexpected error response
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Adds a new employee to the system after validating the provided data.
     * The email of the employee must be unique, and the request data must meet the
     * validation constraints.
     *
     * @param dto The EmployeeRequestDTO containing the necessary details of the
     *            employee to be added.
     *            This includes first name, last name, email, phone, hire date,
     *            role, and team ID.
     * @return A ResponseEntity containing a Map with the operation's status, a
     *         message, and the added employee object if successful.
     *         If there's a validation error, it returns a 400 status with an error
     *         message.
     *         If there's an unexpected error, it returns a 500 status with an error
     *         message.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addEmployee(
            @Validated(EmployeeRequestDTO.Create.class) @RequestBody EmployeeRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validate uniqueness of email
            employeeService.validateEmailUniqueness(dto.getEmail());

            Employee employee = new Employee(
                    0, // ID will be auto-generated
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getPhone(),
                    dto.getHireDate(),
                    dto.getRole(),
                    dto.getTeamId());

            String fullName = employee.getFirstName() + " " + employee.getLastName();
            employeeRepository.save(employee);

            // Build success response
            response.put("success", true);
            response.put("message", "Pracownik " + fullName + " został dodany.");
            response.put("data", employee);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException ex) {
            // Build validation error response
            response.put("success", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception ex) {
            // Build unexpected error response
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Updates an existing employee's details based on the given information.
     * Only the provided fields in the request will be updated.
     *
     * @param id                 The ID of the employee to be updated.
     * @param employeeRequestDTO The data transfer object containing the employee
     *                           details to be updated.
     * @return A ResponseEntity containing a map with the operation result,
     *         including success status, message,
     *         and updated employee data in case of success.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(
            @PathVariable("id") int id,
            @RequestBody @Validated(EmployeeRequestDTO.Update.class) EmployeeRequestDTO employeeRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);

            if (existingEmployeeOptional.isEmpty()) {
                // Build a response for not found
                response.put("success", false);
                response.put("message", "Nie znaleziono pracownika o identyfikatorze " + id + ".");
                return ResponseEntity.status(404).body(response);
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
                if (!employeeRequestDTO.getEmail().equalsIgnoreCase(existingEmployee.getEmail())) {
                    employeeService.validateEmailUniqueness(employeeRequestDTO.getEmail()); // Validate unique email
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

            String fullName = existingEmployee.getFirstName() + " " + existingEmployee.getLastName();
            employeeRepository.update(existingEmployee);

            response.put("success", true);
            response.put("message", "Pracownik " + fullName + " został pomyślnie zaktualizowany.");
            response.put("data", existingEmployee);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException ex) {
            // Handle validation error
            response.put("success", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception ex) {
            // Handle unexpected errors
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd.");
            return ResponseEntity.status(500).body(response);
        }
    }
}