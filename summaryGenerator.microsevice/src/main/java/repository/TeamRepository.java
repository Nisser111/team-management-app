package repository;

import model.Team;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


/**
 * Repository class that provides CRUD operations for the Team entity using JdbcTemplate.
 */
@Repository
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Retrieves all teams from the database.
     *
     * @return a list of Team objects
     */
    public List<Team> findAll() {
        String sql = "SELECT * FROM teams";
        return jdbcTemplate.query(sql, new TeamRowMapper());
    }
}

/**
 * A RowMapper implementation for mapping rows of a ResultSet to Team objects.
 * This class is used to map each row of the result set obtained from a query
 * into a Team object. The mapping is performed by extracting the values of the
 * columns "ID" and "name" from the result set and passing them to the Team constructor.
 */
class TeamRowMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Team(
                rs.getInt("ID"),
                rs.getString("name")
        );
    }
}
