package pl.menagment_system.team_menagment_system.model;


/**
 * The Team class represents a team entity with attributes for unique identification
 * and a name.
 */
public class Team {

    private int id;
    private String name;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Object value) {
        this.id = (int) value;
    }

    public int getId() {
        return this.id;
    }

    public void setName(Object value) {
        this.name = (String) value;
    }

    public String getName() {
        return name;
    }
}