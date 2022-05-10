package exam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests of methods working with birdspecies")
class BirdSpeciesDaoIT {

    EntityManagerFactory factory;

    BirdSpeciesDao dao;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("pu");
        dao = new BirdSpeciesDao(factory);
        new PrepareData().insertData();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("Save species and find it with id")
    void saveAndFindSpeciesByIdTest() {
        Family falconidae = new FamilyDao(factory).findFamilyWithSpeciesByScientificName("Falconidae").get();
        BirdSpecies species = new BirdSpecies(falconidae, "Falco peregrinus", "vándorsólyom");
        dao.saveSpecies(species);

        species = dao.findSpeciesById(species.getId()).get();
        assertThat(species.getHungarianName()).isEqualTo("vándorsólyom");
    }

    @Test
    @DisplayName("Find species in the database by scientific name")
    void findSpeciesByScientificNameTest() {
        BirdSpecies saker = dao.findSpeciesByScientificName("Falco cherrug").get();

        assertThat(saker.getHungarianName()).isEqualTo("kerecsen");
    }

    @Test
    @DisplayName("List all species with family names in DTOs")
    void listAllSpeciesWithFamilyNameTest() {
        List<SpeciesDataDTO> result = dao.listAllSpeciesWithFamilyName();

        assertThat(result).hasSize(10)
                .extracting(SpeciesDataDTO::getHungarianName)
                .contains("kerecsen", "kis őrgébics", "vörösbegy", "kék vércse");
    }
}