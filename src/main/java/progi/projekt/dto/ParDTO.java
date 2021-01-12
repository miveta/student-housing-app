package progi.projekt.dto;

import progi.projekt.model.Par;
import progi.projekt.model.ZaposlenikSC;

import java.util.UUID;

public class ParDTO {
	private int parID;
	private UUID oglas1ID;
	private UUID oglas2ID;
	private Boolean done;
	private Boolean lanac;
	private Boolean ignore;
	private Boolean odobren;
	private ZaposlenikSC zaposlenikSC;

	public ParDTO(Par par) {
		this.parID = par.getIdPar();
		this.oglas1ID = par.getOglas1().getId();
		this.oglas2ID = par.getOglas2().getId();
		this.done = par.getDone();
		this.lanac = par.getLanac();
		this.ignore = par.getIgnore();
		this.odobren = par.getOdobren();
		this.zaposlenikSC = par.getZaposlenikSC();
	}

	public int getParID() {
		return parID;
	}

	public void setParID(int parID) {
		this.parID = parID;
	}

	public UUID getOglas1ID() {
		return oglas1ID;
	}

	public void setOglas1ID(UUID oglas1ID) {
		this.oglas1ID = oglas1ID;
	}

	public UUID getOglas2ID() {
		return oglas2ID;
	}

	public void setOglas2ID(UUID oglas2ID) {
		this.oglas2ID = oglas2ID;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Boolean getLanac() {
		return lanac;
	}

	public void setLanac(Boolean lanac) {
		this.lanac = lanac;
	}

	public Boolean getIgnore() {
		return ignore;
	}

	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}

	public Boolean getOdobren() {
		return odobren;
	}

	public void setOdobren(Boolean odobren) {
		this.odobren = odobren;
	}

	public ZaposlenikSC getZaposlenikSC() {
		return zaposlenikSC;
	}

	public void setZaposlenikSC(ZaposlenikSC zaposlenikSC) {
		this.zaposlenikSC = zaposlenikSC;
	}
}
