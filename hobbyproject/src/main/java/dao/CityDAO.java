package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.CityDTO;
import entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
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
    public String getResponseBody(String url)
    {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                //.get()
                //.addHeader("accept","application/json")
                //.addHeader("token","Bearer e7724d456c5758a7efe5928853f64ba4")
                .method("GET",null)
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            System.out.println(response);
            return response.body().string();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public CityDTO getCityZip(String url)
    {
        //String url = "https://api.dataforsyningen.dk/postnumre?token=e7724d456c5758a7efe5928853f64ba4";
        String response = getResponseBody(url);
        //JsonArray jsonArray = gson.fromJson(response, JsonArray.class);
        return gson.fromJson(response, CityDTO.class);
    }

}
