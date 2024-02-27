package dao;

import dto.AccountDTO;
import entities.Account;
import entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class AccountDetailDAO extends CRUDDao {

    private static EntityManagerFactory emf;
    private static AccountDetailDAO instance;

    public static AccountDetailDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AccountDetailDAO();
        }
        return instance;
    }

    // US-1: As a user I want to get all the information about a person ( Strategy: Lazy)
    public AccountDTO getAllInformationFromAGivenPersonUsingId(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            // Fetch the Account entity along with its associations eagerly
            Account accountFound = em.createQuery(
                            "SELECT a " +
                                    "FROM Account a " +
                                    "JOIN FETCH a.accountDetail ad " +
                                    "LEFT JOIN FETCH a.hobbies " +
                                    "WHERE a.id = :id", Account.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Extract city information
            City cityFound = em.find(City.class, accountFound.getAccountDetail().getZipcode());

            // Returns the values in a DTO
            return new AccountDTO(accountFound.getId(), accountFound.getFullName(), accountFound.getAccountDetail().getDateOfBirth(),
                    accountFound.getAccountDetail().getMobile(), accountFound.getAccountDetail().getUpdatedAt(), accountFound.getAccountDetail().getZipcode(),
                    cityFound.getName(), accountFound.getAccountDetail().getAddress(), accountFound.getHobbies());
        }
    }

    // US-2: As a user I want to get all phone numbers from a given person.

    public List<Integer> getAllPhoneNumbersFromGivenPerson(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT ad.mobile FROM Account a LEFT JOIN AccountDetail ad on a.id = ad.id WHERE a.fullName = :first");
            query.setParameter("first", name);
            return query.getResultList();
        }
    }

    // US-6: As a user I want to get all persons living in a given city (i.e. 2800 Lyngby).

    public List<Account> getAccountsInCity(int zipcode) {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT a FROM Account a " +
                    "JOIN AccountDetail ad ON a.id = ad.id " +
                    "JOIN City c ON ad.zipcode = c.zipcode " +
                    "WHERE c.zipcode = :zipcode", Account.class);
            query.setParameter("zipcode", zipcode);
            return query.getResultList();
        }
    }
}
