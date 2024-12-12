package pl.menagment_system.team_menagment_system.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * A Data Transfer Object (DTO) class for capturing and validating
 * employee-related data required in operations such as employee creation or update.
 *
 * The EmployeeRequestDTO class ensures that all fields are properly validated
 * and adhere to the specified constraints to maintain data integrity.
 *
 * Fields include information such as the employee's name, contact details,
 * role, hire date, and associated team ID.
 *
 * Validation is enforced through annotations such as:
 * - @NotBlank: Ensures a string field is not null or empty.
 * - @Size: Enforces character length constraints.
 * - @Pattern: Validates strings against specific patterns.
 * - @Email: Validates a string as an email address.
 * - @NotNull: Ensures an object field is not null.
 * - @PastOrPresent: Ensures a date field is not in the future.
 * - @Positive: Enforces that a numeric field is greater than zero.
 *
 * This DTO is typically used to collect user inputs and transfer data
 * within layers of an application, such as from a client to a backend system.
 */
public class EmployeeRequestDTO {
    /**
     * Represents the first name of an employee.
     * It is a mandatory field and must meet the following requirements:
     * - Cannot be blank.
     * - Must contain between 2 and 50 characters.
     */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    /**
     * Represents the last name of the employee.
     * It is a required field and must contain between 2 and 50 characters.
     */
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    /**
     * Represents the email address of the employee.
     * This field is mandatory and must be in a valid email address format.
     * Validation Constraints:
     * - Cannot be null or blank.
     * - Must follow a standard email address format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address format")
    private String email;

    /**
     * Represents the phone number of an employee.
     *
     * The phone number must not be blank and must adhere to a valid format.
     * Validation includes:
     * - Ensuring the phone number is not empty.
     * - Ensuring the phone number matches the specified pattern:
     *   - Optionally starts with a plus sign and country code.
     *   - May include area code in parentheses.
     *   - Allows spaces, hyphens, or dots as separators.
     *   - Includes the main phone number with additional groupings if required.
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(?:([+]\\d{1,4})[-.\\s]?)?(?:[(](\\d{1,3})[)][-.\\s]?)?(\\d{1,4})[-.\\s]?(\\d{1,4})[-.\\s]?(\\d{1,9})",
            message = "Phone number must be valid")
    private String phone;

    /**
     * Represents the hire date of an employee.
     * Must be a past or present date.
     * Cannot be null.
     */
    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    private Date hireDate;

    /**
     * Represents the role assigned to the employee.
     * This field is mandatory and cannot be blank.
     * Validation ensures that the role is provided when creating or updating an employee instance.
     */
    @NotBlank(message = "Role is required")
    private String role;

    /**
     * Represents the unique identifier of a team.
     * Must be a non-null positive integer.
     * Validations:
     * - Cannot be null; throws a validation exception if null (NotNull).
     * - Must be a positive number; throws a validation exception if negative or zero (Positive).
     */
    @NotNull(message = "Team ID is required")
    @Positive(message = "Team ID must be a positive number")
    private Integer teamId;

    /**
     * Retrieves the first name of the employee.
     *
     * @return the first name of the employee as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the employee.
     *
     * @return the last name of the employee as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the email address of the employee.
     *
     * @return the email address as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the phone number associated with the employee.
     *
     * @return the phone number as a String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Retrieves the hire date of the employee.
     *
     * @return the hire date of the employee
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * Retrieves the role of the employee.
     *
     * @return the role of the employee as a String
     */
    public String getRole() {
        return role;
    }

    /**
     * Retrieves the team ID associated with the employee.
     *
     * @return the team ID, which must be a positive integer.
     */
    public Integer getTeamId() {
        return teamId;
    }
}