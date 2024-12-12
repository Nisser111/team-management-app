package pl.menagment_system.team_menagment_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.menagment_system.team_menagment_system.model.Employee;
import pl.menagment_system.team_menagment_system.repository.EmployeeRepository;

import java.util.List;

/**
 * Service class responsible for managing Employee-related operations.
 * This class provides functionality for validating employee data
 * and interacting with the EmployeeRepository for persistence operations.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validateEmailUniqueness(String email) {
        List<Employee> existingEmployees = employeeRepository.findAll();
        if (existingEmployees.stream().anyMatch(emp -> emp.getEmail().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException("Email is already in use.");
        }
    }
}