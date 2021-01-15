package Objetos2020;

import java.util.Vector;

public class Linea extends FormaAbs {
	private Vector<Vertice> vertices;
	
	public Linea() {
		this.vertices = new Vector<Vertice>();
	}
	
	public void addVertice(Vertice v) {
		this.vertices.add(v);
	}
	
	@Override
	public Vector<Vertice> getVertices() {
		Vector<Vertice> aux = new Vector<Vertice>();
		for(int i=0; i<this.vertices.size(); i++)
			aux.add(this.vertices.elementAt(i));
		return aux;
	}
	
	@Override
	public Vector<Vertice> getVerticesConAltura(int a) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		for(int i=0; i<this.vertices.size(); i++)
			if(this.vertices.elementAt(i).getAltura() == a)
				solucion.add(this.vertices.elementAt(i));
		return solucion;
	}

	@Override
	public Vector<Vertice> getVerticesIntersecciones(Vector<Vertice> vertices) {
		Vector<Vertice> solucion = new Vector<Vertice>();
		
		// Recorremos los vertices de la forma y por cada uno, recorremos los vertices del vector pasado por parametro
		for(int i=0; i<this.vertices.size(); i++)
			for(int j=0; j<vertices.size(); j++)
				
				// Si son iguales, lo agregamos al vector solucion
				if(vertices.elementAt(j).equals(this.vertices.elementAt(i)))
					solucion.add(this.vertices.elementAt(i));
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaAbs f) {
		return f.getPtoMasCercano(this);
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Punto p) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		Vertice c3 = p.getVertices().get(0);
		for(int i=0; i<this.vertices.size()-1; i++) {
			Vertice c1 = this.vertices.elementAt(i);
			Vertice c2 = this.vertices.elementAt(i+1);
			VerticeDistancia actual = new VerticeDistancia(null,0);
			
			double deltaLat = c2.getLatitud() - c1.getLatitud();
			double deltaLng = c2.getLongitud() - c1.getLongitud();

			double u = (((c3.getLatitud() - c1.getLatitud()) * deltaLat) + ((c3.getLongitud() - c1.getLongitud()) * deltaLng)) / (deltaLat * deltaLat + deltaLng * deltaLng);
			
			// Primero obtenemos el punto mas cercano
			if (u < 0) 
				actual.setVertice(c1);
			else if (u > 1)
				actual.setVertice(c2);
			else
				actual.setVertice(new Vertice(c1.getLatitud() + u * deltaLat, c1.getLongitud() + u * deltaLng));

			// Luego calculamos la distancia
			int distancia = CargaDatos.getDistanciaPuntoPunto(c3.getLongitud(),c3.getLatitud(),actual.getVertice().getLongitud(),actual.getVertice().getLatitud());
			actual.setDistancia(distancia);
			
			if(actual.getDistancia() < solucion.getDistancia()) {
				solucion.setDistancia(actual.getDistancia());
				solucion.setVertice(actual.getVertice());
			}
		}
		return solucion;
	}

	private VerticeDistancia getInterseccion(Vertice a, Vertice b, Vertice c, Vertice d) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
	    
		double dx12 = b.getLatitud() - a.getLatitud();
	    double dy12 = b.getLongitud() - a.getLongitud();
	    double dx34 = d.getLatitud() - c.getLatitud();
	    double dy34 = d.getLongitud() - c.getLongitud();
	    
	    double denominator = (dy12 * dx34 - dx12 * dy34);

	    double t1 = ((a.getLatitud() - c.getLatitud()) * dy34 + (c.getLongitud() - a.getLongitud()) * dx34) / denominator;
	    double t2 = ((c.getLatitud() - a.getLatitud()) * dy12 + (a.getLongitud() - c.getLongitud()) * dx12) / -denominator;
	    
    	if(((t1 >= 0.0) && (t1 <= 1.0)) && ((t2 >= 0.0) && (t2 <= 1.0))) {
    		solucion.setVertice(new Vertice(a.getLatitud() + dx12 * t1, a.getLongitud() + dy12 * t1));
    		solucion.setDistancia(0);
    	}
	    
	    return solucion;	    
	}
	
	@Override
	public VerticeDistancia getPtoMasCercano(Linea l) {
		VerticeDistancia solucion = new VerticeDistancia(null,Integer.MAX_VALUE);
		Vector<Vertice> vertices2 = l.getVertices();
		for(int i=0; i<this.vertices.size()-1; i++) {
			Vertice a = this.vertices.elementAt(i);
			Vertice b = this.vertices.elementAt(i+1);
			for(int j=0; j<vertices2.size()-1; j++) {
				Vertice c = vertices2.elementAt(j);
				Vertice d = vertices2.elementAt(j+1);
				
				VerticeDistancia actual = getInterseccion(a,b,c,d);
				VerticeDistancia aux = new VerticeDistancia(null,Integer.MAX_VALUE);
					
				// Se comparan los puntos c y d contra la recta a,b ya que se debe devolver el punto mas cercano sobre la calle
				// Si actual es nulo significa que no hubo interseccion entre las lineas
				if(actual.getVertice() == null) {
					
					// Linea a,b y punto c
					Linea l3 = new Linea();
					l3.addVertice(a);
					l3.addVertice(b);
					Punto p3 = new Punto(c);
					aux = l3.getPtoMasCercano(p3);
					if(aux.getDistancia() < actual.getDistancia()) {
						actual.setDistancia(aux.getDistancia());
						actual.setVertice(aux.getVertice());
					}
					
					// Linea a,b y punto d
					Linea l4 = new Linea();
					l4.addVertice(a);
					l4.addVertice(b);
					Punto p4 = new Punto(d);
					aux = l4.getPtoMasCercano(p4);
					if(aux.getDistancia() < actual.getDistancia()) {
						actual.setDistancia(aux.getDistancia());
						actual.setVertice(aux.getVertice());
					}
				}
				
				if(actual.getDistancia() < solucion.getDistancia()) {
					solucion.setVertice(actual.getVertice());
					solucion.setDistancia(actual.getDistancia());
				}
			}
		}
		
		return solucion;
	}

	@Override
	public VerticeDistancia getPtoMasCercano(Poligono p) {
		return this.getPtoMasCercano(p.getLinea());
	}

	@Override
	public VerticeDistancia getPtoMasCercano(FormaCompuesta fc) {
		return fc.getPtoMasCercano(this);
	}
}
