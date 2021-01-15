package Objetos2020;

import java.text.Normalizer;
import java.util.Vector;

public class Ciudad {
	private String nombre;
	private Vector<Calle> calles;
	private Vector<Lugar> lugares;
	
	public Ciudad (String nombre) {
		this.nombre = nombre;
		this.calles= new Vector<Calle>();
		this.lugares = new Vector<Lugar>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Vector<Calle> getCalles() {
		Vector<Calle> aux = new Vector<Calle>();
		for(int i=0; i<this.calles.size(); i++)
			aux.add(this.calles.elementAt(i));
		return aux;
	}

	public void addCalle(Calle c) {
		if(!this.calles.contains(c))
			this.calles.add(c);
	}

	public Vector<Lugar> getLugares() {
		Vector<Lugar> aux = new Vector<Lugar>();
		for(int i=0; i<this.lugares.size(); i++)
			aux.add(this.lugares.elementAt(i));
		return aux;
	}

	public void addLugar(Lugar l) {
		if(!this.lugares.contains(l))
			this.lugares.add(l);
	}
	
	public Calle getCalle(String nombre) {
		
		// Busca la calle que posea el nombre pasado por parametro
		for(int i=0; i<this.calles.size(); i++) {
			if(this.calles.elementAt(i).getNombre().equals(nombre))
				return this.calles.elementAt(i);
		}
		return null;
	}
	
	public Lugar getLugar(String nombre) {

		// Busca el lugar que posea el nombre pasado por parametro
		for(int i=0; i<this.lugares.size(); i++) {
			if(nombre.equals(this.lugares.elementAt(i).getNombre()))
				return this.lugares.elementAt(i);
		}
		return null;
	}
	
	private String getNombreLimpio(String nombre) {
		String nombreLimpio = "";
		
		// Pasos para eliminar tildes y mayusculas
		nombreLimpio = Normalizer.normalize(nombre, Normalizer.Form.NFD);
		nombreLimpio = nombreLimpio.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		nombreLimpio = nombreLimpio.toLowerCase();
		return nombreLimpio;
	}
	
	public Vector<Calle> getCalles(String nombre) {
		
		// Busca las calles que contengan el nombre pasado por parametro, ignorando tildes y mayusculas
		Vector<Calle> solucion = new Vector<Calle>();
		for(int i=0; i<this.calles.size(); i++) {
			
			// Si el nombre de la calle contiene el nombre pasado por parametro, la agrega a la solucion
			if(getNombreLimpio(this.calles.elementAt(i).getNombre()).contains(nombre))
				solucion.add(this.calles.elementAt(i));
		}
		return solucion;
	}
	
	public Vector<Lugar> getLugares(String nombre) {
		
		// Busca los lugares que contengan el nombre pasado por parametro ignorando tildes y mayusculas
		Vector<Lugar> solucion = new Vector<Lugar>();
		for(int i=0; i<this.lugares.size(); i++) {
			
			// Si el nombre del lugar contiene el nombre pasado por parametro, lo agrega a la solucion
			if(getNombreLimpio(this.lugares.elementAt(i).getNombre()).contains(nombre))
				solucion.add(this.lugares.elementAt(i));
		}
		return solucion;
	}
}
