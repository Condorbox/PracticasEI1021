package Servidor;

import java.io.IOException;
import java.net.SocketException;

import Comun.MyStreamSocket;
import Gestor.GestorViajes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
		this.myDataSocket = myDataSocket;
		this.gestor = unGestor;
	}

	/**
	 * Gestiona una sesion con un cliente	
	 */
	public void run( ) {
		String operacion = "0";
		boolean done = false;
		try {
			while (!done) {
				String peticion = myDataSocket.receiveMessage();
				JSONObject campos = toJSON(peticion);
				String res = "";
				operacion = (String) campos.get("operacion");
				switch (operacion) {
				case "0":
					gestor.guardaDatos();
					res = "Se ha guardado los datos correctamente.";
					done = true;
					break;

				case "1": { // Consulta los viajes con un origen dado
					JSONArray array = gestor.consultaViajes((String) campos.get("origen"));

					if (array.isEmpty()) {
						res = "Lo sentimos, no hemos encontrado ningún viaje con dicho origen.";
					} else {
						res = array.toJSONString();
					}

					break;
				} 
				case "2": { // Reserva una plaza en un viaje
					JSONObject reserva = gestor.reservaViaje((String) campos.get("codviaje"), (String) campos.get("codcli"));

					if (reserva.isEmpty()) {
						res = "Lo sentimos esta reserva no esta disponible.";
					} else {
						res = "Se ha realizado la reserva con exito: \n" + reserva.toJSONString();
					}

					break;
				}             
				case "3": { // Anular una reserva
					JSONObject reserva = gestor.anulaReserva((String) campos.get("codviaje"), (String) campos.get("codcli"));

					if (reserva.isEmpty()) {
						res = "Lo sentimos, pero no se ha anulado la reserva...";
					} else {
						res = "Se ha efectuado la anulación de la reserva con éxito.\n" + reserva.toJSONString();
					}

					break;
				}
				case "4": { // Oferta un viaje
					//String codcli, String origen, String destino, String fecha, long precio, long numplazas
					JSONObject viajeNuevo = gestor.ofertaViaje((String) campos.get("codcli"), (String) campos.get("origen"), (String) campos.get("destino"),
							(String) campos.get("fecha"), (long) campos.get("precio"), (long) campos.get("numplazas"));
					if (viajeNuevo.isEmpty()) {
						res = "Lo sentimos, ese viaje no es valido";
					} else {
						res = "Se ha creado la siguiente oferta: \n" + viajeNuevo.toJSONString();
					}

					break;
				}
				case "5": { // Borra un viaje
					JSONObject viajeBorrado = gestor.borraViaje((String) campos.get("codviaje"), (String) campos.get("codcli"));

					if (viajeBorrado.isEmpty()) {
						res = "No se ha borrado dicho viaje.";
					} else {
						res = "Se ha borrado el viaje con la siguiente información:\n" + viajeBorrado.toJSONString();
					}
					break;
				}
				} // fin switch
				myDataSocket.sendMessage(res);
				if (operacion.equals("0")){
					myDataSocket.close();
				}
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

	private JSONObject toJSON(String peticion) {
		try {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(peticion);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

} //fin class 