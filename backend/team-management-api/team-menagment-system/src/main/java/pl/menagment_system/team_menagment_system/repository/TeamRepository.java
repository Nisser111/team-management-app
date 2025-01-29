package pl.menagment_system.team_menagment_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.menagment_system.team_menagment_system.model.Team;

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


    /**
     * Retrieves a Team entity from the database by its ID.
     *
     * @param id the ID of the team to retrieve
     * @return an Optional containing the Team object if found, or an empty Optional if not
     */
    public Optional<Team> findById(int id) {
        String sql = "SELECT * FROM teams WHERE ID = ?";
        List<Team> results = jdbcTemplate.query(sql, new TeamRowMapper(), id);
        return results.stream().findFirst();
    }

    /**
     * Saves a new Team entity in the database.
     *
     * @param team the Team object to be saved
     * @return the number of rows affected
     */
    public int save(Team team) {
        String sql = "INSERT INTO teams (name) VALUES (?)";
        return jdbcTemplate.update(sql, team.getName());
    }

    /**
     * Updates an existing Team in the database.
     *
     * @param team the Team object containing updated information
     * @return the number of rows affected
     */
    public int update(Team team) {
        String sql = "UPDATE teams SET name = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, team.getName(), team.getId());
    }

    /**
     * Deletes a Team record from the database by its ID.
     *
     * @param id the ID of the Team to be deleted
     * @return the number of rows affected
     */
    public int deleteById(int id) {
        String sql = "DELETE FROM teams WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
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
