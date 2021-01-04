package progi.projekt.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "kandidat", schema = "public", catalog = "dc917r8jcempur")
public class Kandidat {
	@Id
	@Column(name = "id_kandidat", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idKandidat;

	public UUID getIdKandidat() {
		return idKandidat;
	}

	//TODO: kaj je ovo?
	//@Column(name = "id_oglas", nullable = false)
	//private UUID idOglas;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_oglas")
	private Oglas oglas;

	public Oglas getOglas() {
		return oglas;
	}


	//@Column(name = "id_kand_oglasa", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_kand_oglasa")
	private Oglas kandOglas;

	public Oglas getKandOglas() { return kandOglas;	}


	@Column(name = "bliskost", nullable = true)
	private Integer bliskost;

	public Integer getBliskost() {
		return bliskost;
	}
	public void setBliskost(Integer bliskost) {
		this.bliskost = bliskost;
	}


	@Column(name = "ignore", nullable = true)
	private Boolean ignore;

	public Boolean getIgnore() {
		return ignore;
	}
	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}

	private Integer ocjena;


	public Kandidat(Oglas oglas, Oglas kandOglas, Integer bliskost, Boolean ignore) {
		if (oglas != null || kandOglas != null) {
			this.oglas = oglas;
			this.kandOglas = kandOglas;
			this.bliskost = bliskost;
			this.ignore = ignore;
		} else
			System.err.print("Dani oglasi ne smiju biti null!");
	}

	public Kandidat() {
	}

	public Integer getOcjena() {
		return ocjena;
	}

	public void setOcjena(Integer ocjena) {
		this.ocjena = ocjena;
	}

	public UUID getIdOglas() {
		return new UUID(0,0);
		//return idOglas;	//TODO
	}
/*
	public void setIdOglas(UUID idOglas) {
		this.idOglas = idOglas;
	}*/
}
