package exam;

import javax.persistence.*;

@Entity
@Table(name = "birds")
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sci_name")
    private String scientificName;

    @Column(name = "hun_name")
    private String hungarianName;

    public Bird() {
    }

    public Bird(String scientificName, String hungarianName) {
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public Bird(Long id, String scientificName, String hungarianName) {
        this.id = id;
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
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
}
