package exam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration tests for methods working with Family entity in the database")
class FamilyDaoIT {

    EntityManagerFactory factory;

    FamilyDao dao;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("pu");
        dao = new FamilyDao(factory);
        new PrepareData().insertData();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    @DisplayName("Find family in the database by id")
    void findFamilyByIdTest() {
        Family ciconiidae = new Family("Ciconiidae", "gólyafélék");
        dao.saveFamily(ciconiidae);

        ciconiidae = dao.findFamilyById(ciconiidae.getId()).get();

        assertThat(ciconiidae.getHungarianName()).isEqualTo("gólyafélék");
    }

    @Test
    @DisplayName("Find family in the database by scientific name")
    void findFamilyWithSpeciesByScientificNameTest() {
        Family laniidae = dao.findFamilyWithSpeciesByScientificName("Laniidae").get();

        assertThat(laniidae.getSpecies())
                .extracting(BirdSpecies::getScientificName)
                .containsExactlyInAnyOrder("Lanius collurio", "Lanius minor", "Lanius excubitor");
    }
}