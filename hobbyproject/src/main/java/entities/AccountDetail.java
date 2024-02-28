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
public class AccountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private int privateMobile;
    private int workMobile;
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

    public AccountDetail(int privateMobile, LocalDate dateOfBirth, String address) {
        this.privateMobile = privateMobile;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public AccountDetail(int privateMobile, int workMobile, LocalDate dateOfBirth, String address) {
        this.privateMobile = privateMobile;
        this.workMobile = workMobile;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @PrePersist
    private void prePersist() {
        this.zipcode = city.getZipcode();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDate.now();
    }


}