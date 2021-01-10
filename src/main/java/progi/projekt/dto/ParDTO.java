package progi.projekt.dto;

import progi.projekt.model.Par;

import java.util.UUID;

public class ParDTO {
	private int parID;
	private UUID oglas1ID;
	private UUID oglas2ID;
	private boolean done;
	private boolean lanac;
	private boolean ignore;

	public ParDTO(Par par) {
		this.parID = par.getIdPar();
		this.oglas1ID = par.getOglas1().getId();
		this.oglas2ID = par.getOglas2().getId();
		this.done = par.getDone();
		this.lanac = par.getLanac();
		this.ignore = par.getIgnore();
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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isLanac() {
		return lanac;
	}

	public void setLanac(boolean lanac) {
		this.lanac = lanac;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
}
