import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.HibernateConfig;
import dao.AccountDetailDAO;
import dao.CityDAO;
import dto.CityDTO;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static AccountDetailDAO accountDetailDAO;
    static EntityManagerFactory emf;

    public static void main(String[] args) throws IOException {
        CityDAO cityDAO = new CityDAO();
        Arrays.stream(cityDAO.getCityZip("https://api.dataforsyningen.dk/postnumre"))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        /*
        // NIKS PILLE
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        accountDetailDAO = AccountDetailDAO.getInstance(emf);

        EntityManager em = emf.createEntityManager();

        // Creating/Persisting the city
        City city = new City(2800, "Kongens Lyngby");
        accountDetailDAO.create(city);

        // Creating/Persisting the hobby
        Hobby hobby1 = new Hobby("Volleyball", "wiki@test.dk", "General", Hobby.Type.INDOOR);
        Hobby hobby2 = new Hobby("Fodbold", "wiki@test.dk", "General", Hobby.Type.OUTDOOR);

        //em.persist(hobby1);
        //em.persist(hobby2);

        Account accountAhmad = new Account("Ahmad A");
        accountAhmad.addHobby(hobby1);
        accountAhmad.addHobby(hobby2);

        AccountDetail accountDetailAhmad = new AccountDetail(123, LocalDate.of(1998, 3, 6), "Boulevard");
        accountDetailAhmad.setCity(city);
        accountAhmad.addAccountDetail(accountDetailAhmad);

        em.getTransaction().begin();
        em.persist(accountAhmad);
        em.getTransaction().commit();

        Account foundAccount = em.find(Account.class, 1);
        System.out.println(foundAccount.getFullName());
        System.out.println(foundAccount.getHobbies());

        // NIKS PILLE

         */
    }
}
