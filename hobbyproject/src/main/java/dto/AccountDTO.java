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
}
