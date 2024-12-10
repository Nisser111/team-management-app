package pl.menagment_system.team_menagment_system.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The Employee class represents an employee entity with various attributes
 * such as name, email, phone, hire date, role, and team association.
 */
@Getter
@Setter
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
}