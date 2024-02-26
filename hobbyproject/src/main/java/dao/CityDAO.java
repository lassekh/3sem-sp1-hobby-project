package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.CityDTO;
import jakarta.persistence.EntityManagerFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

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
    public String getResponseBody(String url)
    {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET",null)
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public CityDTO[] getCityZip(String url)
    {
        String response = getResponseBody(url);
        //JsonArray jsonArray = gson.fromJson(response, JsonArray.class);
        return gson.fromJson(response, CityDTO[].class);
    }

}
