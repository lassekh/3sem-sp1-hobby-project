package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityDTO;
import entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CityDAO {
    private static EntityManagerFactory emf;
    private static CityDAO instance;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static CityDAO getInstance(EntityManagerFactory _emf)
    {
        if(instance == null)
        {
            emf = _emf;
            instance = new CityDAO();
        }
        return instance;
    }
    public List<City> getAllCities()
    {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<City> query = em.createQuery("select c from City c", City.class);
            return query.getResultList();
        }
    }
    public String getResponseBody(String url) throws IOException {
        //with Apache HttpClient
        //Create a request
        HttpGet request = new HttpGet(url);
        //try with ressources
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);) {

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                    // return it as a String
                    result = EntityUtils.toString(entity);
                    //System.out.println(result);
            }
            return result;
        }
    }



    public CityDTO[] getCityZip(String url) {
        String response = null;
        try {
            response = getResponseBody(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response, CityDTO[].class);
    }

}
