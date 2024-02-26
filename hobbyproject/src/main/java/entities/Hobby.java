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
@Table(name = "hobby")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    @Column (name = "wiki_link")
    private String wikiLink;
    private String category;
    private String type;

    @ManyToMany
    @JoinTable(
            name = "account_hobby",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<Account> accountSet = new HashSet<>();

}