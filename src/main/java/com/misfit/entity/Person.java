package com.misfit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Person.
 */
@Entity(name = "Person")
@Table(name = "person")
public class Person {
    // Creates instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int personId;
    @Column(name = "p_first_name")
    @NotBlank(message = "First name is required")
    private String firstName;
    @Column(name = "p_last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Column(name = "p_phone")
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Format required: 608-555-1212")
    private String phone;
    @Column(name = "p_email")
    @NotBlank(message = "Email is required")
    private String email;
    @Column(name = "p_role")
    @Pattern(regexp = "^(Foster|Volunteer)$", message = "Role must be Foster or Volunteer")
    private String role;
    @Column(name = "p_preferences")
    private String preferences;
    @Column(name = "p_admin")
    private boolean admin;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
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
     * Add cat.
     *
     * @param cat the cat
     */
    public void addCat(Cat cat) {
        cats.add(cat);
        cat.setPerson(this);
    }

    /**
     * Remove cat.
     *
     * @param cat the cat
     */
    public void removeCat(Cat cat) {
        cats.remove(cat);
        cat.setPerson(null);
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