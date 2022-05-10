package exam;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sci_name")
    private String scientificName;

    @Column(name = "hun_name")
    private String hungarianName;

    @OneToMany(mappedBy = "family", cascade = CascadeType.PERSIST)
    private Set<BirdSpecies> species = new HashSet<>();

    public Family() {
    }

    public Family(String scientificName, String hungarianName) {
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public Family(Long id, String scientificName, String hungarianName) {
        this.id = id;
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public void addBirdSpecies(BirdSpecies birdSpecies) {
        this.species.add(birdSpecies);
        birdSpecies.setFamily(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<BirdSpecies> getSpecies() {
        return species;
    }

    public void setSpecies(Set<BirdSpecies> species) {
        this.species = species;
    }
}
