package exam;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mocked unit tests for Observation business logic")
class ObservationServiceTest {

    @Mock
    BirdSpeciesDao birdSpeciesDao;

    @Mock
    ObservationDao observationDao;

    @InjectMocks
    ObservationService service;

    @Test
    @DisplayName("Tests what happens when species name is invalid")
    void saveObservationWithSpeciesNotInTheDatabaseTest() {
        List<SpeciesDataDTO> species = List.of(new SpeciesDataDTO("Falconidae", "Falco vespertinus", "kék vércse"),
                new SpeciesDataDTO("Turdidae", "Turdus merula", "fekete rigó"),
                new SpeciesDataDTO("Accipitridae", "Aquila heliaca", "parlagi sas"));
        BirdSpecies falcon = new BirdSpecies("Falco tinnunculus", "vörös vércse");
        Observation observation = new Observation(falcon, Sex.FEMALE, 1);
        when(birdSpeciesDao.listAllSpeciesWithFamilyName()).thenReturn(species);

        TaxonNotFoundException tnfe = assertThrows(TaxonNotFoundException.class,
                () -> service.saveObservation(observation));

        assertThat(tnfe).hasMessage("Species Falco tinnunculus not found in the database!");
    }

    @Test
    @DisplayName("Tests exception when sy enter odd individuals for pair of birds")
    void savePairWithOddNumberOfIndividualsTest() {
        BirdSpecies falcon = new BirdSpecies("Falco tinnunculus", "vörös vércse");
        Observation observation = new Observation(falcon, Sex.PAIR, 1);
        when(birdSpeciesDao.listAllSpeciesWithFamilyName()).thenReturn(List.of(
                new SpeciesDataDTO("Falconidae", "Falco tinnunculus", "vörös vércse")));

        InvalidIndividualsInPairException iipe = assertThrows(InvalidIndividualsInPairException.class,
                () -> service.saveObservation(observation));

        assertThat(iipe).hasMessage("If sex is 'pairs', the number of individuals must be even.");
    }

    @Test
    @DisplayName("Basic statistic test")
    void getSumOfIndividualsBySpeciesTest() {
        BirdSpecies falcon = new BirdSpecies("Falco tinnunculus", "vörös vércse");
        BirdSpecies trush = new BirdSpecies("Turdus merula", "fekete rigó");
        Observation falconPairObserv = new Observation(falcon, Sex.PAIR, 1);
        Observation falconObserv = new Observation(falcon, Sex.MALE, 3);
        Observation trushObserv = new Observation(trush, Sex.PAIR, 2);

        when(observationDao.listAllObservations()).thenReturn(
                List.of(falconObserv, falconPairObserv, trushObserv));

        assertThat(service.getSumOfIndividualsBySpecies()).hasSize(2)
                .extractingFromEntries(Map.Entry::getValue)
                .containsExactlyInAnyOrder(4, 2);
    }
}