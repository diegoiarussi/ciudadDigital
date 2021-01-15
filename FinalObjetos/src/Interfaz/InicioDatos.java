package Interfaz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import Objetos2020.CargaDatos;
import Objetos2020.Ciudad;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class InicioDatos extends JPanel {
	static Ciudad ciudad;
	
	private JTextField nombreCiudad;
	private JTextField txtCiudadActual;
	
	private CargaDatos data;
	private boolean calle;
	private boolean lugar;
	private boolean vertice;
	
	public InicioDatos() {
		calle = false;
		vertice = false;
		lugar = false;
		ciudad = new Ciudad("");
		data = new CargaDatos();
		txtCiudadActual = new JTextField();
		
		setLayout(null);
		
		JPanel panel = new JPanel();		
		panel.setBounds(10, 11, 481, 378);
		add(panel);
		panel.setLayout(null);
		
		txtCiudadActual.setEditable(false);
		txtCiudadActual.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCiudadActual.setBounds(10, 11, 461, 67);
		txtCiudadActual.setColumns(10);
		panel.add(txtCiudadActual);
		
		nombreCiudad = new JTextField();
		nombreCiudad.setBounds(146, 89, 296, 28);
		nombreCiudad.setColumns(10);
		panel.add(nombreCiudad);
		
		JLabel lblNewLabel = new JLabel("Ciudad: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(63, 89, 73, 28);
		panel.add(lblNewLabel);
			
		JButton btnCalle = new JButton("Cargar calles");
		btnCalle.setBounds(10, 196, 461, 23);
		panel.add(btnCalle);
		
		JButton btnLugar = new JButton("Cargar lugares");
		btnLugar.setBounds(10, 251, 461, 23);
		panel.add(btnLugar);
		
		JButton btnVertice = new JButton("Cargar vertices con altura");
		btnVertice.setBounds(10, 143, 461, 23);
		panel.add(btnVertice);

		// El boton de crear ciudad no se activa hasta que ya esten cargados todos los datos de la misma
		JButton btnMapa = new JButton("Crear ciudad");
		btnMapa.setBackground(UIManager.getColor("Button.light"));
		btnMapa.setForeground(Color.BLACK);
		btnMapa.setEnabled(false);
		btnMapa.setBounds(240, 344, 231, 23);
		panel.add(btnMapa);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(10, 344, 218, 23);
		panel.add(btnReset);
		
		// El panel principal no se muestra al inicio
		panel.setVisible(false);
		
		// Mensaje inicial para elegir si se trabaja con la ciudad predeterminada o no
		int n = JOptionPane.showConfirmDialog(null, "Desea cargar los datos de la ciudad de Tandil?","Bienvenido a la app!", JOptionPane.YES_NO_OPTION);
		
		// Se carga Tandil
		if(n == JOptionPane.YES_OPTION) {
			panel.setVisible(true);
			data.setDatosActuales(true);
			vertice = data.construirVerticesConAltura();
			calle = data.construirCalles(ciudad);
			lugar = data.construirLugares(ciudad);
			if(vertice && calle && lugar) {
				lblNewLabel.setVisible(false);
				nombreCiudad.setVisible(false);
				btnCalle.setEnabled(false);
				btnLugar.setEnabled(false);
				btnVertice.setEnabled(false);
				ciudad.setNombre("Tandil");
				txtCiudadActual.setText("La ciudad cargada actualmente es: " + ciudad.getNombre());	
			}
			else 
				JOptionPane.showMessageDialog(null, "No fue posible cargar la ciudad predeterminada, por favor intente cargarlo manualmente");
		} 
		else 
			if(n == JOptionPane.NO_OPTION)
				
				// No se carga ninguna ciudad, de esta tarea pasa a encargarse el usuario
				panel.setVisible(true);
			else
				System.exit(0);
		
		
		btnVertice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vertice = data.construirVerticesConAltura();
			}
		});
		
		btnCalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!vertice)
					JOptionPane.showMessageDialog(null, "Usted debe ingresar primero los vertices con altura de la ciudad para cargar las calles");
				else {
					calle = data.construirCalles(ciudad);
					if(lugar && vertice && calle)
						btnMapa.setEnabled(true);
				}
			}	
		});
		
		btnLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lugar = data.construirLugares(ciudad);
				if(lugar && vertice && calle)
					btnMapa.setEnabled(true);
			}
		});
		
		btnMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!nombreCiudad.getText().equals("")) {
					ciudad.setNombre(nombreCiudad.getText());
					lblNewLabel.setVisible(false);
					nombreCiudad.setVisible(false);
					btnCalle.setEnabled(false);
					btnLugar.setEnabled(false);
					btnVertice.setEnabled(false);
					btnMapa.setEnabled(false);
					txtCiudadActual.setText("La ciudad cargada actualmente es: " + ciudad.getNombre());	
					JOptionPane.showMessageDialog(null, "Bienvenido a " + ciudad.getNombre() + "! Ahora puede utilizar los servicios del menu que desee");
				} 
				else 
					JOptionPane.showMessageDialog(null, "Usted debe ingresar primero el nombre de la ciudad");
			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data.setDatosActuales(false);
				lblNewLabel.setVisible(true);
				nombreCiudad.setVisible(true);
				btnCalle.setEnabled(true);
				btnLugar.setEnabled(true);
				btnVertice.setEnabled(true);
				ciudad = new Ciudad("");
				txtCiudadActual.setText("");
				vertice = false;
				lugar = false;
				calle = false;
			}
		});
	}
	
	public static Ciudad getCiudad() {
		return ciudad;
	}
}