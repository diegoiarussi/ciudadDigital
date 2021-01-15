package Interfaz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import Objetos2020.Calle;
import Objetos2020.Ciudad;
import Objetos2020.Linea;
import Objetos2020.Lugar;
import Objetos2020.Punto;
import Objetos2020.Vertice;
import Objetos2020.VerticeDistancia;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.Choice;
import java.awt.Color;
import javax.swing.JButton;

public class Servicio3 extends JPanel {
	private JTextField txtCalle;
	private JTextField txtLugar;
	
	private Ciudad c;
	
	public Servicio3() {
		setLayout(null);
		
		JLabel labelCalle = new JLabel("Ingrese la calle");
		labelCalle.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelCalle.setBounds(10, 26, 129, 26);
		add(labelCalle);
		
		JLabel labelOpCalle = new JLabel("Seleccione una opci\u00F3n");
		labelOpCalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelOpCalle.setBounds(10, 26, 129, 26);
		labelOpCalle.setVisible(false);
		add(labelOpCalle);
		
		JLabel labelLugar = new JLabel("Ingrese el lugar");
		labelLugar.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelLugar.setBounds(10, 82, 129, 26);
		add(labelLugar);
		
		JLabel labelOpLugar = new JLabel("Seleccione una opci\u00F3n");
		labelOpLugar.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelOpLugar.setBounds(10, 82, 129, 26);
		labelOpLugar.setVisible(false);
		add(labelOpLugar);
		
		txtCalle = new JTextField();
		txtCalle.setEditable(true);
		txtCalle.setBounds(172, 26, 336, 26);
		add(txtCalle);
		txtCalle.setColumns(10);
		
		txtLugar = new JTextField();
		txtLugar.setEditable(true);
		txtLugar.setBounds(172, 82, 336, 26);
		add(txtLugar);
		txtLugar.setColumns(10);
		
		TextArea txtInfo = new TextArea();
		txtInfo.setBackground(Color.WHITE);
		txtInfo.setEditable(false);
		txtInfo.setBounds(10, 182, 498, 285);
		add(txtInfo);
		
		Choice choiceCalle = new Choice();
		choiceCalle.setEnabled(false);
		choiceCalle.setBounds(172, 26, 336, 26);
		choiceCalle.setVisible(false);
		add(choiceCalle);
		
		Choice choiceLugar = new Choice();
		choiceLugar.setEnabled(false);
		choiceLugar.setBounds(172, 82, 336, 26);
		choiceLugar.setVisible(false);
		add(choiceLugar);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(10, 153, 219, 23);
		add(btnReset);
		
		JButton btnPto = new JButton("Obtener punto más cercano");
		btnPto.setBounds(256, 153, 252, 23);
		add(btnPto);
		
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
						labelCalle.setVisible(false);
						txtCalle.setVisible(false);
						labelOpCalle.setVisible(true);
						choiceCalle.setVisible(true);
						choiceCalle.setEnabled(true);
					}
					else
						JOptionPane.showMessageDialog(null, "La calle no existe, vuelva a intentarlo");	
				}
			}
		});
		
		txtLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InicioDatos.getCiudad() != null) {
					c = InicioDatos.getCiudad();
					choiceLugar.removeAll();
					Vector<Lugar> lugares = c.getLugares(txtLugar.getText());
					for(int i=0; i<lugares.size(); i++)
						choiceLugar.add(lugares.elementAt(i).getNombre());
					if(lugares.size()!=0) {
						labelLugar.setVisible(false);
						txtLugar.setVisible(false);
						labelOpLugar.setVisible(true);
						choiceLugar.setVisible(true);
						choiceLugar.setEnabled(true);
					}
				else 
					JOptionPane.showMessageDialog(null, "El lugar no existe, vuelva a intentarlo");
				}			
			}		
		});
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelOpCalle.setVisible(false);
				labelOpLugar.setVisible(false);
				txtLugar.setText("");
				txtLugar.setEditable(true);
				txtLugar.setVisible(true);
				choiceLugar.setEnabled(false);
				choiceLugar.setVisible(false);
				txtCalle.setText("");
				txtCalle.setEditable(true);
				txtCalle.setVisible(true);
				choiceCalle.setEnabled(false);
				choiceCalle.setVisible(false);
				txtInfo.setText("");
				labelCalle.setVisible(true);
				labelLugar.setVisible(true);	
			}
		});

		btnPto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtInfo.setText("");
				Calle calle = c.getCalle(choiceCalle.getSelectedItem());
				Lugar lugar = c.getLugar(choiceLugar.getSelectedItem());
				if(calle != null && lugar != null) {
					VerticeDistancia v = calle.getPtoDistMasCercano(lugar);
					txtInfo.append("El vertice mas cercano es: " + v.getVertice().getLatitud() + "," + v.getVertice().getLongitud() + "\nSe encuentra a una distancia de: " + v.getDistancia() + "mts");
				}
			}
		});
	}
}