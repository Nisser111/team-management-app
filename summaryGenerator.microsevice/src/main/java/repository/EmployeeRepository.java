package repository;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
