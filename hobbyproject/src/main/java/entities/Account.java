package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)// a account detail can not exist without account and viceversa thats why persist.all
    private AccountDetail accountDetail;

    @ManyToMany(mappedBy = "accountSet", cascade = CascadeType.PERSIST)
    private Set<Hobby> hobbySet = new HashSet<>();
    public Account(String fullName){
        this.fullName = fullName;
    }

    public void addAccountDetail(AccountDetail accountDetail)
    {
        if(accountDetail != null){
            this.accountDetail = accountDetail;
            accountDetail.setAccount(this);
        }
    }


    public void addHobby(Hobby hobby)
    {
        if(hobby != null){
            this.hobbySet.add(hobby);
            hobby.getAccountSet().add(this);
        }
    }
}