package Objetos2020;

import java.util.Vector;

public class Calle {
	private String nombre;
	private Vector<Tramo> tramos;
	
	public Calle (String nombre) {
		this.nombre = nombre;
		tramos = new Vector<Tramo> ();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void addTramo(Tramo t) {
		this.tramos.add(t);
	}
	
	public Vector<Tramo> getTramos() {
		Vector<Tramo> aux = new Vector<Tramo>();
		for(int i=0; i<this.tramos.size(); i++)
			aux.add(this.tramos.elementAt(i));
		return aux;
	}
	
	private boolean contieneVertice(Vector<Vertice> solucion, Vertice v) {
		
		//Este metodo es utilizado para que no se devuelvan vertices repetidos en el servicio 2
		for(int i=0; i<solucion.size(); i++)
			if(solucion.elementAt(i).equals(v))
				return true;
		return false;
	}
	
	public Vector<Vertice> getUbicacionesIntersecciones(Calle c) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		
		// Obtenemos el vector de vertices de la calle pasada por parametro
		Vector<Tramo> tramos2 = c.getTramos();
		Vector<Vertice> vertices2 = new Vector<Vertice>();	
		for(int i=0; i<tramos2.size(); i++) {
			vertices2.addAll(tramos2.elementAt(i).getForma().getVertices());
		}
		
		// Recorremos los tramos de la calle actual llamando al metodo de su forma que obtiene las intersecciones con la calle pasada por parametro
		for(int i=0; i<this.tramos.size(); i++) {
			Vector<Vertice> aux = this.tramos.elementAt(i).getForma().getVerticesIntersecciones(vertices2);
			for(int j=0; j<aux.size(); j++)
				if(!contieneVertice(solucion,aux.elementAt(j)))
					solucion.add(aux.elementAt(j));
		}
		return solucion;
	}
	
	public Vector<Vertice> getUbicacionesCalleAltura(int a) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		
		// Recorremos los tramos de la calle actual llamando al metodo de su forma que obtiene los vertices que tengan la altura pasada por parametro
		for (int i=0; i<this.tramos.size(); i++) {
			Vector<Vertice> aux = this.tramos.elementAt(i).getForma().getVerticesConAltura(a);
			for(int j=0; j<aux.size(); j++)
				if(!contieneVertice(solucion,aux.elementAt(j)))
					solucion.add(aux.elementAt(j));
		}
		return solucion;
	}
	
	public VerticeDistancia getPtoDistMasCercano(Lugar l) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		
		// Recorremos los tramos de la calle llamando a las funciones de sus formas que obtiene el vertice y la distancia mas cercano 
		for(int i=0; i<this.tramos.size(); i++) {
			VerticeDistancia actual = this.tramos.elementAt(i).getForma().getPtoMasCercano(l.getForma());
			if(actual.getDistancia() < solucion.getDistancia()) {
				solucion.setVertice(actual.getVertice());
				solucion.setDistancia(actual.getDistancia());
			}
		}
		return solucion;
	}
	
	// Si dos calles tienen el mismo nombre, son iguales
	@Override
	public boolean equals(Object c) {
		if(this.getNombre().equals(((Calle) c).getNombre()))
			return true;
		return false;
	}
}

