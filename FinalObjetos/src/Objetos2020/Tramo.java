package Objetos2020;

import java.util.Hashtable;

public class Tramo{
	private FormaAbs forma;
	private Hashtable <String,Object> atributos;
	
	public Tramo(FormaAbs forma) {
		this.forma = forma;
		this.atributos = new Hashtable <String,Object> ();
	}
	
	public FormaAbs getForma() {
		return this.forma;
	}
	
	public void addAtributo(String clave, Object valor) {
		this.atributos.put(clave, valor);
	}
	
	public Object getAtributo(String clave) {
		if (this.atributos.containsKey(clave))
			return this.atributos.get(clave);
		return null;
	}
}
