package entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
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
    private LocalDate updatedAt;

    public AccountDetail(int mobile,LocalDate dateOfBirth, String address)
    {
        this.mobile = mobile;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @PrePersist
    private void prePersist()
    {
        this.zipcode = city.getZipcode();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    private void preUpdate()
    {
        this.updatedAt = LocalDate.now();
    }


}