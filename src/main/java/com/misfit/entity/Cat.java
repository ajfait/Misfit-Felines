package com.misfit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Cat.
 */
@Entity(name = "Cat")
@Table(name = "cat")
public class Cat {
    // Creates instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int catId;
    @Column(name = "c_name")
    @NotBlank(message = "Cat name is required")
    private String name;
    @Column(name = "c_sex")
    @Pattern(regexp = "^(Male|Female)$", message = "Sex must be male or female")
    private String sex;
    @Column(name = "c_dob")
    @PastOrPresent(message = "Birthdate cannot be in the future")
    private LocalDate dob;
    @Column(name = "c_breed")
    @NotNull(message = "Breed is required")
    private String breed;
    @Column(name = "c_bio")
    @NotBlank(message = "Bio is required")
    @Size(max = 3000, message = "Bio must not exceed 3,000 characters")
    private String bio;
    @Column(name = "c_adoptable")
    private boolean adoptable;
    @ManyToOne
    @JoinColumn(name = "p_id", foreignKey = @ForeignKey(name = "Cat_Person_p_id_fk"))
    private Person person;
    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medical> meds = new ArrayList<>();

    /**
     * Instantiates a new Cat.
     */
    public Cat() {
    }

    /**
     * Instantiates a new Cat.
     *
     * @param name      the name
     * @param sex       the sex
     * @param dob       the dob
     * @param breed     the breed
     * @param bio       the bio
     * @param adoptable the adoptable
     * @param person    the person
     */
    public Cat(String name, String sex, LocalDate dob, String breed, String bio, boolean adoptable, Person person) {
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.breed = breed;
        this.bio = bio;
        this.adoptable = adoptable;
        this.person = person;
    }

    /**
     * Add medical.
     *
     * @param medical the medical
     */
    public void addMedical(Medical medical) {
        meds.add(medical);
        medical.setCat(this);
    }

    /**
     * Remove medical.
     *
     * @param medical the medical
     */
    public void removeMedical(Medical medical) {
        meds.remove(medical);
        medical.setCat(null);
    }

    /**
     * Gets cat id.
     *
     * @return the cat id
     */
    public int getCatId() {
        return catId;
    }

    /**
     * Sets cat id.
     *
     * @param catId the cat id
     */
    public void setCatId(int catId) {
        this.catId = catId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets sex.
     *
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets sex.
     *
     * @param sex the sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Gets dob.
     *
     * @return the dob
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets dob.
     *
     * @param dob the dob
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Gets breed.
     *
     * @return the breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Sets breed.
     *
     * @param breed the breed
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * Gets bio.
     *
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets bio.
     *
     * @param bio the bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }


    /**
     * Is adoptable boolean.
     *
     * @return the boolean
     */
    public boolean isAdoptable() {
        return adoptable;
    }

    /**
     * Sets adoptable.
     *
     * @param adoptable the adoptable
     */
    public void setAdoptable(boolean adoptable) {
        this.adoptable = adoptable;
    }

    /**
     * Gets person.
     *
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets person.
     *
     * @param person the person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gets meds.
     *
     * @return the meds
     */
    public List<Medical> getMeds() {
        return meds;
    }

    /**
     * Sets meds.
     *
     * @param meds the meds
     */
    public void setMeds(List<Medical> meds) {
        this.meds = meds;
    }

    /**
     * Calculate age int.
     *
     * @param dob the dob
     * @return the int
     */
    public int calculateAge(LocalDate dob) {
        // Creates LocalDate object
        LocalDate currentDate = LocalDate.now();

        // Calculates the period between current date and birthdate
        Period period = Period.between(dob, currentDate);

        // Returns period between in years
        return period.getYears();
    }

    /**
     * Gets formatted age.
     *
     * @return the formatted age
     */
    public String getFormattedAge() {
        if (dob == null) {
            return "Unknown";
        }

        LocalDate now = LocalDate.now();
        Period age = Period.between(dob, now);

        int years = age.getYears();
        int months = age.getMonths();

        StringBuilder sb = new StringBuilder();

        if (years > 0) {
            sb.append(years).append(" year").append(years > 1 ? "s" : "");
        }

        if (months > 0) {
            if (sb.length() > 0) sb.append(" and ");
            sb.append(months).append(" month").append(months > 1 ? "s" : "");
        }

        if (sb.length() == 0) {
            sb.append("Less than a month");
        }

        return sb.toString();
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        // Returns calculated age
        return calculateAge(dob);
    }

    @Override
    public String toString() {
        return "Cat{" + "catId=" + catId + ", name='" + name + '\'' + ", sex='" + sex + '\'' + ", dob='" + dob + '\'' + ", breed='" + breed + '\'' + ", bio='" + bio + '\'' + ", adoptable=" + adoptable + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return catId == cat.catId && adoptable == cat.adoptable && Objects.equals(name, cat.name) && Objects.equals(sex, cat.sex) && Objects.equals(dob, cat.dob) && Objects.equals(breed, cat.breed) && Objects.equals(bio, cat.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catId, name, sex, dob, breed, bio, adoptable);
    }
}
