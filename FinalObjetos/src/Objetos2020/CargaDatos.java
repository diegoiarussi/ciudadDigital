package Objetos2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CargaDatos {
	private Vector<Vertice> verticesConAltura = new Vector<Vertice>();  // Estos vertices solo son utilizados para asociarle altura a las calles
	private boolean datosActuales;
	
	public boolean construirVerticesConAltura() {
		
		// Este metodo es booleano para poder chequear si se ha realizado con exito
		JSONParser jsonParser = new JSONParser();
		String path = "vertices.geojson";
		int returnVal =0;
		JFileChooser chooser = new JFileChooser();
		if (!this.getDatosActuales()) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("geojson", "GEOJSON");
			chooser.setFileFilter(filter);
			returnVal = chooser.showOpenDialog(null);
		}
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = chooser.getSelectedFile();
			if (fichero == null) {
				fichero = new File (path);
				chooser.setCurrentDirectory(fichero);
			}
			try (FileReader reader = new FileReader(path))
			{
				Object obj = jsonParser.parse(reader);
				JSONObject jsonObject = (JSONObject) obj; 
				JSONArray jsonArray = (JSONArray) jsonObject.get("features");    
				
				// Recorremos todos los vertices que hay en el archivo geojson
				for(int i=0; i<jsonArray.size(); i++) {
					JSONObject verticeConAltura = (JSONObject) jsonArray.get(i);
					JSONObject propiedades = (JSONObject) verticeConAltura.get("properties");
				
					// Si el vertice no tiene nombre de calle asociado, no se carga
					if(propiedades.get("addr:street") != null) {
						JSONObject geometria = (JSONObject) verticeConAltura.get("geometry");
						JSONArray vertices = (JSONArray) geometria.get("coordinates");
					
						// No es necesario asignarle una forma
						String nombreCalle = (String) propiedades.get("addr:street");
						String altura = (String) propiedades.get("addr:housenumber");
						Vertice vertice = new Vertice((double)vertices.get(1),(double)vertices.get(0),Integer.parseInt(altura),nombreCalle);
						verticesConAltura.add(vertice);
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean construirCalles(Ciudad ciudad) {	
		
		// Este metodo es booleano para poder chequear si se ha realizado con exito
		JSONParser jsonParser = new JSONParser();
		String path = "calles.geojson";
		int returnVal =0;
		JFileChooser chooser= new JFileChooser();
		if (!this.getDatosActuales()) { 
			FileNameExtensionFilter filter = new FileNameExtensionFilter("geojson", "GEOJSON");
			chooser.setFileFilter(filter);
			returnVal = chooser.showOpenDialog(null);
		}	
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = chooser.getSelectedFile();
			if (fichero == null) {
				fichero = new File (path);
				chooser.setCurrentDirectory(fichero);
			}
			try (FileReader reader = new FileReader(fichero)){
				Object obj = jsonParser.parse(reader);
				JSONObject jsonObject = (JSONObject) obj; 
				JSONArray jsonArray = (JSONArray) jsonObject.get("features");
				
				// Recorremos todos los tramos que hay en el archivo geojson
				for(int i=0; i<jsonArray.size(); i++) {
					JSONObject tramo = (JSONObject) jsonArray.get(i);
					JSONObject propiedades = (JSONObject) tramo.get("properties");
					
					// Si la calle no tiene nombre no la cargamos ya que seria imposible identificarla
					if(propiedades.get("name") != null) {
						JSONObject geometria = (JSONObject) tramo.get("geometry");
						JSONArray vertices = (JSONArray) geometria.get("coordinates");
						FormaAbs forma = null;
						String tipo = (String) geometria.get("type");
					
						// Segun el tipo que sea, creamos la forma correspondiente
						// Si el tipo es punto no se crea, debido a que los tramos de las calles solo son lineas o multilineas
						// Las alturas solo se asocian a los vertices que forman las calles
						// En algunos casos quedan vertices con una altura de -1 debido a que no se encontraron vertices con altura que esten cerca 
						if(!tipo.equals("Point")) {
							switch(tipo) {
							case "LineString":
								forma = getLinea(vertices,(String)propiedades.get("name"),ciudad);
								break;
							case "MultiLineString":
								forma = getMultiLinea(vertices,(String)propiedades.get("name"),ciudad);
								break;
							default:
								break;
							}
							
							// Cargamos las propiedades del tramo
							cargarAtributosCalle(ciudad,propiedades,forma);
						}
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean construirLugares(Ciudad ciudad) {		
		
		// Este metodo es booleano para poder chequear si se ha realizado con exito
		String path = "lugares.geojson";
		JSONParser jsonParser = new JSONParser();
		JFileChooser chooser = new JFileChooser();
		int returnVal = 0;
		if (!this.getDatosActuales()) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("geojson", "GEOJSON");
			chooser.setFileFilter(filter);
			returnVal= chooser.showOpenDialog(null);
		}
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = chooser.getSelectedFile();
			if (fichero == null) {
				fichero = new File (path);
				chooser.setCurrentDirectory(fichero);
			}
			try (FileReader reader = new FileReader(fichero)){
				Object obj = jsonParser.parse(reader);
				JSONObject jsonObject = (JSONObject) obj; 
				JSONArray jsonArray = (JSONArray) jsonObject.get("features");
				
				// Recorremos todos los lugares que hay en el archivo geojson
				for(int i=0; i<jsonArray.size(); i++) {
					JSONObject lugar = (JSONObject) jsonArray.get(i);
					JSONObject propiedades = (JSONObject) lugar.get("properties");	            
					
					// Si el lugar no tiene nombre no se carga ya que seria imposible identificarlo
					if(propiedades.get("name") != null) {
						JSONObject geometria = (JSONObject) lugar.get("geometry");
						JSONArray vertices = (JSONArray) geometria.get("coordinates");
						FormaAbs forma;	
						String tipo = (String) geometria.get("type");	
						
						// Segun el tipo que sea, creamos la forma correspondiente
						switch(tipo) {
							case "MultiPolygon":
								forma = getMultiPoligono(vertices);
								break;
							case "MultiPoint":
								forma = getMultiPunto(vertices);
								break;
							case "Polygon":
								forma = getPoligono(vertices);
								break;
							case "Point":
								forma = getPunto(vertices);
								break;
							default:
								forma = null;
								break;
						}
						
						// Cargamos las propiedades del lugar
						cargarAtributosLugar(ciudad,propiedades,forma);
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private Punto getPunto(JSONArray vertices) {
		
		// En la posicion 0 de vertices se encuentra la longitud y en la posicion 1 la latitud
		Vertice vertice = new Vertice((double)vertices.get(1),(double)vertices.get(0));
		Punto forma = new Punto(vertice);
		return forma;
	}
	
	private Linea getLinea(JSONArray vertices, String nombre, Ciudad ciudad) {
		Linea forma = new Linea();
		for(int j=0; j<vertices.size(); j++) {
			JSONArray latLng = (JSONArray) vertices.get(j);
			
			// Creamos el vertice con la altura correspondiente obtenida mediante el metodo getAlturaAsociada
			Vertice vertice = new Vertice((double)latLng.get(1),(double)latLng.get(0),getAlturaAsociada(nombre,(double)latLng.get(0),(double)latLng.get(1)));	
			forma.addVertice(vertice);
		}
		return forma;
	} 
	
	private Poligono getPoligono(JSONArray vertices) {
		Linea linea = new Linea();
		JSONArray coordenadas = (JSONArray) vertices.get(0);
		for(int j=0; j<coordenadas.size(); j++) {
			JSONArray latLng = (JSONArray) coordenadas.get(j);
			Vertice vertice = new Vertice((double)latLng.get(1),(double)latLng.get(0));
			linea.addVertice(vertice);
		}
		Poligono forma = new Poligono(linea);
		return forma;
	}
	
	private FormaCompuesta getMultiPunto(JSONArray vertices) { 
		FormaCompuesta forma = new FormaCompuesta();
		for(int j=0; j<vertices.size(); j++) {
			JSONArray latLng = (JSONArray) vertices.get(j);
			Vertice vertice = new Vertice((double)latLng.get(1),(double)latLng.get(0));
			Punto punto = new Punto(vertice);
			forma.addForma(punto);
		}
		return forma;
	}
	
	private FormaCompuesta getMultiLinea(JSONArray vertices, String nombre, Ciudad ciudad) {
		FormaCompuesta forma = new FormaCompuesta();
		for(int i=0; i<vertices.size(); i++) {
			Linea linea = new Linea();
			JSONArray coordenadas = (JSONArray) vertices.get(i);
			for(int j=0; j<coordenadas.size(); j++) {
				JSONArray latLng = (JSONArray) coordenadas.get(j);
				
				// Creamos el vertice con la altura obtenida por el metodo getAlturaAsociada
				Vertice vertice = new Vertice((double)latLng.get(1),(double)latLng.get(0),getAlturaAsociada(nombre,(double)latLng.get(0),(double)latLng.get(1)));		
				linea.addVertice(vertice);
			}
			forma.addForma(linea);
		}
		return forma;
	}
	
	private FormaCompuesta getMultiPoligono(JSONArray vertices) {
		FormaCompuesta forma = new FormaCompuesta();
		for(int i=0; i<vertices.size(); i++) {
			Linea linea = new Linea();
			JSONArray coordenadas = (JSONArray) vertices.get(i);
			for(int j=0; j<coordenadas.size(); j++) {
				JSONArray latLng = (JSONArray) coordenadas.get(j);
				Vertice vertice = new Vertice((double)latLng.get(1),(double)latLng.get(0));
				linea.addVertice(vertice);
			}
			Poligono poligono = new Poligono(linea);
			forma.addForma(poligono);
		}
		return forma;
		
	};
		
	private void cargarAtributosCalle(Ciudad ciudad, JSONObject propiedades, FormaAbs forma) {
		
		// Si la forma no tiene vertices cargados no se carga el tramo
		// Esto podria ocurrir por no cargar vertices repetidos. De esta manera evitamos ese error
		if(forma.getVertices().size() > 0) {
			
			// Obtenemos la caracteristicas principales para añadirselas al tramo creado
			String velocidadMax = "";
			if(propiedades.get("maxspeed") != null) 
				velocidadMax = (String) propiedades.get("maxspeed");
			else
				velocidadMax = "unknown";
			
			String tipoCalle = "";
			if(propiedades.get("highway") != null) 
				tipoCalle = (String) propiedades.get("highway");
			else 
				tipoCalle = "unknown";
			
			String unaMano = "";
			if(propiedades.get("oneway") != null) 
				unaMano = (String) propiedades.get("oneway");
			else
				unaMano = "no";
	
			String carriles = "";
			if(propiedades.get("lanes") != null) 
				carriles = (String) propiedades.get("lanes");
			else
				carriles = "unknown";
		
			// Añadimos las caracteristicas al tramo
			Tramo tramoCalle = new Tramo(forma);
			tramoCalle.addAtributo("VelocidadMaxima", velocidadMax);
			tramoCalle.addAtributo("TipoDeCalle", tipoCalle);
			tramoCalle.addAtributo("UnaMano", unaMano);
			tramoCalle.addAtributo("Carriles", carriles);
		
			// Si existe la calle le agregamos el tramo, si no la creamos
			String nombre = (String) propiedades.get("name");
			Calle calle = ciudad.getCalle(nombre);
			if(calle != null)
				calle.addTramo(tramoCalle);
			else {
				calle = new Calle(nombre);
				calle.addTramo(tramoCalle);
				ciudad.addCalle(calle);
			}
		}
	}
	
	private void cargarAtributosLugar(Ciudad ciudad, JSONObject propiedades, FormaAbs forma) {
		
		// Obtenemos la caracteristicas principales para añadirselas al lugar creado
		String tipoLugar = "";
		if(propiedades.get("amenity") != null) 
			tipoLugar = (String) propiedades.get("amenity");
		else
			tipoLugar = "unknown";
			
		String superficie = "";
		if(propiedades.get("surface") != null) 
			superficie = (String) propiedades.get("surface");
		else 
			superficie = "unknown";
		
		int altura = 0;
		if(propiedades.get("addr:housenumber") != null)
			altura = Integer.parseInt((String)propiedades.get("addr:housenumber"));
		else
			altura = -1;
		
		String nombre = (String) propiedades.get("name");
	
		// Creamos el lugar y le añadimos las caractiristicas obtenidas
		Lugar lugar = new Lugar(forma,nombre);	
		lugar.addAtributo("TipoDeLugar", tipoLugar);
		lugar.addAtributo("Superficie", superficie);
		lugar.addAtributo("Altura", altura);
		
		// No se cargan lugares que posean el mismo nombre ya que no seria posible diferenciarlos
		if(ciudad.getLugar(nombre) == null)
			ciudad.addLugar(lugar);
	}
	
	private int getAlturaAsociada(String nombre, double lon , double lat) {
		int distancia = 0;
		int altura = -1;
		
		// Recorremos todos los vertices con altura para asociarlos a la calle correspondiente
		for(int i=0; i<verticesConAltura.size(); i++) {
			
			// Si tienen el mismo nombre, calculamos su distancia mediante el metodo de Haversine
			if(verticesConAltura.elementAt(i).getNombre().equals(nombre)) {
				distancia = getDistanciaPuntoPunto(lon,lat,verticesConAltura.elementAt(i).getLongitud(),verticesConAltura.elementAt(i).getLatitud());
				
				// Si la distancia es menor que 30 mts, entonces se devuelve esa altura para poder asociarla
				if(distancia < 30)
					altura = verticesConAltura.elementAt(i).getAltura();
			}
		}
		return altura;
	}

	public void setDatosActuales(boolean t) {
		this.datosActuales = t;
	}
	
	public boolean getDatosActuales() {
		return this.datosActuales;
	}

	public static int getDistanciaPuntoPunto(double lon1, double lat1, double lon2, double lat2) {
		
		// Este metodo es estatico ya que es utilizado en las clases de formas
		// Se trata de una formula desarrollada por Haversine para obtener la distancia entre dos puntos ubicados en la tierra
		// Se retorna la distancia en metros 
		double radioTierra = 6371; // km

		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double dlon = (lon2 - lon1);
		double dlat = (lat2 - lat1);

		double sinlat = Math.sin(dlat / 2);
		double sinlon = Math.sin(dlon / 2);

		double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
		double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

		double distanciaEnMetros = radioTierra * c * 1000;

		return (int)distanciaEnMetros;
	}

}
