package exam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests methods working with Observation entity in the database")
class ObservationDaoIT {

    EntityManagerFactory factory;

    ObservationDao dao;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("pu");
        dao = new ObservationDao(factory);
        new PrepareData().insertData();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("Test to find observations in the database by id")
    void saveAndFindObservationByIdTest() {
        Observation observation = new Observation(new BirdSpeciesDao(factory).findSpeciesByScientificName("Erithacus rubecola").get(), Sex.INDET, 2);
        dao.saveObservation(observation);

        observation = dao.findObservationById(observation.getId()).get();
        assertThat(observation)
                .extracting(o -> o.getBirdSpecies().getHungarianName())
                .isEqualTo("vörösbegy");
    }

    @Test
    @DisplayName("Test listing observations by scientific species name")
    void listObservationsByScientificSpeciesNameTest() {
        List<Observation> result = dao.listObservationsByScientificSpeciesName("Falco vespertinus");

        assertThat(result).hasSize(3)
                .extracting(Observation::getNumberOfIndividuals)
                .containsExactlyInAnyOrder(3, 284, 15);
    }
}