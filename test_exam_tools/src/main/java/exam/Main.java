package exam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Bird bird = new Bird("Lanius collurio", "tövisszúró gébics");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(bird);
        em.getTransaction().commit();

        em.close();

        EntityManager em2 = factory.createEntityManager();

        Bird result = em2.find(Bird.class, bird.getId());
        em2.close();

        System.out.println(String.join(", ", result.getHungarianName(), result.getScientificName()));
    }
}
