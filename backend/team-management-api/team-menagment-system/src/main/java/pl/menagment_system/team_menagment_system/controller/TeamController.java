package pl.menagment_system.team_menagment_system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.menagment_system.team_menagment_system.dto.TeamRequestDTO;
import pl.menagment_system.team_menagment_system.model.Team;
import pl.menagment_system.team_menagment_system.repository.TeamRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * Handles HTTP GET requests to retrieve all teams from the repository.
     * This method attempts to fetch all records of teams, constructs a response with the appropriate
     * message, data, and success status, and returns it to the client.
     * If an exception occurs, a 500 Internal Server Error is returned with an error message.
     *
     * @return ResponseEntity containing a Map with success status, message, and team data or error details.
     */
    @GetMapping
    public ResponseEntity<Object> getAllTeams() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Team> teams = teamRepository.findAll();

            if (teams.isEmpty()) {
                response.put("success", true);
                response.put("message", "Nie pobrano żadnych zespołów.");
                response.put("data", teams);
                return ResponseEntity.ok(response);
            }

            response.put("success", true);
            response.put("message", "Zespoły pobrane pomyślnie.");
            response.put("data", teams);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd podczas pobierania zespołów.");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Deletes a team identified by its ID.
     *
     * @param id The ID of the team to be deleted.
     * @return A ResponseEntity containing the response details, including a success message if the team was deleted,
     *         or an error message if the team was not found or an unexpected error occurred.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Team> optionalTeam = teamRepository.findById(id);

            // Check if the team exists
            if (optionalTeam.isEmpty()) {
                response.put("success", false);
                response.put("message", "Zespół o ID " + id + " nie został znaleziony.");
                return ResponseEntity.status(404).body(response);
            }

            String teamName = optionalTeam.get().getName();

            // Perform deletion
            int rowsAffected = teamRepository.deleteById(id);
            if (rowsAffected > 0) {
                response.put("success", true);
                response.put("message", "Zespół " + teamName + " został pomyślnie usunięty.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Nie udało się usunąć zespołu o ID " + id + ".");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception ex) {
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd podczas usuwania zespołu.");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }



    /**
     * Adds a new team using the provided team data.
     *
     * @param dto a TeamRequestDTO object containing team information such as the team's name
     * @return a ResponseEntity object containing a response map with success status, message, and data;
     *         returns HTTP 200 if the operation is successful, HTTP 400 for invalid input,
     *         or HTTP 500 if an unexpected error occurs
     */
    @PostMapping
    public ResponseEntity<Object> addTeam(@Valid @RequestBody TeamRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Team team = new Team(
                    0, // ID will be auto-generated
                    dto.getName()
            );

            teamRepository.save(team);

            response.put("success", true);
            response.put("message", "Nowy zespół " + team.getName() + " został pomyślnie dodany.");
            response.put("data", team);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException ex) {
            response.put("success", false);
            response.put("message", "Nieprawidłowe dane wejściowe: " + ex.getMessage());
            return ResponseEntity.status(400).body(response);

        } catch (Exception ex) {
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd podczas dodawania zespołu.");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }



    /**
     * Updates an existing team's details in the database based on the provided ID
     * and the new team information from the request body.
     *
     * @param id the unique identifier of the team to be updated
     * @param dto the new data for the team encapsulated in a TeamRequestDTO object
     * @return a ResponseEntity containing a response map with the success status,
     *         a message, and optionally the updated team object or error details
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateTeam(
            @PathVariable int id,
            @Valid @RequestBody TeamRequestDTO dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if the team exists
            Optional<Team> optionalTeam = this.teamRepository.findById(id);
            if (optionalTeam.isEmpty()) {
                response.put("success", false);
                response.put("message", "Zespół o ID " + id + " nie został znaleziony.");
                return ResponseEntity.status(404).body(response);
            }

            Team existingTeam = optionalTeam.get();
            String oldTeamName = existingTeam.getName();

            // Update the team name
            existingTeam.setName(dto.getName());
            String newTeamName = existingTeam.getName();

            // Save the updates
            int rowsAffected = teamRepository.update(existingTeam);
            if (rowsAffected > 0) {
                response.put("success", true);
                response.put("message", "Zespół " + oldTeamName + " został zaktualizowany na " + newTeamName + ".");
                response.put("data", existingTeam);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Nie udało się zaktualizować zespołu o ID " + id + ".");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception ex) {
            response.put("success", false);
            response.put("message", "Wystąpił nieoczekiwany błąd podczas aktualizowania zespołu.");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}