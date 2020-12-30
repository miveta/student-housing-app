package progi.projekt.model;

import javax.persistence.*;

@Entity
@Table(name = "par", schema = "public", catalog = "dc917r8jcempur")
public class Par {
	@Id
	@Column(name = "id_par", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPar;

	public int getIdPar() {	return idPar; }


	@Column(name = "id_oglas1", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_oglas1")
	private Oglas oglas1;

	public Oglas getOglas1() {
		return oglas1;
	}


	@Column(name = "id_oglas2", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_oglas2")
	private Oglas oglas2;

	public Oglas getOglas2() {
		return oglas2;
	}


	@Basic
	@Column(name = "done", nullable = true)
	private Boolean done;

	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}

	@Basic
	@Column(name = "lanac", nullable = true)
	private Boolean lanac;

	public Boolean getLanac() {
		return lanac;
	}
	public void setLanac(Boolean done) {
		this.lanac = lanac;
	}


	@Basic
	@Column(name = "ignore", nullable = true)
	private Boolean ignore;

	public Boolean getIgnore() {
		return ignore;
	}
	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}


	public Par(Oglas oglas1, Oglas oglas2, Boolean done) {
		if (oglas1 != null || oglas2 != null) {
			this.oglas1 = oglas1;
			this.oglas2 = oglas2;
			this.done = done;
		} else
			System.err.print("Dani oglasi ne smiju biti null!");
	}

	public Par() {
	}
}
