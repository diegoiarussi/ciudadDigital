package Objetos2020;

import java.util.Hashtable;

public class Lugar {
	private FormaAbs forma;
	private String nombre;
	private Hashtable <String, Object> atributos;
	
	public Lugar(FormaAbs forma, String nombre) {
		this.forma = forma;
		this.nombre = nombre;
		this.atributos = new Hashtable<String,Object>();
	}

	public FormaAbs getForma () {
		return this.forma;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void addAtributo(String clave, Object valor) {
		this.atributos.put(clave, valor);
	}
	
	public Object getAtributo(String clave) {
		if (this.atributos.containsKey(clave))
			return this.atributos.get(clave);
		return null;
	}
	
	// Si dos lugares tienen el mismo nombre y los mismos vertices, son iguales
	@Override
	public boolean equals(Object l) {
		if(this.getNombre().equals(((Lugar) l).getNombre()))
			if(this.getForma().equals(((Lugar) l).getForma()))
				return true;
		return false;
	}
}
