package Interfaz;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Objetos2020.Calle;
import Objetos2020.Ciudad;
import Objetos2020.Lugar;
import Objetos2020.Tramo;
import Objetos2020.Vertice;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Choice;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Servicio1 extends JPanel {
	private JTextField txtBusqueda;
	private Ciudad c;
	
	public Servicio1() {
		setLayout(null);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setEditable(false);
		txtBusqueda.setBounds(228, 74, 280, 20);
		add(txtBusqueda);
		txtBusqueda.setColumns(10);
		
		TextArea txtInfo = new TextArea();
		txtInfo.setBackground(Color.WHITE);
		txtInfo.setEditable(false);
		txtInfo.setBounds(10, 182, 498, 285);
		add(txtInfo);
		
		Choice choice = new Choice();
		choice.setEnabled(false);
		choice.setBounds(228, 74, 280, 20);
		choice.setVisible(false);
		add(choice);
		
		JLabel labelPrincipal = new JLabel("Seleccione que desea buscar");
		labelPrincipal.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelPrincipal.setBounds(10, 29, 204, 34);
		add(labelPrincipal);
		
		JLabel labelOpcion = new JLabel("Seleccione una opci\u00F3n");
		labelOpcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelOpcion.setBounds(87, 74, 127, 20);
		labelOpcion.setVisible(false);
		add(labelOpcion);
		
		JLabel labelBusqueda = new JLabel("Ingrese su b\u00FAsqueda");
		labelBusqueda.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelBusqueda.setBounds(87, 77, 127, 14);
		add(labelBusqueda);
		
		JRadioButton radioCalle = new JRadioButton("Calle");
		radioCalle.setBounds(228, 37, 69, 23);
		add(radioCalle);
		
		JRadioButton radioLugar = new JRadioButton("<html><body>Lugar</body></html>");
		radioLugar.setBounds(334, 37, 63, 23);
		add(radioLugar);
		
		JButton btnVertices = new JButton("Obtener vertices");
		btnVertices.setBounds(256, 153, 252, 23);
		add(btnVertices);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(10, 153, 219, 23);
		add(btnReset);
		
		radioCalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioLugar.isSelected())
					radioLugar.setSelected(false);
				txtBusqueda.setEditable(true);
				txtBusqueda.setVisible(true);
				txtBusqueda.setText("");
				labelBusqueda.setVisible(true);
				choice.enable(true);
				radioCalle.setEnabled(false);
				radioLugar.setEnabled(true);
				choice.setVisible(false);
				labelOpcion.setVisible(false);
			}
		});
		
		radioLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioCalle.isSelected())
					radioCalle.setSelected(false);
				txtBusqueda.setEditable(true);
				txtBusqueda.setText("");
				txtBusqueda.setVisible(true);
				labelBusqueda.setVisible(true);
				choice.enable(true);
				radioCalle.setEnabled(true);
				radioLugar.setEnabled(false);
				labelOpcion.setVisible(false);
				choice.setVisible(false);			
			}
		});
		
		txtBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InicioDatos.getCiudad()!= null) {
					c = InicioDatos.getCiudad();
					choice.removeAll();
					if(txtBusqueda.getText() != null) {
						if(radioCalle.isSelected()) {
							Vector <Calle> calles = new Vector <Calle>();
							calles = c.getCalles(txtBusqueda.getText());
							for(int i=0; i<calles.size(); i++)
								choice.add(calles.get(i).getNombre());
							if(calles.size()!=0) {
								labelBusqueda.setVisible(false);
								labelOpcion.setVisible(true);
								txtBusqueda.setVisible(false);
								choice.setVisible(true);	
							} 
							else 
								JOptionPane.showMessageDialog(null, "La calle no existe, vuelva a intentarlo");	
						}
						else 
							if(radioLugar.isSelected()) {
								Vector<Lugar> lugares = c.getLugares(txtBusqueda.getText());
								for(int i=0; i<lugares.size(); i++)
									choice.add(lugares.elementAt(i).getNombre());
								if(lugares.size()!=0) {
									labelBusqueda.setVisible(false);
									labelOpcion.setVisible(true);
									txtBusqueda.setVisible(false);
									choice.setVisible(true);
							}
							else 
								JOptionPane.showMessageDialog(null, "El lugar no existe, vuelva a intentarlo");
						}		
					}
				}
			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelOpcion.setVisible(false);
				choice.setVisible (false);
				radioCalle.setSelected(false);
				radioLugar.setSelected(false);
				txtBusqueda.setText("");
				txtBusqueda.setEditable(false);
				txtBusqueda.setVisible(true);
				choice.setEnabled(false);
				txtInfo.setText("");
				labelBusqueda.setVisible(true);
				radioLugar.setEnabled(true);
				radioCalle.setEnabled(true);	
			}
		});
		
		btnVertices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtInfo.setText("");
				if(radioCalle.isSelected()) {
					Calle calle = c.getCalle(choice.getSelectedItem());
					Vector<Tramo> tramos = calle.getTramos();
					txtInfo.append("Datos de la calle : " + calle.getNombre()  + "\n");
					for(int i=0; i<tramos.size(); i++) {
						Tramo tramo = tramos.elementAt(i);
						txtInfo.append(("\n" + "Tramo:" + i));
						txtInfo.append("\n" + "Velocidad maxima: " + tramo.getAtributo("VelocidadMaxima"));
						txtInfo.append("\n" + "Tiene una mano? :" + tramo.getAtributo("UnaMano"));
						txtInfo.append("\n" + "Tipo de Calle: " + tramo.getAtributo("TipoDeCalle"));
						txtInfo.append("\n" + "Cantidad de vertices del tramo : " + tramo.getForma().getVertices().size());
						Vector<Vertice> vertices = tramos.elementAt(i).getForma().getVertices();
						for(int j=0; j<vertices.size(); j++){
							Vertice v = vertices.elementAt(j);
							txtInfo.append("\n" + "Latitud : " + v.getLatitud() + " Longitud: " + v.getLongitud() + " Altura: " + v.getAltura());
						}
						txtInfo.append("\n");
					}
				}
				else 
					if(radioLugar.isSelected()) {
						Lugar lugar = c.getLugar(choice.getSelectedItem());
						txtInfo.append("Datos del lugar : " + lugar.getNombre());
						txtInfo.append("\n" + "Altura: " + lugar.getAtributo("Altura"));
						txtInfo.append("\n" + "Tipo de lugar: " + lugar.getAtributo("TipoDeLugar"));
						txtInfo.append("\n" + "Superficie: " + lugar.getAtributo("Superficie"));
						Vector<Vertice> vertices = lugar.getForma().getVertices();
						for(int i=0; i<vertices.size(); i++)
							txtInfo.append("\n" + "Latitud: " + vertices.elementAt(i).getLatitud() + " Longitud: " + vertices.elementAt(i).getLongitud());
					}
			}
		});
	}
}