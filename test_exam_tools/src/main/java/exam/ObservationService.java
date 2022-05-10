package exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ObservationService {

    private ObservationDao observationDao;

    private BirdSpeciesDao birdSpeciesDao;

    public ObservationService(ObservationDao observationDao, BirdSpeciesDao birdSpeciesDao) {
        this.observationDao = observationDao;
        this.birdSpeciesDao = birdSpeciesDao;
    }

    public Optional<Observation> saveObservation(Observation observation) {
        validateObservation(observation);
        return observationDao.saveObservation(observation);
    }

    public Map<String, Integer> getSumOfIndividualsBySpecies() {
        Map<String, Integer> result = new HashMap<>();
        List<Observation> observations = observationDao.listAllObservations();
        for (Observation actual : observations) {
            String scientificName = actual.getBirdSpecies().getScientificName();
            if (!result.containsKey(scientificName)) {
                result.put(scientificName, 0);
            }
            result.put(scientificName, result.get(scientificName) + actual.getNumberOfIndividuals());
        }
        return result;
    }

    private void validateObservation(Observation observation) {
        validateSpecies(observation.getBirdSpecies());
        validatePair(observation.getSex(), observation.getNumberOfIndividuals());
    }

    private void validatePair(Sex sex, int numberOfIndividuals) {
        if (sex == Sex.PAIR && (numberOfIndividuals == 0 || numberOfIndividuals % 2 != 0)) {
            throw new InvalidIndividualsInPairException("If sex is 'pairs', the number of individuals must be even.");
        }
    }

    private void validateSpecies(BirdSpecies birdSpecies) {
        List<String> speciesInDatabase = birdSpeciesDao.listAllSpeciesWithFamilyName()
                .stream().map(SpeciesDataDTO::getScientificName)
                .toList();
        if (!speciesInDatabase.contains(birdSpecies.getScientificName())) {
            throw new TaxonNotFoundException("Species " + birdSpecies.getScientificName() + " not found in the database!");
        }
    }
}
