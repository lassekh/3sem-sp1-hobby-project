package dao;

import dto.AccAccDetHobbyDTO;
import dto.AccountDTOYoussef;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountDAO extends CRUDDao{

    //[US-8] As a user I want to get all the information about
    //a person (address, hobbies etc.) given a phone number

    public AccountDTOYoussef getAccountInfoByPhoneNumber(int phoneNumber) {

        try (var em = emf.createEntityManager()) {

            //Da jeg ikke har en klasse som rummer al data jeg skal bruge laver jeg en DTO som skal indeholde Account AccountDetails og Hobby
            TypedQuery<AccAccDetHobbyDTO> query = em.createQuery(
                    "SELECT new dto.AccAccDetHobbyDTO(a, ad, h) FROM Account a " +
                            "JOIN a.accountDetail ad " +
                            "LEFT JOIN a.hobbySet h " + //Left JOIN for at få ALLE account entiteterne retur. selvom en account ikke har en hobby tilknyttet
                            "WHERE ad.mobile = :phoneNumber", AccAccDetHobbyDTO.class);
            query.setParameter("phoneNumber", phoneNumber);

            //Benytter resultlist da en person kan have flere hobbier. For hver hobby vil der være en account
            List<AccAccDetHobbyDTO> dtos = query.getResultList();
            if (dtos.isEmpty()) return null;

            //Da en person kan have flere hobbier gennemgår vi listen og sætter unikke hobbier i et Set.
            Set<String> hobbies = new HashSet<>();
            for (AccAccDetHobbyDTO dto : dtos) {
                if (dto.getHobby() != null) { // Tjek for at undgå null værdier fra LEFT JOIN
                    hobbies.add(dto.getHobby().getName());
                }
            }
                //instantierer en ny AccountDTOYoussef via data fra
            return new AccountDTOYoussef(
                    dtos.get(0).getAccount().getId(),
                    dtos.get(0).getAccount().getFullName(),
                    dtos.get(0).getAccountDetail().getDateOfBirth(),
                    dtos.get(0).getAccountDetail().getMobile(),
                    dtos.get(0).getAccountDetail().getZipcode(),
                    dtos.get(0).getAccountDetail().getCity() != null ? dtos.get(0).getAccountDetail().getCity().getName() : null,
                    dtos.get(0).getAccountDetail().getAddress(),
                    //da vores DTO tager en liste, konverteres settet til en liste.
                    new ArrayList<>(hobbies)
            );
        }
    }
}
