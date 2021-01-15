package Objetos2020;

import java.util.Vector;

public class Poligono extends FormaAbs {
	
	// El perimetro de un poligono es una linea
	private Linea linea;
	
	public Poligono (Linea linea) {
		this.linea = linea;
	}
	
	public Linea getLinea() {
		return this.linea;
	}
	
	@Override
	public Vector<Vertice> getVertices() {
		return this.linea.getVertices();
	}

	@Override
	public Vector<Vertice> getVerticesConAltura(int a) {
		return this.linea.getVerticesConAltura(a);
	}
	
	@Override
	public Vector<Vertice> getVerticesIntersecciones(Vector<Vertice> vertices) {
		return this.linea.getVerticesIntersecciones(vertices);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaAbs f) {
		return f.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Punto p) {
		return this.getLinea().getPtoMasCercano(p);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Linea l) {
		return l.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Poligono p) {
		return this.getLinea().getPtoMasCercano(p);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaCompuesta fc) {
		return fc.getPtoMasCercano(this);
	}
}
