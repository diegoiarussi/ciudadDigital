package Objetos2020;

public class Vertice {
	private double latitud;
	private double longitud;
	private int altura;

	// Este atributo solo se utiliza en los vertices con altura(vertices.geojson), para facilitar la asociacion con los que forman las calles
	private String nombre;
	
	public Vertice (double latitud, double longitud, int altura, String nombre) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.altura = altura;
		this.nombre = nombre;
	}
	
	public Vertice(double latitud, double longitud,int altura) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.altura = altura;
		this.nombre = null;
	}
	
	public Vertice(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.altura = -1;
		this.nombre = null;
	}
	
	public double getLatitud () {
		return this.latitud;
	}
	
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public double getLongitud () {
		return this.longitud;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public int getAltura () {
		return this.altura;
	}
	
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Si dos vertices tienen la misma latitud y longitud, son iguales
	@Override
	public boolean equals(Object v) {
		if ((this.getLatitud() == ((Vertice) v).getLatitud()) && (this.getLongitud() == ((Vertice) v).getLongitud()))
			return true;
		return false;
	}
}
