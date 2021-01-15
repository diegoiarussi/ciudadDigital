package Objetos2020;

import java.util.Vector;

public class Punto extends FormaAbs {
	private Vertice vertice;
	
	public Punto (Vertice vertice) {
		this.vertice = vertice;
	}

	public void setVertice(Vertice vertice) {
		this.vertice = vertice;
	}
	
	@Override
	public Vector<Vertice> getVertices() {
		Vector<Vertice> aux = new Vector<Vertice>();
		aux.add(this.vertice);
		return aux;
	}

	@Override
	public Vector<Vertice> getVerticesConAltura(int a) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		if(this.vertice.getAltura() == a)
			solucion.add(this.vertice);
		return solucion;
	}

	@Override
	public Vector<Vertice> getVerticesIntersecciones(Vector<Vertice> vertices) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		for(int i=0; i<vertices.size(); i++)
			if(vertices.elementAt(i).equals(this.vertice))
				solucion.add(this.vertice);
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaAbs f) {
		return f.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Punto p) {
		VerticeDistancia solucion = new VerticeDistancia(vertice,
														 CargaDatos.getDistanciaPuntoPunto(this.vertice.getLongitud(), 
																 						   this.vertice.getLatitud(), 
																						   p.getVertices().get(0).getLongitud(), 
																						   p.getVertices().get(0).getLatitud()));
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Linea l) {
		return l.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Poligono p) {
		return p.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaCompuesta fc) {
		return fc.getPtoMasCercano(this);
	}
}
