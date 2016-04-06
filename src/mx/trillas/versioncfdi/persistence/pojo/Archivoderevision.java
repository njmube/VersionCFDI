package mx.trillas.versioncfdi.persistence.pojo;

import java.util.Date;

public class Archivoderevision {
	private Date fechadeverificacion;
	private long tiempovalidez;
	private int numeroderevision;
	private String hash;

	public Archivoderevision() {

	}

	public Archivoderevision(Date fechadeverificacion, long tiempovalidez,
			int numeroderevision) {
		super();
		this.fechadeverificacion = fechadeverificacion;
		this.tiempovalidez = tiempovalidez;
		this.numeroderevision = numeroderevision;
	}

	public Date getFechadeverificacion() {
		return fechadeverificacion;
	}

	public void setFechadeverificacion(Date fechadeverificacion) {
		this.fechadeverificacion = fechadeverificacion;
	}

	public long getTiempovalidez() {
		return tiempovalidez;
	}

	public void setTiempovalidez(long tiempovalidez) {
		this.tiempovalidez = tiempovalidez;
	}

	public int getNumeroderevision() {
		return numeroderevision;
	}

	public void setNumeroderevision(int numeroderevision) {
		this.numeroderevision = numeroderevision;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int compareTo(Date anotherDate) {
		return fechadeverificacion.compareTo(anotherDate);
	}

	public String toString() {
		return fechadeverificacion.toString();
	}
	

}
