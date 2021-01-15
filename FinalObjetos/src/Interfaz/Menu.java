package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Menu extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu() {
		setTitle("Final Objetos");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 580);
	
		
		 //Creamos el conjunto de pestañas
        JTabbedPane pestañas=new JTabbedPane();
        
        //Creamos el panel y lo añadimos a las pestañas con un nombre
        JPanel panel1=new JPanel();
        pestañas.addTab("Cargar ciudad", panel1);
        panel1.setLayout(null);   
        InicioDatos cargarDatos = new InicioDatos();
        cargarDatos.setBounds(0, 0, 533, 530);
        panel1.add(cargarDatos);
 
        JPanel panel2=new JPanel();
        pestañas.addTab("Servicio 1", panel2);
        panel2.setLayout(null);
        Servicio1 servicio1 = new Servicio1();
        servicio1.setBounds(0, 0, 533, 530);
        panel2.add(servicio1);
 
        JPanel panel3=new JPanel();
        pestañas.addTab("Servicio 2", panel3);
        panel3.setLayout(null);       
        Servicio2 servicio2 = new Servicio2();
        servicio2.setBounds(0, 0, 533, 530);
        panel3.add(servicio2);
 
        JPanel panel4=new JPanel();
        pestañas.addTab("Servicio 3", panel4);
        panel4.setLayout(null);
        Servicio3 servicio3 = new Servicio3();
        servicio3.setBounds(0, 0, 533, 530);
        panel4.add(servicio3);
 
        getContentPane().add(pestañas);
		
	}
}