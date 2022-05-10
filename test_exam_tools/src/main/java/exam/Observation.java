package exam;

import javax.persistence.*;

@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private BirdSpecies birdSpecies;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @Column(name = "individuals", nullable = false)
    private int numberOfIndividuals;

    public Observation() {
    }

    public Observation(BirdSpecies birdSpecies, Sex sex, int numberOfIndividuals) {
        this.birdSpecies = birdSpecies;
        this.sex = sex;
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public Observation(Long id, BirdSpecies birdSpecies, Sex sex, int numberOfIndividuals) {
        this.id = id;
        this.birdSpecies = birdSpecies;
        this.sex = sex;
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BirdSpecies getBirdSpecies() {
        return birdSpecies;
    }

    public void setBirdSpecies(BirdSpecies birdSpecies) {
        this.birdSpecies = birdSpecies;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getNumberOfIndividuals() {
        return numberOfIndividuals;
    }

    public void setNumberOfIndividuals(int numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }
}
