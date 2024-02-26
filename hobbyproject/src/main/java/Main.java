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

        Account account = new Account("Lasse");
        AccountDAO dao = new AccountDAO();
        //dao.create(account);

        CityDAO cityDAO = CityDAO.getInstance(emf);
        CityDTO[] cityDTO = cityDAO.getCityZip("https://api.dataforsyningen.dk/postnumre");
        Arrays.stream(cityDTO).forEach(System.out::println);

    }
}