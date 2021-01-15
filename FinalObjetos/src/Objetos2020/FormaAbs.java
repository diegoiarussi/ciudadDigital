package Objetos2020;

import java.util.Vector;

public abstract class FormaAbs {
	
	// Retorna todos los vertices que componen la forma
	public abstract Vector<Vertice> getVertices();
	
	// Retorna todos los vertices que posean la altura pasada por parametro
	public abstract Vector<Vertice> getVerticesConAltura(int a);
	
	// Retorna todos los vertices que se encuentren dentro del vector de vertices pasado por parametro
	public abstract Vector<Vertice> getVerticesIntersecciones(Vector<Vertice> vertices);
	
	// Retornan el vertice y la distancia mas cercano a la forma actual
	public abstract VerticeDistancia getPtoMasCercano(FormaAbs f);	
	public abstract VerticeDistancia getPtoMasCercano(Punto p);
	public abstract VerticeDistancia getPtoMasCercano(Linea l);
	public abstract VerticeDistancia getPtoMasCercano(Poligono p);
	public abstract VerticeDistancia getPtoMasCercano(FormaCompuesta fc);
	
	// Si dos formas tienen los mismos vertices, son iguales
	@Override
	public boolean equals(Object f) {
		Vector<Vertice> aux = this.getVertices();
		Vector<Vertice> aux2 = ((FormaAbs) f).getVertices();
		for(int i=0; i<aux.size(); i++) {
			for(int j=0; j<aux2.size(); j++)
				if(!aux.elementAt(i).equals(aux2.elementAt(j)))
					return false;
		}
		return true;
	}
}
