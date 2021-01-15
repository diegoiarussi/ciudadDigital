package Interfaz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Objetos2020.Calle;
import Objetos2020.Ciudad;
import Objetos2020.Vertice;

import javax.swing.JButton;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Servicio2 extends JPanel {
	private JTextField txtCalle;
	private JTextField txtInterseccion;
	private JTextField txtAltura;
	private Ciudad c;

	public Servicio2() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Selecione que desea buscar");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(10, 41, 156, 53);
		add(lblNewLabel);
		
		JLabel selectorCalle = new JLabel("Seleccione una opción");
		selectorCalle.setVisible(false);
		selectorCalle.setFont(new Font("Tahoma", Font.BOLD, 10));
		selectorCalle.setBounds(57, 105, 109, 20);
		add(selectorCalle);
		
		JLabel txtSelecInterseccion = new JLabel("<html><body>Seleccione una opción</body></html>");
		txtSelecInterseccion.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtSelecInterseccion.setBounds(57, 194, 124, 20);
		txtSelecInterseccion.setVisible(false);
		add(txtSelecInterseccion);
		
		JLabel lblNewLabel_2 = new JLabel("Ingrese la altura");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(57, 127, 109, 14);
		add(lblNewLabel_2);
		
		JLabel nombreCalle = new JLabel("Ingrese la calle");
		nombreCalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		nombreCalle.setBounds(57, 105, 105, 14);
		add(nombreCalle);
		
		JLabel labelInterseccion = new JLabel("Ingrese la calle");
		labelInterseccion.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelInterseccion.setBounds(57, 197, 109, 14);
		add(labelInterseccion);
		
		JRadioButton radioAltura = new JRadioButton("Calle con altura");
		radioAltura.setBounds(172, 71, 229, 23);
		add(radioAltura);
		
		JRadioButton radioInterseccion = new JRadioButton("Intersección de dos calles");
		radioInterseccion.setBounds(172, 41, 229, 23);
		add(radioInterseccion);
		
		txtCalle = new JTextField();
		txtCalle.setEditable(false);
		txtCalle.setBounds(172, 101, 323, 20);
		add(txtCalle);
		txtCalle.setColumns(10);
		
		txtAltura = new JTextField();
		txtAltura.setEditable(false);
		txtAltura.setBounds(172, 124, 323, 20);
		add(txtAltura);
		txtAltura.setColumns(10);
		
		txtInterseccion = new JTextField();
		txtInterseccion.setEditable(false);
		txtInterseccion.setBounds(172, 194, 323, 20);
		add(txtInterseccion);
		txtInterseccion.setColumns(10);
		
		Choice choiceInterseccion = new Choice();
		choiceInterseccion.setEnabled(false);
		choiceInterseccion.setBounds(172, 194, 323, 20);
		choiceInterseccion.setVisible(false);
		add(choiceInterseccion);
		
		Choice choiceCalle = new Choice();
		choiceCalle.setEnabled(false);
		choiceCalle.setBounds(172, 100, 323, 20);
		choiceCalle.setVisible(false);
		add(choiceCalle);
		
		TextArea txtInfo = new TextArea();
		txtInfo.setBackground(Color.WHITE);
		txtInfo.setEditable(false);
		txtInfo.setBounds(7, 297, 488, 191);
		add(txtInfo);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(10, 268, 229, 23);
		add(btnReset);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(249, 268, 246, 23);
		add(btnBuscar);
		
		radioAltura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioInterseccion.setSelected(false);
				txtInterseccion.enable(false);
				choiceInterseccion.enable(false);
				txtInterseccion.setText("");
				choiceInterseccion.removeAll();
				txtAltura.setEditable(true);
				txtAltura.enable(true);
				txtCalle.setEditable(true);
				choiceCalle.enable(true);
				radioInterseccion.setEnabled(true);
				radioAltura.setEnabled(false);
			}
		});
		
		radioInterseccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioAltura.setSelected(false);
				txtAltura.setText("");
				txtAltura.enable(false);
				txtInterseccion.enable(true);
				txtInterseccion.setEditable(true);
				txtInterseccion.setVisible(true);
				txtCalle.setEditable(true);
				txtCalle.enable(true);
				txtCalle.setVisible(true);
				selectorCalle.setVisible(false);
				nombreCalle.setVisible(true);
				choiceCalle.setVisible(false);
				choiceInterseccion.enable(true);
				radioInterseccion.setEnabled(false);
				radioAltura.setEnabled(true);
			}
		});
		
		txtCalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InicioDatos.getCiudad() != null) {
					c = InicioDatos.getCiudad();
					choiceCalle.removeAll();
					Vector <Calle> calles = new Vector <Calle>();
					calles = c.getCalles(txtCalle.getText());
					txtCalle.setText("");
					for(int i=0; i<calles.size(); i++)
						choiceCalle.add(calles.get(i).getNombre());
					if(calles.size()!= 0) {
						nombreCalle.setVisible(false);
						txtCalle.setVisible(false);
						selectorCalle.setVisible(true);
						choiceCalle.setVisible(true);
						choiceCalle.setEnabled(true);
					}
					else
						JOptionPane.showMessageDialog(null, "La calle no existe, vuelva a intentarlo");	
				}
			}
		});
		
		txtInterseccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InicioDatos.getCiudad() != null) {
					c = InicioDatos.getCiudad();
					choiceInterseccion.removeAll();
					Vector <Calle> calles = new Vector <Calle>();
					calles = c.getCalles(txtInterseccion.getText());
					txtInterseccion.setText("");
					for(int i=0; i<calles.size(); i++)
						choiceInterseccion.add(calles.get(i).getNombre());
					if(calles.size() != 0) {
						labelInterseccion.setVisible(false);
						txtSelecInterseccion.setVisible(true);
						txtInterseccion.setVisible(false);
						choiceInterseccion.setVisible(true);
					} 
					else
						JOptionPane.showMessageDialog(null, "La calle no existe, vuelva a intentarlo");	
				}
			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectorCalle.setVisible(false);
				choiceCalle.setVisible(false);
				txtCalle.setVisible(true);
				txtCalle.setText("");
				txtCalle.setEditable(false);
				nombreCalle.setVisible(true);
				txtAltura.setText("");
				txtAltura.setEditable(false);
				txtInterseccion.setText("");
				txtInterseccion.setVisible(true);
				txtInterseccion.setEditable(false);
				labelInterseccion.setVisible(true);
				txtSelecInterseccion.setVisible(false);
				choiceInterseccion.setVisible(false);
				radioAltura.setSelected(false);
				radioInterseccion.setSelected(false);
				radioInterseccion.setEnabled(true);
				radioAltura.setEnabled(true);
				txtInfo.setText("");
			}
		});
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioAltura.isSelected()) {
					Calle calle = c.getCalle(choiceCalle.getSelectedItem());
					int altura = Integer.parseInt(txtAltura.getText());
					txtInfo.setText("");
					if(calle != null) {
						Vector<Vertice> vertices = calle.getUbicacionesCalleAltura(altura);
						for(int i=0; i<vertices.size(); i++) {
							txtInfo.append("¡Altura encontrada!"+ "\n"+ "Vertice/s:");
							txtInfo.append("\n"+ "Latitud: " + vertices.elementAt(i).getLatitud() + " Longitud: " + vertices.elementAt(i).getLongitud());
						}
						if(vertices.size() == 0)
							txtInfo.append("La calle no posee la altura ingresada, vuelva a intentarlo");
					}
				}
				else 
					if(radioInterseccion.isSelected()) {
						txtInfo.setText("");
						Calle calle = c.getCalle(choiceCalle.getSelectedItem());
						Calle interseccion = c.getCalle(choiceInterseccion.getSelectedItem());
						if(calle != null && interseccion != null) {
							Vector <Vertice> vertices = calle.getUbicacionesIntersecciones(interseccion);
							if(vertices.size() == 0)
								txtInfo.append("No se encontro punto de intersección entre las calles seleccionadas");
							else {
								txtInfo.append("¡Intersección encontrada!" + "\n" );
								txtInfo.append("Vertice/s: ");
								for(int i=0; i<vertices.size(); i++) 
									txtInfo.append("\n" + "Latitud: " + vertices.elementAt(i).getLatitud() + " Longitud: " + vertices.elementAt(i).getLongitud() + " Altura: " + vertices.elementAt(i).getAltura());
							}
						}
					}
			}
		});
	}
}