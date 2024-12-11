package pl.menagment_system.team_menagment_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.model.Team;
import pl.menagment_system.team_menagment_system.repository.TeamRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This controller provides RESTful endpoints for managing teams in the system.
 * It allows the retrieval, addition, deletion, and partial updating of team entities.
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Retrieves all teams from the database.
     *
     * @return a list of Team objects representing all teams in the system
     */
    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Deletes a team by its ID.
     *
     * @param id the unique identifier of the team to be deleted
     * @return a ResponseEntity containing a success message if the team is deleted,
     *         or an error message if the team is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable int id) {
        int rowsAffected = teamRepository.deleteById(id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Team with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Team with ID " + id + " not found.");
        }
    }

    /**
     * Adds a new team to the system.
     *
     * @param team the Team object containing the team details to be added;
     *             must not be null, and its name field must not be null or empty.
     * @return a ResponseEntity containing the status and a message indicating
     *         the success or failure of the operation. Returns a 200 status for
     *         successful addition, or a 400 status in case of errors.
     */
    @PostMapping
    public ResponseEntity<String> addTeam(@RequestBody Team team) {
        if (team == null || team.getName() == null || team.getName().trim().isEmpty()) {
            return ResponseEntity.status(400).body("Invalid team data. Team name is required.");
        }

        int rowsAffected = teamRepository.save(team);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("New team has been added successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to add the new team.");
        }
    }

    /**
     * Updates the attributes of an existing team entity identified by its ID.
     * This method supports partial updates to the team object, currently limited to updating the team name.
     * If the provided update data is invalid, the method returns an appropriate error message.
     *
     * @param id the unique identifier of the team to update
     * @param updates a map containing the fields to update and their new values
     *                (currently supports only the "name" field)
     * @return a ResponseEntity containing a success message, an error message, or
     *         an appropriate HTTP status based on the outcome of the operation
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTeam(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        if (updates == null || updates.isEmpty()) {
            return ResponseEntity.status(400).body("Update data cannot be null or empty.");
        }

        Optional<Team> optionalTeam = this.teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            return ResponseEntity.status(404).body("Team with ID " + id + " not found.");
        }

        Team existingTeam = optionalTeam.get();

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key)) {
                if (value == null || ((String) value).trim().isEmpty()) {
                    return ResponseEntity.status(400).body("Invalid team name.");
                }
                existingTeam.setName(value);
            } else {
                return ResponseEntity.status(400).body("Failed to update the team with ID " + id + ". Body element not recognized.");
            }
        }

        int rowsAffected = teamRepository.update(existingTeam);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Team with ID " + id + " has been updated.");
        } else {
            return ResponseEntity.status(400).body("Failed to update the team with ID " + id + ".");
        }
    }
}