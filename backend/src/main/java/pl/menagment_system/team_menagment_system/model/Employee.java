package pl.menagment_system.team_menagment_system.model;

import java.util.Date;

/**
 * The Employee class represents an employee entity with various attributes
 * such as name, email, phone, hire date, role, and team association.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date hireDate;
    private String role;
    private int teamId;

    /**
     * Constructs a new Employee object with the specified details.
     *
     * @param id        the unique identifier of the employee
     * @param firstName the first name of the employee
     * @param lastName  the last name of the employee
     * @param email     the email address of the employee
     * @param phone     the phone number of the employee
     * @param hireDate  the hiring date of the employee
     * @param role      the role or position of the employee in the organization
     * @param teamId    the team ID the employee is associated with
     */
    public Employee(int id, String firstName, String lastName, String email, String phone, Date hireDate, String role, int teamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
        this.role = role;
        this.teamId = teamId;
    }

    /**
     * Gets the first name of the employee.
     *
     * @return the first name
     */
    public Object getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the employee.
     *
     * @return the last name
     */
    public Object getLastName() {
        return lastName;
    }

    /**
     * Gets the email address of the employee.
     *
     * @return the email
     */
    public Object getEmail() {
        return email;
    }

    /**
     * Gets the phone number of the employee.
     *
     * @return the phone number
     */
    public Object getPhone() {
        return phone;
    }

    /**
     * Gets the hiring date of the employee.
     *
     * @return the hire date
     */
    public Object getHireDate() {
        return hireDate;
    }

    /**
     * Gets the role of the employee.
     *
     * @return the role
     */
    public Object getRole() {
        return role;
    }

    /**
     * Gets the team ID associated with the employee.
     *
     * @return the team ID
     */
    public Object getTeamId() {
        return teamId;
    }

    /**
     * Gets the unique ID of the employee.
     *
     * @return the ID
     */
    public Object getId() {
        return id;
    }

    /**
     * Sets the first name of the employee.
     *
     * @param firstName the new first name
     */
    public void setFirstName(Object firstName) {
        this.firstName = (String) firstName;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param lastName the new last name
     */
    public void setLastName(Object lastName) {
        this.lastName = (String) lastName;
    }

    /**
     * Sets the email address of the employee.
     *
     * @param email the new email address
     */
    public void setEmail(Object email) {
        this.email = (String) email;
    }

    /**
     * Sets the phone number of the employee.
     *
     * @param phone the new phone number
     */
    public void setPhone(Object phone) {
        this.phone = (String) phone;
    }

    /**
     * Sets the hiring date of the employee.
     *
     * @param hireDate the new hire date
     */
    public void setHireDate(Object hireDate) {
        this.hireDate = (Date) hireDate;
    }

    /**
     * Sets the role of the employee.
     *
     * @param role the new role
     */
    public void setRole(Object role) {
        this.role = (String) role;
    }

    /**
     * Sets the team ID associated with the employee.
     *
     * @param teamId the new team ID
     */
    public void setTeamId(Object teamId) {
        this.teamId = (int) teamId;
    }

    /**
     * Sets the unique ID of the employee.
     *
     * @param id the new ID
     */
    public void setId(Object id) {
        this.id = (int) id;
    }
}
