package pl.menagment_system.team_menagment_system.model;

import lombok.Data;

import java.util.Date;

/**
 * The Employee class represents an employee entity with various attributes
 * such as name, email, phone, hire date, role, and team association.
 */
public class Employee {

    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private Date hireDate;
    private String role;
    private int teamId;

    /**
     * Constructs a new Employee object with all attributes.
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

    public void setId(Object value) {
        this.id = (int) value;
    }

    public int getId() {
        return this.id;
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

    public void setTeamId(Object value) {
        this.teamId = (int) value;
    }

    public int getTeamId() {
        return teamId;
    }
}