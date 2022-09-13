package viajes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
					System.out.println(reserva.toString());
				}
				// POR IMPLEMENTAR

				break;
			}

			case 3: { // Anular una reserva

				// POR IMPLEMENTAR

				break;
			}

			case 4: { // Ofertar un viaje

				// POR IMPLEMENTAR

				break;
			}

			case 5: { // Borrar un viaje ofertado

				// POR IMPLEMENTAR

				break;
			}

			} // fin switch

		} while (opcion != 0);

	} // fin de main

} // fin class
