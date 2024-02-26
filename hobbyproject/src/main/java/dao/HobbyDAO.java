package dao;

import entities.Hobby;
import jakarta.persistence.TypedQuery;

public class HobbyDAO extends CRUDDao{

    //[US-4] As a user I want to get the number of people with a given hobby

    public int getNumberOfPeopleGivenHobby(String hobbyName){
        try(var em = emf.createEntityManager()){
            TypedQuery<Long> query = em.createQuery("SELECT count(a) FROM Account a JOIN a.hobbySet h WHERE h.name = :hobbyName", Long.class);
            query.setParameter("hobbyName", hobbyName);
            return query.getSingleResult().intValue();
        }
    }
}