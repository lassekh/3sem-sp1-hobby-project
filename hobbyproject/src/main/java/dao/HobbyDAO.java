package dao;

import entities.Account;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class HobbyDAO extends CRUDDao {
    private static EntityManagerFactory emf;
    private static HobbyDAO instance;
    public static HobbyDAO getInstance(EntityManagerFactory _emf)
    {
        if(instance == null)
        {
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }
    public List<Account> getAllAccountsWithHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Account> query = em.createQuery("select a from Account a join Hobby h where h.id = ?1", Account.class);
            query.setParameter(1,hobby.getId());
            return query.getResultList();
        }
    }
}
