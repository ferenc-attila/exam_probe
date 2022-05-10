package exam;

import javax.persistence.*;

@Entity
@Table(name = "species")
public class BirdSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id")
    public Family family;

    @Column(name = "sci_name")
    private String scientificName;

    @Column(name = "hun_name")
    private String hungarianName;

    public BirdSpecies() {
    }

    public BirdSpecies(String scientificName, String hungarianName) {
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public BirdSpecies(Family family, String scientificName, String hungarianName) {
        this.family = family;
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public BirdSpecies(Long id, Family family, String scientificName, String hungarianName) {
        this.id = id;
        this.family = family;
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getHungarianName() {
        return hungarianName;
    }

    public void setHungarianName(String hungarianName) {
        this.hungarianName = hungarianName;
    }
}
