package dao;

import dto.AccountDTO;
import entities.Account;
import entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

            // Returnerer værdierne i en DTO (Data Transfer Object)
            return new AccountDTO(accountFound.getId(), accountFound.getFullName(), accountFound.getAccountDetail().getDateOfBirth(),
                    accountFound.getAccountDetail().getPrivateMobile(), accountFound.getAccountDetail().getWorkMobile(), accountFound.getAccountDetail().getUpdatedAt(), accountFound.getAccountDetail().getZipcode(),
                    cityFound.getName(), accountFound.getAccountDetail().getAddress(), accountFound.getHobbies());
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

            // Konverter resultatlisten til en strøm og udfør operationer for at mappe og filtrere telefonnumre
            return resultList.stream()
                    .flatMap(Arrays::stream)          // Fladt ud arrays til en strøm af objekter
                    .filter(Objects::nonNull)         // Filtrer ud null-værdier
                    .map(Object::toString)            // Konverter objekter til strenge
                    .map(Integer::parseInt)           // Konverter strenge til heltal
                    .collect(Collectors.toList());    // Indsaml de konverterede heltal til en liste og returnér den
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

            // Udfør forespørgslen og få resultatet som en liste af Account-objekter
            return query.getResultList();
        }
    }

    // EN EKSTRA METODE
    public List<Account> getPersonsInASpecifikCityByName(String cityName) {
        try (EntityManager em = emf.createEntityManager()) {

            // Opret forespørgsel for at vælge konti i en bestemt by baseret på postnummeret
            Query query = em.createQuery("SELECT a FROM Account a " +
                    "JOIN a.accountDetail ad " +
                    "JOIN City c ON ad.zipcode = c.zipcode " +
                    "WHERE c.name = :city_name", Account.class);

            query.setParameter("city_name", cityName);

            // Udfør forespørgslen og få resultatet som en liste af Account-objekter
            return query.getResultList();
        }
    }
}
