package dao;

import entities.Account;
import entities.Hobby;
import filewriter.FileWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
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

    public List<Account> getAllAccountsWithHobby(String hobby) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Account> query = em.createQuery("select a from Account a join a.hobbies h where h.name = ?1", Account.class);
            query.setParameter(1, hobby);

            List<Account> accounts = query.getResultList();

            if(accounts.isEmpty()){
                FileWriter.storeNegative("No persons available with interest in: " + hobby);
                return List.of(); //returns an empty list - avoiding null
            }

            FileWriter.storePositive("Found the following persons with interest in "+ hobby + ": "+ accounts.stream().map(a -> a.getFullName()).collect(Collectors.toList()));

            return accounts;
        }
    }

    // US-5: As a user I want to get a list all hobbies + a count of how many are interested in each hobby
    public Map<String, Integer> getAllHobbiesAndHowManyAreAssignedToEach() {
        try (EntityManager em = emf.createEntityManager()) {

            // Hent data baseret på en unidirektionel @ManyToMany fra Account til Hobby
            Map<String, Integer> map = em.createQuery("SELECT h.name, COUNT(a) FROM Account a " +
                            "JOIN a.hobbies h " +
                            "GROUP BY h.name", Object[].class)
                    .getResultStream()
                    .collect(Collectors.toMap(
                            array -> (String) array[0],     // Hobby name
                            array -> ((Long) array[1]).intValue()      // Count
                    ));

            if(map.isEmpty()){
                FileWriter.storeNegative("An error has acquired");
                return map = null;
            }

            FileWriter.storePositive("Hobbies and number og persons assign" + map);
            return map;
        }
    }
  //[US-4] As a user I want to get the number of people with a given hobby

    public int getNumberOfPeopleWithGivenHobby(String hobbyName){
        try(var em = emf.createEntityManager()){
            TypedQuery<Long> query = em.createQuery("SELECT count(a) FROM Account a " +
                    "JOIN a.hobbies h WHERE h.name = :hobbyName", Long.class);
            query.setParameter("hobbyName", hobbyName);

            int result = query.getSingleResult().intValue();
            if(result > 0 ){
            FileWriter.storePositive(result + " user found assigned to hobby: "+hobbyName);
            }else {
                FileWriter.storeNegative("No user with given hobby: "+hobbyName);
            }
            return result;
        }catch (NoResultException e) {
            FileWriter.storeNegative("No user with given hobby: "+hobbyName + "error description: "+e.getMessage());
            return 0;
        }
    }
}
