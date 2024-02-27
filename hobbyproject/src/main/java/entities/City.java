package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "city")
public class City {
    @Id
    @Column(name = "zipcode", nullable = false)
    private Integer zipcode;
    private String name;

}