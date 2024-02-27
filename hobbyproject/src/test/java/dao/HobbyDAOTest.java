package dao;

import config.HibernateConfig;
import dto.AccountDTOYoussef;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HobbyDAOTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfigTest();
    private static HobbyDAO dao = new HobbyDAO();
    private static AccountDAO accountDao = new AccountDAO();

    private static Account a1;
    private static Account a2;
    private static Account a3;
    private static Account a4;

    private static AccountDetail ad1;
    private static AccountDetail ad2;
    private static AccountDetail ad3;
    private static AccountDetail ad4;

    private static Hobby h;


    @BeforeAll
    static void beforeAll() {
        a1 = new Account("Youssef");
        a2 = new Account("Lasse");
        a3 = new Account("Ahmad");
        a4 = new Account("Hanni");

        ad1 = new AccountDetail(30117195, LocalDate.of(1990,07,12), "Østervang");

        City city = new City(3400, "Hillerød");

        ad1.setCity(city);

        h = new Hobby("Fitness", "www.wiki.dk", "Fitness", Hobby.Type.INDOOR);
    }

    @BeforeEach
    void setUp() {

        a1.addHobby(h);
        a2.addHobby(h);
        a3.addHobby(h);
        a4.addHobby(h);

        a1.addAccountDetail(ad1);

        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(a4);
            em.getTransaction().commit();
        }

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNumberOfPeopleGivenHobby() {

        int actual = dao.getNumberOfPeopleGivenHobby("Fitness");
        assertEquals(4, actual);

    }

    @Test
    void getAccountInfoByPhoneNumber() {
        AccountDTOYoussef dto = accountDao.getAccountInfoByPhoneNumber(30117195);
        String actualName = dto.getFullName();
        assertEquals("Youssef", actualName);

        System.out.println(dto);

    }

}