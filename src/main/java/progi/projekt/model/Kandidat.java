package progi.projekt.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Kandidat {
	@Id
	@Column(name = "id_kandidat", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idKandidat;

	//TODO: kaj je ovo?
	//@Column(name = "id_oglas", nullable = false)
	//private UUID idOglas;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_oglas")
	private Oglas oglas;

	//@Column(name = "id_kand_oglasa", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_kand_oglasa")
	private Oglas kandOglas;

	@Column(name = "bliskost", nullable = true)
	private Integer bliskost;

	@Column(name = "ocjena1", nullable = true)
	private Integer ocjena1;

	@Column(name = "ocjena2", nullable = true)
	private Integer ocjena2;

	@Column(name = "ignore", nullable = true)
	private Boolean ignore;

	private Date stvoren;


	public Kandidat(Oglas oglas, Oglas kandOglas, Integer bliskost, Date stvoren, Boolean ignore) {
		if (oglas != null || kandOglas != null) {
			this.oglas = oglas;
			this.kandOglas = kandOglas;
			this.bliskost = bliskost;
			this.stvoren = stvoren;
			this.ignore = ignore;
		} else
			System.err.print("Dani oglasi ne smiju biti null!");
	}

	public Kandidat() {
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kandidat kandidat = (Kandidat) o;
		//todo: popraviti (ovo i dalje dodaje duplikat kandidata)
		return Objects.equals(oglas, kandidat.oglas) &&
				Objects.equals(kandOglas, kandidat.kandOglas) &&
				Objects.equals(oglas, kandidat.kandOglas) &&
				Objects.equals(kandOglas, kandidat.oglas) &&
				Objects.equals(bliskost, kandidat.bliskost) &&
				Objects.equals(stvoren, kandidat.stvoren);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oglas, kandOglas, bliskost, stvoren);
	}

	public UUID getIdKandidat() {
		return idKandidat;
	}

	public void setIdKandidat(UUID idKandidat) {
		this.idKandidat = idKandidat;
	}

	public Oglas getOglas() {
		return oglas;
	}

	public void setOglas(Oglas oglas) {
		this.oglas = oglas;
	}

	public Oglas getKandOglas() {
		return kandOglas;
	}

	public void setKandOglas(Oglas kandOglas) {
		this.kandOglas = kandOglas;
	}

	public Integer getBliskost() {
		return bliskost;
	}

	public void setBliskost(Integer bliskost) {
		this.bliskost = bliskost;
	}

	public Integer getOcjena1() {
		return ocjena1;
	}

	public void setOcjena1(Integer ocjena1) {
		this.ocjena1 = ocjena1;
	}

	public Integer getOcjena2() {
		return ocjena2;
	}

	public void setOcjena2(Integer ocjena2) {
		this.ocjena2 = ocjena2;
	}

	public Boolean getIgnore() {
		return ignore;
	}

	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}

	public Date getStvoren() {
		return stvoren;
	}

	public void setStvoren(Date stvoren) {
		this.stvoren = stvoren;
	}
}
