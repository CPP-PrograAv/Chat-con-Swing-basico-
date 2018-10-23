package base;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Servidor {

	//debe permancer a la escucha constantemente y tener el puerto abierto(se utiliza hilos)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
	
}

class MarcoServidor extends JFrame implements Runnable{
	
		private JTextArea areatexto;
		
		public MarcoServidor() {
			
			setBounds(820,300,280,350);
			setTitle("Servidor");
			JPanel milamina = new JPanel();
			milamina.setLayout(new BorderLayout());
			areatexto = new JTextArea();
			milamina.add(areatexto,BorderLayout.CENTER);
			add(milamina);
			
			setVisible(true);
			
			Thread mihilo =  new Thread(this);
			
			mihilo.start(); // ejecutar el hilo
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println("Estoy a la escucha");
			
			try {
				ServerSocket servidor =new ServerSocket(9999); //este a la escucha 
				
				while(true) {
				Socket misocket = servidor.accept(); //acepte las conexiones q les vengan del exterior
				
				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream()); //xq socket viaja la info de entrada
				
				String mensajeTexto = flujoEntrada.readUTF();
				
				areatexto.append( mensajeTexto +"\n");
				
				misocket.close();
				
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
}
