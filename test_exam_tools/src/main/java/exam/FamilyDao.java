package exam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class FamilyDao {

    private EntityManagerFactory factory;

    public FamilyDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Optional<Family> saveFamily(Family family) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(family);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return Optional.of(family);
    }

    public Optional<Family> findFamilyById(long id) {
        EntityManager em = factory.createEntityManager();
        Optional<Family> result;
        try {
            result = Optional.ofNullable(em.find(Family.class, id));
        } finally {
            em.close();
        }
        return result;
    }

    public Optional<Family> findFamilyWithSpeciesByScientificName(String scientificName) {
        EntityManager em = factory.createEntityManager();
        Optional<Family> result;
        try {
            result = Optional.ofNullable(em.createQuery("select f from Family f join fetch f.species where f.scientificName = :scientificName",
                            Family.class)
                    .setParameter("scientificName", scientificName)
                    .getSingleResult());
        } finally {
            em.close();
        }
        return result;
    }
}
