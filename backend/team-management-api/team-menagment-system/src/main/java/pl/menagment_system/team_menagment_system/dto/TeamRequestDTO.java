package pl.menagment_system.team_menagment_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A Data Transfer Object (DTO) class used to capture and validate data
 * related to team creation or updates within the system.
 *
 * The TeamRequestDTO enforces the following validations on the provided data:
 * - The team name must not be blank.
 * - The team name must have a length between 2 and 50 characters.
 *
 * This class is typically used to transfer data between application layers
 * and ensures that only valid data is accepted during operations.
 */
public class TeamRequestDTO {
    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    private String name;

    /**
     * Retrieves the name of the team.
     *
     * @return the name of the team as a String
     */
    public String getName() {
        return  name;
    }
}
