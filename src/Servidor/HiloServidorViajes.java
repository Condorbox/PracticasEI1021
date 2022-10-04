package Servidor;

import java.io.IOException;
import java.net.SocketException;

import Comun.MyStreamSocket;
import Gestor.GestorViajes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de viajes.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorViajes implements Runnable {


	private MyStreamSocket myDataSocket;
	private GestorViajes gestor;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 * @param	unGestor		gestor de viajes
	 */
	HiloServidorViajes(MyStreamSocket myDataSocket, GestorViajes unGestor) {
		// POR IMPLEMENTAR
	}

	/**
	 * Gestiona una sesion con un cliente	
	 */
	public void run( ) {
		String operacion = "0";
		boolean done = false;
	    // ...
		try {
			while (!done) {
				// Recibe una petición del cliente
				// Extrae la operación y sus parámetros

				switch (operacion) {
				case "0":
					// ...
					break;

				case "1": { // Consulta los viajes con un origen dado
					// ...
					break;
				} 
				case "2": { // Reserva una plaza en un viaje
					// ...

					break;
				}             
				case "3": { // Pone en venta un articulo
					// ...

					break;
				}
				case "4": { // Oferta un viaje
					// ...

					break;
				}
				case "5": { // Borra un viaje
					// ...
					break;
				}

				} // fin switch
			} // fin while   
		} // fin try
		catch (SocketException ex) {
			System.out.println("Capturada SocketException");
		}
		catch (IOException ex) {
			System.out.println("Capturada IOException");
		}
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch
	} //fin run

} //fin class 
