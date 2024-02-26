package dao;

import config.HibernateConfig;
import entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CRUDDaoTest {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfigTest();
    private AccountDAO accountDAO = new AccountDAO();

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        em.persist(new Account("Lasse"));
        em.persist(new Account("Youssef"));
        em.persist(new Account("Ahmad"));
        em.persist(new Account("Hanni"));
    }

    @AfterEach
    void tearDown() {
        emf.close();
    }

    @Test
    void create() {
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}