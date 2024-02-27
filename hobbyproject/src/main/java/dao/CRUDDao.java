package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import config.HibernateConfig;

public abstract class CRUDDao {
    static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

        //CREATE
        public <T> void create (T t){
            try(EntityManager em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
            }
        }

        //READ
        public <T> T read (Class<T> tClass, int id){
            try(EntityManager em = emf.createEntityManager()){
                //Entity is managed after being retrieved
                T entity = em.find(tClass, id);
                // entity is detached after the entity is returned
                return entity;
            }
        }


        //UPDATE
        public <T> T update (T t){
            try (var em = emf.createEntityManager()) {
                em.getTransaction().begin();
                //The entity is managed after the merge
                T entity = em.merge(t);
                // entity is in stransient state (after being retrieved)
                em.getTransaction().commit();
                //entitry is detached after it is returned
                return entity;
            }
        }


        //DELETE
        public <T> void delete (Class<T> tClass, int id){
            try (var em = emf.createEntityManager()) {
                em.getTransaction().begin();
                // the entity is managed after it is found/retrieved
                T entity = read(tClass, id);
                if (entity != null) {
                    em.remove(entity);
                    System.out.println("The entity has been deleted");
                } else {
                    System.out.println("The entity you are looking for does not exist");
                }
                em.getTransaction().commit();
            }
        }

        //GET-ALL
        private <T> void getAll (EntityManager em, T t){
        }
}



