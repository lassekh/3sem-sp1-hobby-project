package dto;

import entities.Hobby;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AccountDTO {
    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private int mobile;
    private LocalDate updatedAt;
    private int zipcode;
    private String cityName;
    private String address;
    private Set<Hobby> hobbies;
    private String hobbyName;
    private String category;
    private String type;

    public AccountDTO(int id, String fullName, LocalDate dateOfBirth, int mobile, LocalDate updatedAt, int zipcode, String cityName, String address, String hobbyName, String category) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.mobile = mobile;
        this.updatedAt = updatedAt;
        this.zipcode = zipcode;
        this.cityName = cityName;
        this.address = address;
        this.hobbyName = hobbyName;
        this.category = category;
    }
}
