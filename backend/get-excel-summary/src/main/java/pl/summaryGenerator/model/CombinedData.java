package pl.summaryGenerator.model;

import java.util.Date;

/**
 * Represents combined data for an employee, including personal and professional
 * details.
 */
public class CombinedData {

    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private Date hireDate;
    private String role;
    private String team;

    /**
     * Constructs a new CombinedData object with all attributes.
     *
     * @param firstName the first name of the employee
     * @param lastName  the last name of the employee
     * @param email     the email address of the employee
     * @param phone     the phone number of the employee
     * @param hireDate  the hiring date of the employee
     * @param role      the role or position of the employee in the organization
     * @param team      the team name of the employee
     */
    public CombinedData(String firstName, String lastName, String email, String phone, Date hireDate, String role,
            String team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
        this.role = role;
        this.team = team;
    }

    public void setFirstName(Object value) {
        this.firstName = (String) value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(Object value) {
        this.lastName = (String) value;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(Object value) {
        this.email = (String) value;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(Object value) {
        this.phone = (String) value;
    }

    public String getPhone() {
        return phone;
    }

    public void setRole(Object value) {
        this.role = (String) value;
    }

    public String getRole() {
        return role;
    }

    public void setHireDate(Object value) {
        this.hireDate = (Date) value;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setTeam(Object value) {
        this.team = (String) value;
    }

    public String getTeam() {
        return team;
    }
}