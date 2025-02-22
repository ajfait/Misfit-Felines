package matc.entity;

import jakarta.persistence.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Cat.
 */
@Entity(name = "Cat")
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
    public Cat(String name, String sex, String dob, String breed, String bio, boolean adoptable, Person person) {
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
    public String getDob() {
        return dob;
    }

    /**
     * Sets dob.
     *
     * @param dob the dob
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
     * @param dob        the dob
     * @param dateFormat the date format
     * @return the int
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
     * @return the age
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
                "catId=" + catId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dob='" + dob + '\'' +
                ", breed='" + breed + '\'' +
                ", bio='" + bio + '\'' +
                ", adoptable=" + adoptable +
                '}';
    }
}
