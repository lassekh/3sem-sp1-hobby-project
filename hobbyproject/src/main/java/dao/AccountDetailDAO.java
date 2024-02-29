package dao;

import dto.AccountDTO;
import entities.Account;
import entities.City;
import filewriter.FileWriter;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

public class AccountDetailDAO extends CRUDDao {

    private static EntityManagerFactory emf;
    private static AccountDetailDAO instance;

    public static AccountDetailDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AccountDetailDAO();
        }
        return instance;
    }

    // US-1: As a user I want to get all the information about a person ( Strategy: Lazy)
    public AccountDTO getAllInformationFromAGivenPersonUsingId(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            // Hent Account entiteten sammen med dens tilknyttede objekter (eager loading)
            Account accountFound = em.createQuery(
                            "SELECT a " +
                                    "FROM Account a " +
                                    "JOIN FETCH a.accountDetail ad " +  // Brug FETCH for at hente AccountDetail sammen med Account
                                    "LEFT JOIN FETCH a.hobbies " +     // Brug FETCH for at hente Hobbies sammen med Account (left join, hvis der er nogle)
                                    "WHERE a.id = :id", Account.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Udtræk byinformation
            City cityFound = em.find(City.class, accountFound.getAccountDetail().getZipcode());


            FileWriter.storePositive("Found user - id: " + id + ", name: "+accountFound.getFullName());

            // Returnerer værdierne i en DTO (Data Transfer Object)

            return AccountDTO.builder()
                    .id(accountFound.getId())
                    .fullName(accountFound.getFullName())
                    .dateOfBirth(accountFound.getAccountDetail().getDateOfBirth())
                    .privateMobile(accountFound.getAccountDetail().getPrivateMobile())
                    .workMobile(accountFound.getAccountDetail().getWorkMobile())
                    .updatedAt(accountFound.getAccountDetail().getUpdatedAt())
                    .zipcode(accountFound.getAccountDetail().getZipcode())
                    .cityName(cityFound.getName())
                    .address(accountFound.getAccountDetail().getAddress())
                    .hobbies(accountFound.getHobbies())
                    .build();
        } catch (NoResultException e) {
            FileWriter.storeNegative("User with id: " + id + " was not found");
            return null;
        }
    }

    // US-2: As a user I want to get all phone numbers from a given person.
    public List<Integer> getAllPhoneNumbersFromGivenPersonByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            // Opret forespørgsel for at hente privateMobile og workMobile fra AccountDetail
            Query query = em.createQuery("SELECT ad.privateMobile, ad.workMobile FROM Account a " +
                    "LEFT JOIN AccountDetail ad on a.id = ad.id " +  // Foretag en venstre join for at inkludere AccountDetail
                    "WHERE a.fullName = :first");                   // Vælg konti baseret på fulde navn

            query.setParameter("first", name);  // Indsæt parameteren for fulde navn i forespørgslen

            // Udfør forespørgslen og få resultatet som en liste af Object-arrays
            List<Object[]> resultList = query.getResultList();

            if (resultList.isEmpty()) {
                FileWriter.storeNegative("No phone numbers found for person: " + name);
                return List.of(); // Returner en tom liste for at undgå null
            }

            FileWriter.storePositive("Phone numbers retrieved for person: " + name);

            // Konverter resultatlisten til en strøm og udfør operationer for at mappe og filtrere telefonnumre
            return resultList.stream()
                    .flatMap(Arrays::stream)          // Fladt ud arrays til en strøm af objekter
                    .filter(Objects::nonNull)         // Filtrer ud null-værdier
                    .map(Object::toString)            // Konverter objekter til strenge
                    .map(Integer::parseInt)           // Konverter strenge til heltal
                    .collect(Collectors.toList());    // Indsaml de konverterede heltal til en liste og returnér den
        }catch (Exception e) {
            FileWriter.storeNegative("Error retrieving phone numbers for person: " + name + "system error description: " + e.getMessage());
            throw e; // Kast en ny undtagelse for at håndtere fejlen yderligere oppe i kaldstakken
        }
    }


    // US-6: As a user I want to get all persons living in a given city (i.e. 2800 Lyngby).
    public List<Account> getPersonsInASpecifikCityByZipcode(int zipcode) {
        try (EntityManager em = emf.createEntityManager()) {

            // Opret forespørgsel for at vælge konti i en bestemt by baseret på postnummeret
            Query query = em.createQuery("SELECT a FROM Account a " +
                    "JOIN a.accountDetail ad " +
                    "JOIN City c ON ad.zipcode = c.zipcode " +
                    "WHERE c.zipcode = :zipcode", Account.class);

            query.setParameter("zipcode", zipcode);

            List<Account> accounts = query.getResultList();

            if(accounts.isEmpty()){
                FileWriter.storeNegative("No persons available living i zipcode: " + zipcode);
                return List.of(); //returns an empty list - avoiding null
            }
            FileWriter.storePositive("Found the following persons: " + accounts);
            // Udfør forespørgslen og få resultatet som en liste af Account-objekter
            return accounts;
        }
    }

    // US-11: As a user I want to get all persons living in a given city by name.
    public List<Account> getPersonsInASpecifikCityByName(String cityName) {
        try (EntityManager em = emf.createEntityManager()) {

            // Opret forespørgsel for at vælge konti i en bestemt by baseret på postnummeret
            Query query = em.createQuery("SELECT a FROM Account a " +
                    "JOIN a.accountDetail ad " +
                    "JOIN City c ON ad.zipcode = c.zipcode " +
                    "WHERE c.name = :city_name", Account.class);

            query.setParameter("city_name", cityName);

            List<Account> accounts = query.getResultList();

            if(accounts.isEmpty()){
                FileWriter.storeNegative("No persons available living i city: " + cityName);
                return List.of(); //returns an empty list - avoiding null
            }
            FileWriter.storePositive("Found the following persons: " + accounts + " living in the city: " + cityName);
            // Udfør forespørgslen og få resultatet som en liste af Account-objekter
            return accounts;
        }
    }

    // US-10: As a user I want to see all people on an address with a count on how many hobbies each person has (Use Java Streams for this one)
    public List<Account> getPersonsByAddress(String address)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.accountDetail.address = :address", Account.class);
            query.setParameter("address", address);

            List<Account> accounts = query.getResultList();

            if(accounts.isEmpty()){
                FileWriter.storeNegative("No persons available living on address: " + address);
                return List.of(); //returns an empty list - avoiding null
            }
            FileWriter.storePositive("Found the following persons: " + accounts);
            // Udfør forespørgslen og få resultatet som en liste af Account-objekter
            return accounts;

        }
    }

    public Map<String, Integer> getCountOfHobbiesByAddress(List<Account> accounts)
    {
        Map<String, Integer> hobbycounts = accounts.stream().collect(Collectors.toMap(Account::getFullName, account -> account.getHobbies().size()));
        return hobbycounts;
    }
}
