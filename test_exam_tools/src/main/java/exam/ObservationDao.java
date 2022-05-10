package exam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObservationDao {

    private EntityManagerFactory factory;

    public ObservationDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Optional<Observation> saveObservation(Observation observation) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(observation);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return Optional.of(observation);
    }

    public List<Observation> saveMultipleObservations(List<Observation> observations) {
        EntityManager em = factory.createEntityManager();
        try {
            saveListOfObservations(em, observations);
        } finally {
            em.close();
        }
        return observations;
    }

    public Optional<Observation> findObservationById(long id) {
        EntityManager em = factory.createEntityManager();
        Optional<Observation> result;
        try {
            result = Optional.ofNullable(em.find(Observation.class, id));
        } finally {
            em.close();
        }
        return result;
    }

    public List<Observation> listAllObservations() {
        EntityManager em = factory.createEntityManager();
        List<Observation> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("select o from Observation o",
                            Observation.class)
                    .getResultList());
        } finally {
            em.close();
        }
        return result;
    }

    public List<Observation> listObservationsByScientificSpeciesName(String scientificName) {
        EntityManager em = factory.createEntityManager();
        List<Observation> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("select o from Observation o where o.birdSpecies.scientificName = :scientificName",
                            Observation.class)
                    .setParameter("scientificName", scientificName)
                    .getResultList());
        } finally {
            em.close();
        }
        return result;
    }

    private void saveListOfObservations(EntityManager em, List<Observation> observations) {
        em.getTransaction().begin();
        observations.forEach(em::persist);
        em.getTransaction().commit();
    }
}
