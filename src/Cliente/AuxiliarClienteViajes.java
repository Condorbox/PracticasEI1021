package Cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Comun.MyStreamSocket;

/**
 * Esta clase es un modulo que proporciona la logica de aplicacion
 * para el Cliente del sevicio de viajes usando sockets de tipo stream
 */

public class AuxiliarClienteViajes {

	private final MyStreamSocket mySocket; // Socket de datos para comunicarse con el servidor
	JSONParser parser;

	/**
	 * Construye un objeto auxiliar asociado a un cliente del servicio.
	 * Crea un socket para conectar con el servidor.
	 * @param	hostName	nombre de la maquina que ejecuta el servidor
	 * @param	portNum		numero de puerto asociado al servicio en el servidor
	 */
	AuxiliarClienteViajes(String hostName, String portNum) 
			throws SocketException, UnknownHostException, IOException {

		// IP del servidor
		InetAddress serverHost = InetAddress.getByName(hostName);
		// Puerto asociado al servicio en el servidor
		int serverPort = Integer.parseInt(portNum);
		//Instantiates a stream-mode socket and wait for a connection.
		this.mySocket = new MyStreamSocket(serverHost, serverPort);
		/**/  System.out.println("Hecha peticion de conexion");
		parser = new JSONParser();
	} // end constructor

	/**
	 * Devuelve los viajes ofertados desde un origen dado
	 * 
	 * @param origen	origen del viaje ofertado
	 * @return array JSON de viajes desde un origen. array vacio si no hay ninguno
	 */
	public JSONArray consultaViajes(String origen) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "1");
		peticion.put("origen",origen);

		mySocket.sendMessage(peticion.toJSONString());

		String respuesta = mySocket.receiveMessage();

		JSONArray jsonArray = null;
		JSONParser jsonParser = new JSONParser();
		Object object = jsonParser.parse(respuesta);
		jsonArray = (JSONArray) object;

		return jsonArray;
	} // end consultaViajes

	/**
	 * Un pasajero reserva un viaje
	 * 
	 * @param codviaje		codigo del viaje
	 * @param codcliente	codigo del pasajero
	 * @return	objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
	 */
	public JSONObject reservaViaje(String codviaje, String codcliente) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "2");
		peticion.put("codviaje",codviaje);
		peticion.put("codcli",codcliente);

		mySocket.sendMessage(peticion.toJSONString());

		String respuesta =  mySocket.receiveMessage();

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(respuesta);

		return jsonObject;

	} // end reservaViaje
	
	/**
	 * Un pasajero anula una reserva
	 * 
	 * @param codviaje		codigo del viaje
	 * @param codcliente	codigo del pasajero
	 * @return	objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
	 */
	public JSONObject anulaReserva(String codviaje, String codcliente) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "3");
		peticion.put("codviaje",codviaje);
		peticion.put("codcli",codcliente);

		mySocket.sendMessage(peticion.toJSONString());

		String respuesta =  mySocket.receiveMessage();

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(respuesta);

		return jsonObject;
	} // end anulaReserva

	/**
	 * Un cliente oferta un nuevo viaje
	 * 
	 * @param codprop	codigo del cliente que hace la oferta
	 * @param origen	origen del viaje
	 * @param destino	destino del viaje
	 * @param fecha		fecha del viaje en formato dd/mm/aaaa
	 * @param precio	precio por plaza
	 * @param numplazas	numero de plazas ofertadas
	 * @return	viaje ofertado en formatoJSON. Vacio si no se ha podido ofertar
	 */
	public JSONObject ofertaViaje(String codprop, String origen, String destino,
			String fecha, long precio, long numplazas) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "4");
		peticion.put("codprop",codprop);
		peticion.put("origen",origen);
		peticion.put("destino",destino);
		peticion.put("fecha",fecha);
		peticion.put("precio",precio);
		peticion.put("numplazas",numplazas);

		mySocket.sendMessage(peticion.toJSONString());
		String respuesta =  mySocket.receiveMessage();

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(respuesta);

		return jsonObject;
	} // end ofertaViaje

	/**
	 * Un cliente borra una oferta de viaje
	 * 
	 * @param codviaje		codigo del viaje
	 * @param codcliente	codigo del pasajero
	 * @return	objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
	 */
	public JSONObject borraViaje(String codviaje, String codcliente) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "5");
		peticion.put("codviaje",codviaje);
		peticion.put("codcli",codcliente);

		mySocket.sendMessage(peticion.toJSONString());
		String respuesta =  mySocket.receiveMessage();

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(respuesta);

		return jsonObject;
	} // end borraViaje

	/**
	 * Finaliza la conexion con el servidor
	 */
	public void cierraSesion( ) throws IOException, ParseException {
		JSONObject peticion = new JSONObject();
		peticion.put("operacion", "0");

		mySocket.sendMessage(peticion.toJSONString());
		String respuesta =  mySocket.receiveMessage();

	} // end done
} //end class
