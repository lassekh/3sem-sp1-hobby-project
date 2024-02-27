package dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


// TODO skal kaldes noget andet... - Youssef
@Getter
public class AccountDTOYoussef {
    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private int mobile;
    private int zipcode;
    private String cityName;
    private String address;
    private List<String> hobbies;

    public AccountDTOYoussef(int id, String fullName, LocalDate dateOfBirth, int mobile, int zipcode, String cityName, String address, List<String> hobbies) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.mobile = mobile;
        this.zipcode = zipcode;
        this.cityName = cityName;
        this.address = address;
        this.hobbies = hobbies;
    }


    @Override
    public String toString() {
        return "AccountDTOYoussef{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", mobile=" + mobile +
                ", zipcode=" + zipcode +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", hobbies=" + hobbies +
                '}';
    }
}
