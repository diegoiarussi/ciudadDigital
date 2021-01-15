package Objetos2020;

public class VerticeDistancia {
	
	// Esta clase es creada para poder retornar el vertice con la distancia en un mismo objeto, solicitado por el servicio 3
	private Vertice vertice;
	private int distancia;
	
	public VerticeDistancia(Vertice vertice, int distancia) {
		this.vertice = vertice;
		this.distancia = distancia;
	}
	
	public void setVertice(Vertice vertice) {
		this.vertice = vertice;
	}
	
	public Vertice getVertice() {
		return this.vertice;
	}
	
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	public int getDistancia() {
		return this.distancia;
	}
}
