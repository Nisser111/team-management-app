package pl.menagment_system.team_menagment_system.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;


/**
 * A Data Transfer Object (DTO) class used to capture and validate input data
 * for employee-related operations such as creation or updates within the system.
 *
 * The EmployeeRequestDTO ensures data validity by enforcing constraints
 * on the fields via validation annotations. This helps in maintaining data integrity
 * when processing employee information within the application.
 *
 * The class also defines two marker interfaces, `Create` and `Update`, used
 * to differentiate validation groups for Create and Update operations.
 *
 * Validation constraints applied include:
 * - Required fields such as first name, last name, email, phone number, hire date, role, and team ID.
 * - Email format validation to ensure a valid email address is provided.
 * - String length constraints for first name and last name.
 * - Pattern matching to validate the format of phone numbers.
 * - Date constraint to ensure the hire date is not in the future.
 * - Positive value requirement for numeric fields such as the team ID.
 *
 * This class is primarily used to transfer data between the application layers
 * while enforcing the required validations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRequestDTO {
    public interface Create {}
    public interface Update {}
    /**
     * Represents the first name of an employee.
     * It is a mandatory field and must meet the following requirements:
     * - Cannot be blank.
     * - Must contain between 2 and 50 characters.
     */
    @NotBlank(message = "First name is required", groups = Create.class)
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters", groups = {Create.class, Update.class})
    private String firstName;

    /**
     * Represents the last name of the employee.
     * It is a required field and must contain between 2 and 50 characters.
     */
    @NotBlank(message = "Last name is required", groups = Create.class)
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters", groups = {Create.class, Update.class})
    private String lastName;

    /**
     * Represents the email address of the employee.
     * This field is mandatory and must be in a valid email address format.
     * Validation Constraints:
     * - Cannot be null or blank.
     * - Must follow a standard email address format.
     */
    @NotBlank(message = "Email is required", groups = Create.class)
    @Email(message = "Invalid email address format", groups = {Create.class, Update.class})
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
    @NotBlank(message = "Phone number is required", groups = Create.class)
    @Pattern(regexp = "(?:([+]\\d{1,4})[-.\\s]?)?(?:[(](\\d{1,3})[)][-.\\s]?)?(\\d{1,4})[-.\\s]?(\\d{1,4})[-.\\s]?(\\d{1,9})",
            message = "Phone number must be valid", groups = {Create.class, Update.class})
    private String phone;

    /**
     * Represents the hire date of an employee.
     * Must be a past or present date.
     * Cannot be null.
     */
    @NotNull(message = "Hire date is required", groups = Create.class)
    @PastOrPresent(message = "Hire date cannot be in the future", groups = {Create.class, Update.class})
    private Date hireDate;

    /**
     * Represents the role assigned to the employee.
     * This field is mandatory and cannot be blank.
     * Validation ensures that the role is provided when creating or updating an employee instance.
     */
    @NotBlank(message = "Role is required", groups = Create.class)
    private String role;

    /**
     * Represents the unique identifier of a team.
     * Must be a non-null positive integer.
     * Validations:
     * - Cannot be null; throws a validation exception if null (NotNull).
     * - Must be a positive number; throws a validation exception if negative or zero (Positive).
     */
    @NotNull(message = "Team ID is required", groups = Create.class)
    @Positive(message = "Team ID must be a positive number", groups = {Create.class, Update.class})
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