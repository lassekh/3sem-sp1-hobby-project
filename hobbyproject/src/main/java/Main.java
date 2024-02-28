import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.HibernateConfig;
import dao.AccountDAO;
import dao.AccountDetailDAO;
import dao.CityDAO;
import dao.HobbyDAO;
import dto.CityDTO;
import entities.Account;
import entities.AccountDetail;
import entities.City;
import entities.Hobby;
import filewriter.FileWriter;
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

        emf = HibernateConfig.getEntityManagerFactoryConfig();

//
//        System.out.println(dao.getAccountInfoByPhoneNumber(2));

        AccountDAO aDao = AccountDAO.getInstance(emf);
        AccountDetailDAO aDDao = AccountDetailDAO.getInstance(emf);
        HobbyDAO hDao = HobbyDAO.getInstance(emf);

        System.out.println(hDao.getAllHobbiesAndHowManyAreAssignedToEach());


    }
}
