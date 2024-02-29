package dao;

import config.HibernateConfig;
import dto.AccountDTOYoussef;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest
{
    private static EntityManagerFactory emf;
    private static AccountDAO accountDAO;

    @BeforeAll
    static void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        accountDAO = AccountDAO.getInstance(emf);

        EntityManager em = emf.createEntityManager();

        // City persisting
        City city = new City(4700, "Næstved");
        accountDAO.create(city);

        Hobby fodbold = new Hobby("Fodbold", "www.wiki-link.dk", "Sport", Hobby.Type.OUTDOOR);
        Account account = new Account("Hanni");
        account.addHobby(fodbold);

        AccountDetail accountDetail = new AccountDetail(53702510, 12345678, LocalDate.of(1998,10,25),"Thyrasvej 48A");
        accountDetail.setCity(city);
        account.addAccountDetail(accountDetail);

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    @AfterAll
    static void close()
    {
        emf.close();
    }

    @Test
    void getAccountInfoByPhoneNumber()
    {
        //Given
        String expectedName = "Hanni";
        LocalDate expectedBirthdate = LocalDate.of(1998,10,25);
        int expectedNumber = 53702510;
        int expectedZipcode = 4700;
        String expectedAddress = "Thyrasvej 48A";
        String expectedHobby = "Fodbold";


        //When
        AccountDTOYoussef accountInfo = accountDAO.getAccountInfoByPhoneNumber(53702510);

        //Then
        assertNotNull(accountInfo);
        assertEquals(expectedName, accountInfo.getFullName());
        assertEquals(expectedBirthdate, accountInfo.getDateOfBirth());
        assertEquals(expectedNumber , accountInfo.getMobile());
        assertEquals(expectedZipcode , accountInfo.getZipcode());
        assertEquals(expectedAddress, accountInfo.getAddress());
        assertTrue(accountInfo.getHobbies().contains(expectedHobby));
    }
}