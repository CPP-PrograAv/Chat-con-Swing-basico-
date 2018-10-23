package base;

import java.net.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.*;

public class Cliente {
	
	

	public static void main(String[] args) {
		
		MarcoCliente mimarco = new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
	
class MarcoCliente extends JFrame{
	
	public MarcoCliente() {
		setBounds(600,300,280,350);
		LaminaMarcoCliente milamina = new LaminaMarcoCliente();
		add(milamina);
		setVisible(true);
	}
	
}

	
class LaminaMarcoCliente extends JPanel{
	
	private JTextField campo1,nick,ip;
	private JButton miboton;
	private JTextArea campochat;
	
	
	public LaminaMarcoCliente() {
		
		nick = new JTextField(5);//tamanio
		add(nick);
		JLabel texto = new JLabel("Cliente");
		add(texto);
		
		ip = new JTextField(8);
		add(ip);
		
		campochat = new JTextArea(12,20);//coordenadas
		add(campochat);
		
		campo1 = new JTextField(20);
		add(campo1);
		
		miboton = new JButton("Enviar");		
		EnviarTexto mievento = new EnviarTexto();
		miboton.addActionListener(mievento);//adicionarle el evento
		
		add(miboton);
		
	}
	
	
	private class EnviarTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			System.out.println(campo1.getText());
			
			try {
				
				Socket misocket= new Socket("192.168.1.43", 9999);
				
				DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());//indicar por donde va a circular, y lo hacemos con el socket creado
				
				flujoSalida.writeUTF(campo1.getText());  //que es lo que va a circular,escribe en el flujo lo que hay en el campo1, que circulara por el socket que se dirige al servidor.
				
				flujoSalida.close(); // cerrar el flujo de datos.
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
			
		}
		
		
		
	}
	
}
	

