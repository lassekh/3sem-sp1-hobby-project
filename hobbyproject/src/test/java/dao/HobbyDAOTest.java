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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HobbyDAOTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfigTest();
    private static HobbyDAO hobbyDAO;
    private static AccountDAO accountDao = new AccountDAO();

    private static Account a1;
    private static Account a2;
    private static Account a3;
    private static Account a4;

    private static AccountDetail ad1;
    private static AccountDetail ad2;
    private static AccountDetail ad3;
    private static AccountDetail ad4;

    private static Hobby h1;
    private static Hobby h2;
    private static Hobby h3;
    private static Hobby h4;


    @BeforeAll
    static void beforeAll() {
        hobbyDAO = HobbyDAO.getInstance(emf);

        a1 = new Account("Youssef");
        a2 = new Account("Lasse");
        a3 = new Account("Ahmad");
        a4 = new Account("Hanni");

        ad1 = new AccountDetail(30117195, LocalDate.of(1990,07,12),3400, "Østervang");

        City city = new City(3400, "Hillerød");

        ad1.setCity(city);

        h1 = new Hobby("Fitness", "www.wiki.dk", "Fitness", Hobby.Type.INDOOR);
        h2 = new Hobby("Boxing", "www.wiki.dk", "Fitness", Hobby.Type.INDOOR);
        h3 = new Hobby("Running", "www.wiki.dk", "Fitness", Hobby.Type.OUTDOOR);
        h4 = new Hobby("Fitness", "www.wiki.dk", "Fitness", Hobby.Type.INDOOR);
    }

    @BeforeEach
    void setUp() {

        a1.addHobby(h1);
        a2.addHobby(h2);
        a3.addHobby(h3);
        a4.addHobby(h4);

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

        int actual = hobbyDAO.getNumberOfPeopleGivenHobby("Fitness");
        assertEquals(4, actual);

    }

    @Test
    void getAccountInfoByPhoneNumber() {
        AccountDTOYoussef dto = accountDao.getAccountInfoByPhoneNumber(30117195);
        String actualName = dto.getFullName();
        assertEquals("Youssef", actualName);

        System.out.println(dto);

    }

    @Test
    void getAllAccountsWithHobby() {
        //given
        int expectedSize = 2;
        String expectedName = "Youssef";
        //when
        List<Account> actual = hobbyDAO.getAllAccountsWithHobby("Fitness");
        //then
        assertNotNull(actual);
        assertEquals(expectedSize,actual.size());
        assertTrue(actual.stream().anyMatch(account -> expectedName.equals(account.getFullName())));
    }
}