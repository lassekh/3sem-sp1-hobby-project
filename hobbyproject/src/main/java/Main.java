import config.HibernateConfig;
import dao.AccountDetailDAO;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {

    static AccountDetailDAO accountDetailDAO;
    static EntityManagerFactory emf;

    public static void main(String[] args) {

        // NIKS PILLE
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        accountDetailDAO = AccountDetailDAO.getInstance(emf);

        EntityManager em = emf.createEntityManager();

        // Creating/Persisting the city
        City city = new City(2800, "Kongens Lyngby");
        accountDetailDAO.create(city);

        // Creating/Persisting the hobby
        Hobby hobby1 = new Hobby("Volleyball", "wiki@test.dk", "General", Hobby.Type.INDOOR);
        Hobby hobby2 = new Hobby("Fodbold", "wiki@test.dk", "General", Hobby.Type.OUTDOOR);

        //em.persist(hobby1);
        //em.persist(hobby2);

        Account accountAhmad = new Account("Ahmad A");
        accountAhmad.addHobby(hobby1);
        accountAhmad.addHobby(hobby2);

        AccountDetail accountDetailAhmad = new AccountDetail(123, LocalDate.of(1998, 3, 6), "Boulevard");
        accountDetailAhmad.setCity(city);
        accountAhmad.addAccountDetail(accountDetailAhmad);

        em.getTransaction().begin();
        em.persist(accountAhmad);
        em.getTransaction().commit();

        Account foundAccount = em.find(Account.class, 1);
        System.out.println(foundAccount.getFullName());
        System.out.println(foundAccount.getHobbies());

        // NIKS PILLE
    }
}
