package dao;

import config.HibernateConfig;
import entities.Account;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRUDDaoTest {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfigTest();
    private static AccountDAO accountDAO;
    private static EntityManager em;

    @BeforeAll
    static void setUp() {
        //em = emf.createEntityManager();
        accountDAO = AccountDAO.getInstance(emf);
        em.persist(new Account("Lasse"));
        em.persist(new Account("Youssef"));
        em.persist(new Account("Ahmad"));
        em.persist(new Account("Hanni"));
    }

    @AfterAll
    static void tearDown() {
        emf.close();
    }

    @Test
    @DisplayName("Creating an hobby entity.")
    void create() {
        // Given
        Hobby creatingHobby = new Hobby("Test", "", "", Hobby.Type.INDOOR);

        // When
        accountDAO.create(creatingHobby);

        // Then
        assertEquals(creatingHobby.getName(), em.find(Hobby.class, 1).getName());
    }

    @Test
    @DisplayName("Reading an account entity.")
    void read() {
        // Given
        String nameExpected = "Youssef";

        // When
        Account actualAccount = accountDAO.read(Account.class, 2);

        // Then
        assertEquals(nameExpected, actualAccount.getFullName());
    }

    @Test
    @DisplayName("Updating existing account's name.")
    void update() {
        // Given
        String newName = "Test";

        // When
        Account actualAccount = em.find(Account.class, 1);
        actualAccount.setFullName("Test");
        Account updatedAccount = accountDAO.update(actualAccount);

        // Then
        assertEquals(newName, updatedAccount.getFullName());
    }

    @Test
    @DisplayName("Deleting existing account.")
    void delete() {
        // Given
        int deletedAccounts = 1;

        // When
        accountDAO.delete(Account.class,1);

        // Then
        assertEquals(deletedAccounts,1);
    }
}