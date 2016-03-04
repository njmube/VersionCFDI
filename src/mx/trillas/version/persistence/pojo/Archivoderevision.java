package mx.trillas.version.persistence.pojo;

import java.util.Date;

public class Archivoderevision {
	private Date fechadeverificacion;
	private long tiempovalidez;
	private int numeroderevision;

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

	@Override
	public String toString() {
		return "Archivoderevision [fechadeverificacion=" + fechadeverificacion
				+ ", tiempovalidez=" + tiempovalidez + ", numeroderevision="
				+ numeroderevision + ", getFechadeverificacion()="
				+ getFechadeverificacion() + ", getTiempovalidez()="
				+ getTiempovalidez() + ", getNumeroderevision()="
				+ getNumeroderevision() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((fechadeverificacion == null) ? 0 : fechadeverificacion
						.hashCode());
		result = prime * result + numeroderevision;
		result = prime * result
				+ (int) (tiempovalidez ^ (tiempovalidez >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Archivoderevision other = (Archivoderevision) obj;
		if (fechadeverificacion == null) {
			if (other.fechadeverificacion != null)
				return false;
		} else if (!fechadeverificacion.equals(other.fechadeverificacion))
			return false;
		if (numeroderevision != other.numeroderevision)
			return false;
		if (tiempovalidez != other.tiempovalidez)
			return false;
		return true;
	}

}
