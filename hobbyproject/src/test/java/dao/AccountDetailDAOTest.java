package dao;

import config.HibernateConfig;
import dto.AccountDTO;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountDetailDAOTest {

    static EntityManagerFactory emf;

    static AccountDetailDAO accountDetailDAO;

    @BeforeAll
    static void setup() {

        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        accountDetailDAO = AccountDetailDAO.getInstance(emf);

        EntityManager em = emf.createEntityManager();

        // Creating/Persisting the city
        City city = new City(2800, "Kongens Lyngby");
        accountDetailDAO.create(city);

        // Creating/Persisting the hobby
        Hobby hobby1 = new Hobby("Volleyball", "wiki@test.dk", "General", Hobby.Type.INDOOR);
        Hobby hobby2 = new Hobby("Fodbold", "wiki@test.dk", "General", Hobby.Type.OUTDOOR);

        Account accountAhmad = new Account("Ahmad A");
        accountAhmad.addHobby(hobby1);
        accountAhmad.addHobby(hobby2);

        AccountDetail accountDetailAhmad = new AccountDetail(123, 456, LocalDate.of(1998, 3, 6), "Boulevard");
        accountDetailAhmad.setCity(city);
        accountAhmad.addAccountDetail(accountDetailAhmad);

        em.getTransaction().begin();
        em.persist(accountAhmad);
        em.getTransaction().commit();

        Account foundAccount = em.find(Account.class, 1);
        System.out.println(foundAccount.getFullName());
        System.out.println(foundAccount.getHobbies());
    }

    @AfterAll
    static void close() {
        emf.close();
    }

    @Test
    @DisplayName("Getting all information from a given person using id.")
    void getAllInformationFromAGivenPersonUsingId() {
        // Given
        String expectedFullName = "Ahmad A";
        int expectedHobbySize = 2;

        // When
        AccountDTO accountDTO = accountDetailDAO.getAllInformationFromAGivenPersonUsingId(1);

        // Then
        assertNotNull(accountDTO);
        assertEquals(expectedFullName, accountDTO.getFullName());
        assertEquals(expectedHobbySize, accountDTO.getHobbies().size());
    }

    @Test
    @DisplayName("Getting all phone numbers from a given person.")
    void getAllPhoneNumbersFromGivenPerson() {
        // Given
        int expectedNumbersSize = 2;

        // When
        String setName = "Ahmad A";
        List<Integer> actualNumbers = accountDetailDAO.getAllPhoneNumbersFromGivenPersonByName(setName);

        // Then
        assertNotNull(actualNumbers);
        assertEquals(expectedNumbersSize, actualNumbers.size());
    }

    @Test
    @DisplayName("Getting all persons in a specific city by zipcode.")
    void getPersonsInACityByZipcode() {
        // Given
        int expectedPersonsSize = 1;
        int expectedPrivateNumber = 123;
        int expectedWorkNumber = 456;
        int expectedZipcode = 2800;

        // When
        int setZipcode = 2800;
        List<Account> actualAccountList = accountDetailDAO.getPersonsInASpecifikCityByZipcode(setZipcode);

        // Then
        assertNotNull(actualAccountList);
        assertEquals(expectedPersonsSize, actualAccountList.size());
        assertEquals(expectedZipcode, actualAccountList.get(0).getAccountDetail().getZipcode());
        assertEquals(expectedPrivateNumber, actualAccountList.get(0).getAccountDetail().getPrivateMobile());
        assertEquals(expectedWorkNumber, actualAccountList.get(0).getAccountDetail().getWorkMobile());
    }

    @Test
    @DisplayName("Getting all persons in a specific city by name.")
    void getPersonsInASpecifikCityByName() {
        // Given
        int expectedPersonsSize = 1;
        int expectedZipCode = 2800; // Since we can't get hold of the city name, we'll use the corresponding zipcode

        // When
        String setCityName = "Kongens Lyngby";
        List<Account> actualAccountList = accountDetailDAO.getPersonsInASpecifikCityByName(setCityName);


        // Then
        assertNotNull(actualAccountList);
        assertEquals(expectedPersonsSize, actualAccountList.size());
        assertEquals(expectedZipCode,actualAccountList.get(0).getAccountDetail().getZipcode());
    }
}