package dao;

import config.HibernateConfig;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityDAOTest {
    private static EntityManagerFactory emf;
    private static CityDAO cityDAO;

    @BeforeAll
    static void setUpAll()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        cityDAO = CityDAO.getInstance(emf);

        Account[] accounts = {new Account("Lasse"),new Account("Ahmad"),new Account("Hanni"),new Account("Youssef")};
        AccountDetail[] details = {
                new AccountDetail(30117195, LocalDate.of(1990,7,12),3400, "Østervang"),
                new AccountDetail(40404040, LocalDate.of(1992,5,10),2800, "Firskovvej"),
                new AccountDetail(50505050, LocalDate.of(1993,2,11),2800, "Lyngbyvej"),
                new AccountDetail(30333330, LocalDate.of(1998,1,1),2800, "Hovedvejen")
        };
        for(int i = 0; i < accounts.length; i++)
        {
            accounts[i].addAccountDetail(details[i]);
        }
        City[] cities = {
                new City(2800,"Lyngby"),
                new City(3400,"Hillerød"),
                new City(3120,"Dronningmølle"),
                new City(3250,"Gilleleje")
        };
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (City city : cities) {
                em.persist(city);
            }
            em.getTransaction().commit();
        }
    }
    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
        emf.close();
    }

    @Test
    @DisplayName("Testing that a given city name is in the returned list")
    void getAllCities() {
        //given
        String expectedCity = "Lyngby";
        //when
        List<City> cities = cityDAO.getAllCities();
        //then
        assertNotNull(cities);
        assertTrue(cities.stream().anyMatch(city -> expectedCity.equals(city.getName())));
    }
}