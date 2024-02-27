package dao;

import config.HibernateConfig;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class AccountDetailDAOTest {

    static EntityManagerFactory emf;

    static AccountDetailDAO accountDetailDAO;

    @BeforeAll
    static void setup() {
        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        accountDetailDAO = AccountDetailDAO.getInstance(emf);
        City city = new City(2800, "Kongens Lyngby");
        accountDetailDAO.create(city);
        Account accountAhmad = new Account("Ahmad");
        AccountDetail accountDetailAhmad = new AccountDetail(123, LocalDate.of(1998, 3, 6), "Boulevard");
        accountAhmad.setAccountDetail(accountDetailAhmad);
        Hobby hobby = new Hobby("Volleyball", "wiki@test.dk", "General", Hobby.Type.INDOOR);
        accountAhmad.addHobby(hobby);
    }

    @AfterAll
    static void close() {
        emf.close();
    }

    @Test
    @DisplayName("Getting all information from a given person using method 1")
    void getAllInformationFromAGivenPersonUsingId1() {
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("Getting all information from a given person using method 2")
    void getAllInformationFromAGivenPersonUsingId2() {
        // Given

        // When

        // Then
    }
}