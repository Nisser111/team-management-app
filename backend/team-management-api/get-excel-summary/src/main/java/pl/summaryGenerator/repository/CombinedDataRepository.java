package pl.summaryGenerator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.summaryGenerator.model.CombinedData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository for accessing combined data of employees from the database.
 * This class uses JdbcTemplate to execute SQL queries and map the results
 * to CombinedData objects.
 */
@Repository
public class CombinedDataRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a CombinedDataRepository with the specified JdbcTemplate.
     *
     * @param jdbcTemplate the JdbcTemplate used for database operations
     */
    @Autowired
    public CombinedDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of CombinedData objects representing employees.
     *
     * @return a List of CombinedData containing employee details
     */
    public List<CombinedData> get() {
        String sql = "SELECT first_name, last_name, email, phone, hire_date, role, team_id, name as team FROM employees JOIN menagement_system.teams t on t.ID = employees.team_id";
        return jdbcTemplate.query(sql, new CombinedDataRepositoryRowMapper());
    }
}

/**
 * RowMapper implementation for mapping rows of the ResultSet to CombinedData
 * objects.
 */
class CombinedDataRepositoryRowMapper implements RowMapper<CombinedData> {
    @Override
    public CombinedData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CombinedData(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getDate("hire_date"),
                rs.getString("role"),
                rs.getString("team"));
    }
}
