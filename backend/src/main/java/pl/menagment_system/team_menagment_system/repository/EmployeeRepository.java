package pl.menagment_system.team_menagment_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.menagment_system.team_menagment_system.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository class that provides CRUD operations for the Employee entity using JdbcTemplate.
 */
@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new EmployeeRepository with the specified JdbcTemplate.
     *
     * @param jdbcTemplate the JdbcTemplate for interacting with the database
     */
    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of Employee objects
     */
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    /**
     * Retrieves an employee from the database by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the Employee object corresponding to the specified ID
     */
    public Optional<Employee> findById(int id) {
        String sql = "SELECT * FROM employees WHERE ID = ?";
        List<Employee> results = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);
        return results.stream().findFirst();
    }

    /**
     * Retrieves a list of employees belonging to a specific team from the database.
     *
     * @param teamId the ID of the team to filter employees by
     * @return a list of Employee objects associated with the specified team ID
     */
    public List<Employee> findByTeamId(int teamId) {
        String sql = "SELECT * FROM employees WHERE team_id = ?";
        return jdbcTemplate.query(sql, new EmployeeRowMapper(), teamId);
    }


    /**
     * Inserts a new Employee into the database.
     *
     * @param employee the Employee to save
     * @return the number of rows affected
     */
    public int save(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, phone, hire_date, role, team_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhone(), employee.getHireDate(), employee.getRole(), employee.getTeamId());
    }

    /**
     * Updates an existing Employee in the database.
     *
     * @param employee the Employee with updated information
     * @return the number of rows affected
     */
    public int update(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone = ?, hire_date = ?, role = ?, team_id = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhone(), employee.getHireDate(), employee.getRole(), employee.getTeamId(), employee.getId());
    }

    /**
     * Deletes an Employee from the database by ID.
     *
     * @param id the ID of the Employee to delete
     * @return the number of rows affected
     */
    public int deleteById(int id) {
        String sql = "DELETE FROM employees WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }
}

/**
 * RowMapper implementation for mapping rows of a ResultSet to Employee objects.
 */
class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(
                rs.getInt("ID"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getDate("hire_date"),
                rs.getString("role"),
                rs.getInt("team_id")
        );
    }
}
