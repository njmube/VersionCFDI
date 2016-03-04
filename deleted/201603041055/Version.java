package mx.trillas.version.pojo;

import java.util.Date;

public class Version {
	private int numeroRevision;
	private Date tiempoValidez;
	private Date fechaVerificacion;
	
	public Version(){
		
	}
	
	public Version(int numeroRevsion, Date tiempoValidez, Date fechaVerificacion) {
		super();
		this.numeroRevision = numeroRevsion;
		this.tiempoValidez = tiempoValidez;
		this.fechaVerificacion = fechaVerificacion;
	}

	public int getNumeroRevision() {
		return numeroRevision;
	}

	public void setNumeroRevision(int numeroRevsion) {
		this.numeroRevision = numeroRevsion;
	}

	public Date getTiempoValidez() {
		return tiempoValidez;
	}

	public void setTiempoValidez(Date tiempoValidez) {
		this.tiempoValidez = tiempoValidez;
	}

	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Version)) return false;
		Version version = (Version)obj;
		if (this.getNumeroRevision() == version.getNumeroRevision() && this.getTiempoValidez() == version.getTiempoValidez()&& this.getFechaVerificacion() == version.getFechaVerificacion()) return true;
		return false;
	}
	
	public String toString(){
		return this.getNumeroRevision() + "  " + this.getTiempoValidez() + "  " + this.getFechaVerificacion();
	}
}
