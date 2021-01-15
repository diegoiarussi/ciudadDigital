package Objetos2020;

import java.util.Vector;

public class FormaCompuesta extends FormaAbs{
	private Vector<FormaAbs> formas;
	
	public FormaCompuesta () {
		this.formas = new Vector <FormaAbs>();
	}
	
	public void addForma(FormaAbs f) {
		this.formas.add(f);
	}
	
	public Vector<FormaAbs> getFormas() {
		Vector<FormaAbs> aux = new Vector<FormaAbs>();
		for(int i=0; i<this.formas.size(); i++) 
			aux.add(this.formas.elementAt(i));
		return aux;
	}
	
	@Override
	public Vector<Vertice> getVertices() {
		Vector<Vertice> aux = new Vector<Vertice>();
		
		// Obtenemos todos los vertices de cada forma
		for(int i=0; i<this.formas.size(); i++)
			aux.addAll(this.formas.elementAt(i).getVertices());
		return aux;
	}
	
	@Override
	public Vector<Vertice> getVerticesConAltura(int a) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		
		// Obtenemos todos los vertices de cada forma con la misma altura que la pasada por paraemtro
		for(int i=0; i<this.formas.size(); i++)
			solucion.addAll(this.formas.elementAt(i).getVerticesConAltura(a));
		return solucion;
	}

	@Override
	public Vector<Vertice> getVerticesIntersecciones(Vector<Vertice> vertices) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		
		// Obtenemos todos los vertices de cada forma, que se encuentren dentro del vector de vertices pasado por parametro
		for(int i=0; i<this.formas.size(); i++)
			solucion.addAll(this.formas.elementAt(i).getVerticesIntersecciones(vertices));
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaAbs f) {
		return f.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Punto p) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		for(int i=0; i<this.formas.size(); i++) {
			
			// Llamamos al metodo de la forma i que recibe un punto por parametro
			VerticeDistancia actual = this.formas.elementAt(i).getPtoMasCercano(p);
			if(actual.getDistancia() < solucion.getDistancia()) {
				solucion.setVertice(actual.getVertice());
				solucion.setDistancia(actual.getDistancia());
			}
		}
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Linea l) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		for(int i=0; i<this.formas.size(); i++) {
			
			// Llamamos al metodo de la forma i que recibe una linea por parametro
			VerticeDistancia actual = this.formas.elementAt(i).getPtoMasCercano(l);
			if(actual.getDistancia() < solucion.getDistancia()) {
				solucion.setVertice(actual.getVertice());
				solucion.setDistancia(actual.getDistancia());
			}
		}
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Poligono p) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		for(int i=0; i<this.formas.size(); i++) {
			
			// Llamamos al metodo de la forma i que recibe un poligono por parametro
			VerticeDistancia actual = this.formas.elementAt(i).getPtoMasCercano(p);
			if(actual.getDistancia() < solucion.getDistancia()) {
				solucion.setVertice(actual.getVertice());
				solucion.setDistancia(actual.getDistancia());
			}
		}
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaCompuesta fc) {
		
		// Por cada forma de la forma actual, recorremos las formas del objeto pasado por parametro y le calculamos el punto mas cercano
		// En una ciudad este caso podria darse si se calcula el punto mas cercano entre una multiLinea y un multiPoligono/multiPunto
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		Vector<FormaAbs> formas2 = fc.getFormas();
		for(int i=0; i<this.formas.size(); i++) {
			for(int j=0; j<formas2.size(); j++) {
				
				// Llamamos al metodo de la forma j pasando la forma i por parametro
				VerticeDistancia actual = formas2.elementAt(j).getPtoMasCercano(this.formas.elementAt(i));
				if(actual.getDistancia() < solucion.getDistancia()) {
					solucion.setVertice(actual.getVertice());
					solucion.setDistancia(actual.getDistancia());
				}
			}
		}
		return solucion;
	}
}
