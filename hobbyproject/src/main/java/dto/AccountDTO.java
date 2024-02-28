package dto;

import entities.Hobby;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class AccountDTO {
    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private int privateMobile;
    private int workMobile;
    private LocalDate updatedAt;
    private int zipcode;
    private String cityName;
    private String address;
    private Set<Hobby> hobbies;

    public AccountDTO(int id, String fullName, LocalDate dateOfBirth, int mobile, LocalDate updatedAt, int zipcode, String cityName, String address) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.privateMobile = mobile;
        this.updatedAt = updatedAt;
        this.zipcode = zipcode;
        this.cityName = cityName;
        this.address = address;
    }
}
