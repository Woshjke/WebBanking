package bank.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "organisations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Organisations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long user_id;
}
