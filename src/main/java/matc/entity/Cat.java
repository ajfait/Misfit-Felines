package matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A class to represent a cat.
 *
 * @author afait
 */
@Entity
@Table(name = "Cat")
public class Cat {
    // Creates instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private int catId;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_sex")
    private String sex;
    @Column(name = "c_dob")
    private String dob;
    @Column(name = "c_breed")
    private String breed;
    @Column(name = "c_bio")
    private String bio;
    @Column(name = "c_adoptable")
    private boolean adoptable;

    /**
     * Instantiates a new Cat.
     */
    public Cat() {
    }

    /**
     * Instantiates a new Cat.
     *
     * @param catId     cat's id
     * @param name      cat's name
     * @param sex       cat's sex
     * @param dob       cat's date of birth
     * @param breed     cat's breed
     * @param bio       cat's bio
     * @param adoptable adoptable status
     */
    public Cat(int catId, String name, String sex, String dob, String breed, String bio, boolean adoptable) {
        this.catId = catId;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.breed = breed;
        this.bio = bio;
        this.adoptable = adoptable;
    }

    /**
     * Gets catId.
     *
     * @return the catId
     */
    public int getCatId() {
        return catId;
    }

    /**
     * Sets catId.
     *
     * @param catId the catId
     */
    public void setCatId(int catId) {
        this.catId = catId;
    }

    /**
     * Gets name.
     *
     * @return name
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
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets date of birth.
     *
     * @param dob date of birth
     */
    public void setDob(String dob) {
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
     * Gets adoptable status.
     *
     * @return the adoptable status
     */
    public boolean getAdoptable() {
        return adoptable;
    }

    /**
     * Sets adoptable status.
     *
     * @param adoptable the adoptable status
     */
    public void setAdoptable(boolean adoptable) {
        this.adoptable = adoptable;
    }

    /**
     * Method that uses the current date and the cat's birthdate to
     * calculate their age.
     *
     * @param dob date of birth
     * @param dateFormat date format
     * @return period.getYears()
     */
    public int calculateAge(String dob, String dateFormat) {
        // Creates DateTimeFormatter object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        // Formats birthdate
        LocalDate birthDate = LocalDate.parse(dob, formatter);

        // Creates LocalDate object
        LocalDate currentDate = LocalDate.now();

        // Calculates the period between current date and birthdate
        Period period = Period.between(birthDate, currentDate);

        // Returns period between in years
        return period.getYears();
    }

    /**
     * Gets age.
     *
     * @return age
     */
    public int getAge() {
        // Sets date format
        String dateFormat = "yyyy-MM-dd";

        // Returns calculated age
        return calculateAge(dob, dateFormat);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "catId='" + catId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dob='" + dob + '\'' +
                ", breed='" + breed + '\'' +
                ", bio='" + bio + '\'' +
                ", adoptable='" + adoptable + '\'' +
                // ", age='" + getAge() + '\'' +
                '}';
    }
}
