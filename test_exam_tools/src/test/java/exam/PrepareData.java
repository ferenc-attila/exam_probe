package exam;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PrepareData {

    private EntityManagerFactory factory;

    private FamilyDao familyDao;

    private BirdSpeciesDao birdSpeciesDao;

    private ObservationDao observationDao;

    public void insertData() {
        factory = Persistence.createEntityManagerFactory("pu");
        familyDao = new FamilyDao(factory);
        birdSpeciesDao = new BirdSpeciesDao(factory);
        observationDao = new ObservationDao(factory);
        createLaniidae();
        createFalconidae();
        createTurdidae();
        createObservations();
    }

    private void createObservations() {
        observationDao.saveMultipleObservations(List.of(
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Turdus merula").get(), Sex.FEMALE, 1),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Falco vespertinus").get(), Sex.MALE, 3),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Lanius collurio").get(), Sex.MALE, 5),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Lanius excubitor").get(), Sex.INDET, 2),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Falco vespertinus").get(), Sex.INDET, 284),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Falco tinnunculus").get(), Sex.PAIR, 2),
                new Observation(birdSpeciesDao.findSpeciesByScientificName("Falco vespertinus").get(), Sex.INDET, 15)));
    }

    private void createTurdidae() {
        Family turdidae = new Family("Turdidae", "rigófélék");
        turdidae.addBirdSpecies(new BirdSpecies("Turdus merula", "feketerigó"));
        turdidae.addBirdSpecies(new BirdSpecies("Erithacus rubecola", "vörösbegy"));
        turdidae.addBirdSpecies(new BirdSpecies("Turdus philomelos", "énekes rigó"));
        familyDao.saveFamily(turdidae);
    }

    private void createFalconidae() {
        Family falconidae = new Family("Falconidae", "sólyomfélék");
        falconidae.addBirdSpecies(new BirdSpecies("Falco vespertinus", "kék vércse"));
        falconidae.addBirdSpecies(new BirdSpecies("Falco tinnunculus", "vörös vércse"));
        falconidae.addBirdSpecies(new BirdSpecies("Falco subbuteo", "kabasólyom"));
        falconidae.addBirdSpecies(new BirdSpecies("Falco cherrug", "kerecsen"));
        familyDao.saveFamily(falconidae);
    }

    private void createLaniidae() {
        Family laniidae = new Family("Laniidae", "gébicsfélék");
        laniidae.addBirdSpecies(new BirdSpecies("Lanius collurio", "tövisszúró gébics"));
        laniidae.addBirdSpecies(new BirdSpecies("Lanius minor", "kis őrgébics"));
        laniidae.addBirdSpecies(new BirdSpecies("Lanius excubitor", "nagy őrgébics"));
        familyDao.saveFamily(laniidae);
    }
}
