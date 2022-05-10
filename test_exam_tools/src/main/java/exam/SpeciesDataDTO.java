package exam;

public class SpeciesDataDTO {

    private String familyName;

    private String scientificName;

    private String hungarianName;

    public SpeciesDataDTO(String familyName, String scientificName, String hungarianName) {
        this.familyName = familyName;
        this.scientificName = scientificName;
        this.hungarianName = hungarianName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getHungarianName() {
        return hungarianName;
    }
}
