package com.misfit.entity;

import jakarta.persistence.*;

/**
 * The type Medical.
 */
@Entity(name = "Medical")
@Table(name = "medical")
public class Medical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private int medicalId;
    @Column(name = "m_name")
    private String medicationName;
    @Column(name = "m_date_given")
    private String medicationDateGiven;
    @ManyToOne
    @JoinColumn(name = "c_id", foreignKey = @ForeignKey(name = "Medical_Cat_c_id_fk"))
    private Cat cat;

    /**
     * Instantiates a new Medical.
     */
    public Medical() {
    }

    /**
     * Instantiates a new Medical.
     *
     * @param medicationName      the medication name
     * @param medicationDateGiven the medication date given
     * @param cat                 the cat
     */
    public Medical(String medicationName, String medicationDateGiven, Cat cat) {
        this.medicationName = medicationName;
        this.medicationDateGiven = medicationDateGiven;
        this.cat = cat;
    }

    /**
     * Gets medical id.
     *
     * @return the medical id
     */
    public int getMedicalId() {
        return medicalId;
    }

    /**
     * Sets medical id.
     *
     * @param medicalId the medical id
     */
    public void setMedicalId(int medicalId) {
        this.medicalId = medicalId;
    }

    /**
     * Gets medication name.
     *
     * @return the medication name
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets medication name.
     *
     * @param medicationName the medication name
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Gets medication date given.
     *
     * @return the medication date given
     */
    public String getMedicationDateGiven() {
        return medicationDateGiven;
    }

    /**
     * Sets medication date given.
     *
     * @param medicationDateGiven the medication date given
     */
    public void setMedicationDateGiven(String medicationDateGiven) {
        this.medicationDateGiven = medicationDateGiven;
    }

    /**
     * Gets cat.
     *
     * @return the cat
     */
    public Cat getCat() {
        return cat;
    }

    /**
     * Sets cat.
     *
     * @param cat the cat
     */
    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Medical{" +
                "medicalId=" + medicalId +
                ", medicationName='" + medicationName + '\'' +
                ", medicationDateGiven='" + medicationDateGiven + '\'' +
                '}';
    }
}
