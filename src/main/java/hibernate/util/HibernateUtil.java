package hibernate.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static HibernateUtil instance;
    private final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("myDatabase");
    private final EntityManager em = factory.createEntityManager();

    private HibernateUtil() {
    }

    public static HibernateUtil getInstance() {
        if (null == instance) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public void save(Object o) {
        em.getTransaction().begin();
        if (!em.contains(o)) {
            em.persist(o);
            em.flush();
        }
        em.getTransaction().commit();
    }

    public void delete(Class clazz, Long id) {

        em.getTransaction().begin();
        Object o = em.find(clazz, id);
        if (null != o) {
            em.remove(o);
        }
        em.getTransaction().commit();
    }

    public EntityManager getEm() {
        return em;
    }
}
