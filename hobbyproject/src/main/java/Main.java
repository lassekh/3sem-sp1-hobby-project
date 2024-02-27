import config.HibernateConfig;
import dao.AccountDAO;
import dao.CityDAO;
import dto.CityDTO;
import entities.Account;
import jakarta.persistence.EntityManagerFactory;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    }
}