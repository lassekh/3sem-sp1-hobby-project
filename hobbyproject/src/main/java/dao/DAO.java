package dao;

import jakarta.persistence.EntityManagerFactory;

public class DAO {

    private static EntityManagerFactory emf;
    private static DAO instance;
    //Singleton pattern. ensure only onw instance is running in the program
    public static DAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new DAO();
        }
        return instance;
    }
}
