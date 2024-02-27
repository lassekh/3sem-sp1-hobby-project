package dao;

import dto.AccountDTO;
import entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

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

    // US-1: As a user I want to get all the information about a person.

    public AccountDTO getAllInformationFromAGivenPerson(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<AccountDTO> typedQuery = em.createQuery(
                    "SELECT NEW dto.AccountDTO" +
                            "(a.id, a.fullName, ad.dateOfBirth, ad.mobile, ad.updatedAt, ad.zipcode, c.name, ad.address, h.name, h.category) " +
                            " FROM Account a " +
                            " LEFT JOIN AccountDetail ad ON a.id = ad.id" +
                            " LEFT JOIN City c ON ad.zipcode = c.zipcode" +
                            " LEFT JOIN Hobby h" +
                            " WHERE a.id = :first", AccountDTO.class);
            return typedQuery.getSingleResult(); // TODO, make the h.type also work (note for: Ahmad & Hanni)
        }
    }


    // US-2: As a user I want to get all phone numbers from a given person.

    public List<Integer> getAllPhoneNumbersFromGivenPerson(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Query query = em.createQuery("SELECT ad.mobile FROM Account a LEFT JOIN AccountDetail ad on a.id = ad.id WHERE a.fullName = :first");
            query.setParameter("first", name);
            return query.getResultList();
        }
    }

    // US-6: As a user I want to get all persons living in a given city (i.e. 2800 Lyngby).

    public List<Account> getAccountsInCity(int zipcode)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Query query = em.createQuery("SELECT a FROM Account a " +
                    "JOIN AccountDetail ad ON a.id = ad.id " +
                    "JOIN City c ON ad.zipcode = c.zipcode " +
                    "WHERE c.zipcode = :zipcode", Account.class);
            query.setParameter("zipcode", zipcode);
            return query.getResultList();
        }
    }
}
