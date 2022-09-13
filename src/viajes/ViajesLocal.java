package viajes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLOutput;
import java.util.Scanner;


public class ViajesLocal {


	/**
	 * Muestra el menu de opciones y lee repetidamente de teclado hasta obtener una opcion valida
	 * @param teclado	stream para leer la opción elegida de teclado
	 * @return			opción elegida
	 */
	public static int menu(Scanner teclado) {
		int opcion;
		System.out.println("\n\n");
		System.out.println("=====================================================");
		System.out.println("============            MENU        =================");
		System.out.println("=====================================================");
		System.out.println("0. Salir");
		System.out.println("1. Consultar viajes con un origen dado");
		System.out.println("2. Reservar un viaje");
		System.out.println("3. Anular una reserva");
		System.out.println("4. Ofertar un viaje");
		System.out.println("5. Borrar un viaje");
		do {
			System.out.print("\nElige una opcion (0..5): ");
			opcion = teclado.nextInt();
		} while ( (opcion<0) || (opcion>5) );
		teclado.nextLine(); // Elimina retorno de carro del buffer de entrada
		return opcion;
	}


	/**
	 * Programa principal. Muestra el menú repetidamente y atiende las peticiones del cliente.
	 * 
	 * @param args	no se usan argumentos de entrada al programa principal
	 */
	public static void main(String[] args)  {

		Scanner teclado = new Scanner(System.in);

		// Crea un gestor de viajes
		GestorViajes gestor = new GestorViajes();

		System.out.print("Introduce tu codigo de cliente: ");
		String codcli = teclado.nextLine();

		int opcion; 
		do {
			opcion = menu(teclado);
			switch (opcion) {
			case 0: // Guardar los datos en el fichero y salir del programa

				gestor.guardaDatos();
				System.out.println("Se ha guardado los datos correctamente.");
				// POR IMPLEMENTAR

				break;

			case 1: { // Consultar viajes con un origen dado

				System.out.println("Introduzca el nombre del origen para buscar viajes: ");
				String origen = teclado.next();

				JSONArray array = gestor.consultaViajes(origen);

				if(array.isEmpty()){
					System.out.println("Lo sentimos, no hemos encontrado ningún viaje con dicho destino.");
				}
				else{
					System.out.println(array.toJSONString());
				}
				// POR IMPLEMENTAR

				break;
			}

			case 2: { // Reservar un viaje

				System.out.print("Introduzca el codigo del viaje a reservar: ");
				String codviaje = teclado.next();

				JSONObject reserva = gestor.reservaViaje(codviaje,codcli);

				if(reserva.isEmpty()){
					System.out.println("Lo sentimos esta reserva no esta disponible.");
				}
				else{
					System.out.println("Se ha realizado la reserva con exito: ");
					System.out.println(reserva.toJSONString());
				}
				// POR IMPLEMENTAR

				break;
			}

			case 3: { // Anular una reserva

				System.out.println("Introduzca el código del viaje a cancelar");
				String codviaje = teclado.next();

				JSONObject reserva = gestor.anulaReserva(codviaje,codcli);

				if(reserva.isEmpty()){
					System.out.println("Lo sentimos, pero no se ha anulado la reserva...");
				}
				else{
					System.out.println("Se ha efectuado la anulación de la reserva con éxito.");
					System.out.println(reserva.toJSONString());
				}
				// POR IMPLEMENTAR

				break;
			}

			case 4: { // Ofertar un viaje

				System.out.println("A continuación escriba en el siguiente formato los datos del viaje:");
				System.out.println("codcli,origen,destino,fecha,percio,numplazas");
				String output = teclado.next();
				Object[] vector = output.split(",");

				JSONObject viajeNuevo = gestor.ofertaViaje((String) vector[0], (String) vector[1],(String) vector[2],
																(String) vector[3],(long) vector[4],(long) vector[5]);

				System.out.println("Se ha creado la siguiente oferta: ");
				System.out.println(viajeNuevo.toJSONString());
				// POR IMPLEMENTAR

				break;
			}

			case 5: { // Borrar un viaje ofertado

				// POR IMPLEMENTAR

				System.out.println("Introduce el código del viaje:");
				String codviaje = teclado.next();

				JSONObject viajeBorrado = gestor.borraViaje(codviaje,codcli);

				if(viajeBorrado.isEmpty()){
					System.out.println("No se ha borrado dicho viaje.");
				}
				else{
					System.out.println("Se ha borrado el viaje con la siguiente información:");
					System.out.println(viajeBorrado.toJSONString());
				}

				break;
			}

			} // fin switch

		} while (opcion != 0);

	} // fin de main

} // fin class
