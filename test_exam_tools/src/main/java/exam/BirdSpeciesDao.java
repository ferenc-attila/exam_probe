package exam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BirdSpeciesDao {

    private EntityManagerFactory factory;

    public BirdSpeciesDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Optional<BirdSpecies> saveSpecies(BirdSpecies species) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(species);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return Optional.of(species);
    }

    public Optional<BirdSpecies> findSpeciesById(long id) {
        EntityManager em = factory.createEntityManager();
        Optional<BirdSpecies> result;
        try {
            result = Optional.ofNullable(em.find(BirdSpecies.class, id));
        } finally {
            em.close();
        }
        return result;
    }

    public Optional<BirdSpecies> findSpeciesByScientificName(String scientificName) {
        EntityManager em = factory.createEntityManager();
        Optional<BirdSpecies> result;
        try {
            result = Optional.ofNullable(em.createQuery("select b from BirdSpecies b where b.scientificName = :scientificName",
                            BirdSpecies.class)
                    .setParameter("scientificName", scientificName)
                    .getSingleResult());
        } finally {
            em.close();
        }
        return result;
    }

    public List<SpeciesDataDTO> listAllSpeciesWithFamilyName() {
        EntityManager em = factory.createEntityManager();
        List<SpeciesDataDTO> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("select new exam.SpeciesDataDTO(b.family.scientificName, b.scientificName, b.hungarianName) from BirdSpecies b",
                            SpeciesDataDTO.class)
                    .getResultList());
        } finally {
            em.close();
        }
        return result;
    }

    public List<String> listAllSpeciesScientificName() {
        EntityManager em = factory.createEntityManager();
        List<String> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("select b from BirdSpecies b",
                            BirdSpecies.class)
                    .getResultStream().map(BirdSpecies::getScientificName).toList());
        } finally {
            em.close();
        }
        return result;
    }

    public BirdSpecies findSpeciesByHungarianName(String hungarianName) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BirdSpecies> query = cb.createQuery(BirdSpecies.class);
        Root<BirdSpecies> obs = query.from(BirdSpecies.class);
        query.select(obs)
                .where(cb.equal(obs.get("hungarianName"), hungarianName));
        BirdSpecies result = em.createQuery(query).getSingleResult();
        em.close();
        return result;
    }
}
