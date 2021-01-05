package progi.projekt.model;

import lombok.Data;
import progi.projekt.model.enums.OznakeKategorijaEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Paviljon implements Serializable {
    @Id
    @Column(name = "id_paviljon")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String naziv;

    private int brojKatova;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dom")
    private Dom dom;

    @OneToMany(mappedBy = "paviljon", cascade = CascadeType.ALL)
    private Set<Soba> sobe;

    @Column(name = "kategorija")
    @Enumerated(EnumType.STRING)
    private OznakeKategorijaEnum kategorija;

}
