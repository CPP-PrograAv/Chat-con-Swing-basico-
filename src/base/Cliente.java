package base;

import java.net.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

	
class LaminaMarcoCliente extends JPanel implements Runnable{
	
	private JTextField mensaje,nick,ip;
	private JButton miboton;
	private JTextArea campochat;
	
	
	public LaminaMarcoCliente() {
		
		nick = new JTextField(5);//tamanio
		add(nick); //aniado el nick al JPanel
		JLabel texto = new JLabel("Cliente");
		add(texto);
		
		ip = new JTextField(8);
		add(ip);
		
		campochat = new JTextArea(12,20);//coordenadas
		add(campochat);
		
		mensaje = new JTextField(20);
		add(mensaje);
		
		miboton = new JButton("Enviar");		
		EnviarTexto mievento = new EnviarTexto();
		miboton.addActionListener(mievento);//adicionarle el evento
		add(miboton);
		
		
		Thread hilo = new Thread(this);
		
		hilo.start();
		
	}
	
	
	private class EnviarTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.out.println(campo1.getText());
			
			//ahora enviare un objeto por la red.
			//serializaciaon: necesaria cuando queremos enviar un objeto por la red, consiste en decirle a una clase o los objetos pertenecientes a esta en una serie de bytes
			//hay que serializar la clase PaqueteEnvio
			
			try {
				
				Socket misocket= new Socket("192.168.1.43", 9999);
				
				PaqueteEnvio datos = new PaqueteEnvio();
				
				datos.setMensaje(mensaje.getText());
				datos.setIp(ip.getText());
				datos.setNick(nick.getText());
				//esta  vez necesito enviar un objeto por el flujo
				ObjectOutputStream flujoSalida = new ObjectOutputStream(misocket.getOutputStream());
				//enviar objects
				flujoSalida.writeObject(datos);
				
				
				
				misocket.close();
				
				
				//ahora necesito que el cliente escuche permanentemente, como tambien debe eviar, al hacer dos cosas a la vez, utilizo un hilo 
			
				
				/*DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());//indicar por donde va a circular, y lo hacemos con el socket creado
				
				//writeUTF solo para enviar texto
				flujoSalida.writeUTF(campo1.getText());  //que es lo que va a circular,escribe en el flujo lo que hay en el campo1, que circulara por el socket que se dirige al servidor.
				
				flujoSalida.close(); // cerrar el flujo de datos.
				*/
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


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket servidorCliente = new ServerSocket(9090);
			
			Socket cliente;
			
			PaqueteEnvio paqueteRecibido;
			
			while(true) { //ciclo infinito para que siempre escuche
				
				cliente = servidorCliente.accept(); //aceptar las conexiones
				//objeto de entrada, creo el flujo
				ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
				
				//atrapo lo que esta en el flujo
				paqueteRecibido =(PaqueteEnvio) flujoEntrada.readObject();
				
				//escribo el msj en el area de texto del cliente
				
				campochat.append(paqueteRecibido.getNick()+": " + paqueteRecibido.getMensaje()+"\n");
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());//que me de el msj de error en consola si este sucede
		}
		
		
	}
	
}

//como este paquete es el que envio, implemento el Serializable, para convertirse en una serie de bytes
//si no lo implementamos lanzaria un exception si tratamos de enviar el objeto.
class PaqueteEnvio implements Serializable{
	
	private String nick, ip, mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}
	

