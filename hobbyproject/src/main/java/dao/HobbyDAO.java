package dao;

import entities.Hobby;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Map;
import java.util.stream.Collectors;

public class HobbyDAO extends CRUDDao {

    private static EntityManagerFactory emf;
    private static HobbyDAO instance;

    public static HobbyDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }

    // US-5: As a user I want to get a list all hobbies + a count of how many are interested in each hobby
    public Map<String, Integer> getAllHobbiesAndHowManyAreAssignedToEach() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT h.name, COUNT(a) FROM Hobby h JOIN h.accountSet a GROUP BY h.name", Object[].class)
                    .getResultStream()
                    .collect(Collectors.toMap(
                            array -> (String) array[0],   // Hobby name
                            array -> (int) array[1]      // Count
                    ));
        }
    }
  //[US-4] As a user I want to get the number of people with a given hobby

    public int getNumberOfPeopleGivenHobby(String hobbyName){
        try(var em = emf.createEntityManager()){
            TypedQuery<Long> query = em.createQuery("SELECT count(a) FROM Account a JOIN a.hobbySet h WHERE h.name = :hobbyName", Long.class);
            query.setParameter("hobbyName", hobbyName);
            return query.getSingleResult().intValue();
        }
    }
}
