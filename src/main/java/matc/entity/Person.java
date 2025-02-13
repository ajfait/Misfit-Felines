package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * A class to represent a person.
 *
 * @author afait
 */
@Entity
@Table(name = "Person")
public class Person {
    // Creates instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int id;
    @Column(name = "p_first_name")
    private String firstName;
    @Column(name = "p_last_name")
    private String lastName;
    @Column(name = "p_phone")
    private String phone;
    @Column(name = "p_email")
    private String email;
    @Column(name = "p_role")
    private String role;
    @Column(name = "p_preferences")
    private String preferences;
    @Column(name = "p_admin")
    private boolean admin;

    /**
     * Instantiates a new Person.
     */
    public Person() {
    }

    /**
     * Instantiates a new Person.
     *
     * @param id            person's id
     * @param firstName     person's first name
     * @param lastName      person's last name
     * @param phone         person's phone number
     * @param email         person's email address
     * @param role          person's role
     * @param preferences   person's preferences
     * @param admin         admin status
     */
    public Person(int id, String firstName, String lastName, String phone, String email, String role, String preferences, boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.preferences = preferences;
        this.admin = admin;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets phone.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets email.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets preferences.
     *
     * @return the preferences
     */
    public String getPreferences() {
        return preferences;
    }

    /**
     * Sets preferences.
     *
     * @param preferences the preferences
     */
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    /**
     * Gets admin status.
     *
     * @return the admin status
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * Sets admin status.
     *
     * @param admin the admin status
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", preferences='" + preferences + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}