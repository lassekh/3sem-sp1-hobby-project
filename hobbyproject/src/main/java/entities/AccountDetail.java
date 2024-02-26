package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_details")
public class AccountDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private int mobile;
    private LocalDate dateOfBirth;
    @ManyToOne
    @Transient
    private City city;
    private int zipcode;
    private String address;

    @OneToOne
    @MapsId
    private Account account;

    public AccountDetail(int mobile,LocalDate dateOfBirth, int zipcode, String address)
    {
        this.mobile = mobile;
        this.dateOfBirth = dateOfBirth;
        this.zipcode = zipcode;
        this.address = address;
    }

    @PrePersist
    private void prePersist()
    {
        this.zipcode = city.getZipcode();
    }


}