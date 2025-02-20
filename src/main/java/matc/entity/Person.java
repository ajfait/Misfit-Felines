package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Person.
 */
@Entity(name = "Person")
@Table(name = "Person")
public class Person {
    // Creates instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int personId;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Cat> cats = new ArrayList<>();

    /**
     * Instantiates a new Person.
     */
    public Person() {
    }

    /**
     * Instantiates a new Person.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param phone       the phone
     * @param email       the email
     * @param role        the role
     * @param preferences the preferences
     * @param admin       the admin
     */
    public Person(String firstName, String lastName, String phone, String email, String role, String preferences, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.preferences = preferences;
        this.admin = admin;
    }

    /**
     * Gets person id.
     *
     * @return the person id
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Sets person id.
     *
     * @param personId the person id
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Gets first name.
     *
     * @return the first name
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
     * @return the last name
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
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
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Gets cats.
     *
     * @return the cats
     */
    public List<Cat> getCats() {
        return cats;
    }

    /**
     * Sets cats.
     *
     * @param cats the cats
     */
    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    /**
     * Sets admin.
     *
     * @param admin the admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + personId + '\'' +
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