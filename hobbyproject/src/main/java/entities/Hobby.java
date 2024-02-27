package entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hobby")
@ToString
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    @Column(name = "wiki_link")
    private String wikiLink;
    private String category;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Hobby(String name, String wikiLink, String category, Type type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public enum Type {
        OUTDOOR,
        INDOOR
    }
}
