package pl.menagment_system.team_menagment_system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.dto.TeamRequestDTO;
import pl.menagment_system.team_menagment_system.model.Team;
import pl.menagment_system.team_menagment_system.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

/**
 * This controller provides RESTful endpoints for managing teams in the system.
 * It allows the retrieval, addition, deletion, and partial updating of team entities.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        String teamName = teamRepository.findById(id).get().getName();

        int rowsAffected = teamRepository.deleteById(id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok(teamName + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Team with ID " + id + " not found.");
        }
    }


    /**
     * Adds a new team to the system using the data provided in the request body.
     * The team name is mandatory, and the ID will be auto-generated.
     * If the team name is missing or invalid, a 400 Bad Request is returned.
     * If the operation is successful, a 200 OK response with a confirmation message is returned.
     * Otherwise, a 400 Bad Request is returned in case of an error during the operation.
     *
     * @param dto the data transfer object containing the team details to be added
     * @return a ResponseEntity containing:
     *         - a success message with HTTP status 200 if the team is created successfully
     *         - an error message with HTTP status 400 if validation fails or the operation is unsuccessful
     */
    @PostMapping
    public ResponseEntity<String> addTeam(@Valid @RequestBody TeamRequestDTO dto) {
        try {
            Team team = new Team(
                    0, // ID will be auto-generated
                    dto.getName()
            );

            teamRepository.save(team);

            return ResponseEntity.ok("New team has been added successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Unexpected error occurred. Failed to add the new team.");
        }
    }


    /**
     * Updates the details of an existing team identified by its unique ID.
     * The method checks if the team exists, updates its name, and saves the changes.
     * If the team does not exist or the update operation fails, an appropriate response is returned.
     *
     * @param id the unique identifier of the team to be updated
     * @param dto the data transfer object containing the updated team details
     * @return a ResponseEntity containing:
     *         - a success message with HTTP status 200 if the team is updated successfully
     *         - an error message with HTTP status 404 if the team is not found
     *         - an error message with HTTP status 400 if the update operation fails
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTeam(
            @PathVariable int id,
            @Valid @RequestBody TeamRequestDTO dto) {
        // Check if the team exists
        Optional<Team> optionalTeam = this.teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            return ResponseEntity.status(404).body("Team with ID " + id + " not found.");
        }

        Team existingTeam = optionalTeam.get();
        String oldTeamName = existingTeam.getName();

        // Update the team name
        existingTeam.setName(dto.getName());
        String newTeamName = existingTeam.getName();

        // Save the updates
        int rowsAffected = teamRepository.update(existingTeam);
        if (rowsAffected > 0) {
            return ResponseEntity.ok(oldTeamName + " has been updated to " + newTeamName);
        } else {
            return ResponseEntity.status(400).body("Failed to update the team with ID " + id + ".");
        }
    }
}