package Cliente;

import Comun.IntCallbackCliente;
import Comun.IntServidorViajes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.Naming;
import java.util.Scanner;

public class ClienteViajesRMI {
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
        System.out.println("6. Subscribirse a un origen");
        System.out.println("7. Eliminarse de un origen");
        do {
            System.out.print("\nElige una opcion (0..7): ");
            opcion = teclado.nextInt();
        } while ( (opcion<0) || (opcion>7) );
        teclado.nextLine(); // Elimina retorno de carro del buffer de entrada
        return opcion;
    }


    /**
     * Programa principal. Muestra el menú repetidamente y atiende las peticiones del cliente.
     *
     * @param args	no se usan argumentos de entrada al programa principal
     */
    public static void main(String[] args) {
        try {
            String hostName = "localhost";
            int RMIPortNum = 1099;
            String registryURL = "rmi://" + hostName + ":" + RMIPortNum + "/viajes";

            IntServidorViajes servidorViajes = (IntServidorViajes) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");

            IntCallbackCliente callbackCliente = new ImplCallbackCliente();

            Scanner teclado = new Scanner(System.in);

            System.out.print("Introduce tu codigo de cliente: ");
            String codcli = teclado.nextLine();
            int opcion;
            do {
                opcion = menu(teclado);
                switch (opcion) {

                    case 0 -> { // Guardar los datos en el fichero y salir del programa

                        servidorViajes.guardarDatos();
                        System.out.println("Se ha guardado los datos correctamente.");
                    }

                    case 1 -> { // Consultar viajes con un origen dado

                        System.out.println("Introduzca el nombre del origen para buscar viajes: ");
                        String origen = teclado.next();

                        JSONArray array = servidorViajes.consultaViajes(origen);

                        if (array.isEmpty()) {
                            System.out.println("Lo sentimos, no hemos encontrado ningún viaje con dicho origen.");
                        } else {
                            System.out.println(array.toJSONString());
                        }

                    }

                    case 2 -> { // Reservar un viaje

                        System.out.print("Introduzca el codigo del viaje a reservar: ");
                        String codviaje = teclado.next();

                        JSONObject reserva = servidorViajes.reservaViaje(codviaje, codcli);

                        if (reserva.isEmpty()) {
                            System.out.println("Lo sentimos esta reserva no esta disponible.");
                        } else {
                            System.out.println("Se ha realizado la reserva con exito: ");
                            System.out.println(reserva.toJSONString());
                        }

                    }

                    case 3 -> { // Anular una reserva

                        System.out.println("Introduzca el código del viaje a cancelar");
                        String codviaje = teclado.next();

                        JSONObject reserva = servidorViajes.anulaReserva(codviaje, codcli);

                        if (reserva.isEmpty()) {
                            System.out.println("Lo sentimos, pero no se ha anulado la reserva...");
                        } else {
                            System.out.println("Se ha efectuado la anulación de la reserva con éxito.");
                            System.out.println(reserva.toJSONString());
                        }

                    }

                    case 4 -> { // Ofertar un viaje
                        System.out.println("A continuación escriba en el siguiente formato los datos del viaje:");
                        System.out.println("origen,destino,fecha,precio,numplazas");
                        String output = teclado.next();
                        Object[] vector = output.split(",");

                        JSONObject viajeNuevo = servidorViajes.ofertaViaje(codcli, (String) vector[0], (String) vector[1],
                                (String) vector[2], Long.parseLong((String) vector[3]), Long.parseLong((String) vector[4]));

                        if (viajeNuevo.isEmpty()) {
                            System.out.println("Lo sentimos, ese viaje no es valido");
                        } else {
                            System.out.println("Se ha creado la siguiente oferta: ");
                            System.out.println(viajeNuevo.toJSONString());
                        }

                    }

                    case 5 -> { // Borrar un viaje ofertado

                        System.out.println("Introduce el código del viaje:");
                        String codviaje = teclado.next();

                        JSONObject viajeBorrado = servidorViajes.borraViaje(codviaje, codcli);

                        if (viajeBorrado.isEmpty()) {
                            System.out.println("No se ha borrado dicho viaje.");
                        } else {
                            System.out.println("Se ha borrado el viaje con la siguiente información:");
                            System.out.println(viajeBorrado.toJSONString());
                        }
                    }

                    case 6 -> { // Subscribirse
                        System.out.println("Introduce el origen donde deseas registrarte: ");
                        String origen = teclado.next();
                        boolean added = servidorViajes.registerForCallback(callbackCliente, origen);
                        if (added) {
                            System.out.println("\nResgistrado en " + origen);
                        } else {
                            System.out.println("\nYa estas registrado en " + origen);
                        }
                    }

                    case 7 -> { // Eliminarse
                        System.out.println("Introduce el origen donde deseas eliminarte: ");
                        String origen = teclado.next();
                        boolean removed = servidorViajes.unregisterForCallback(callbackCliente, origen);
                        if (removed) {
                            System.out.println("\nEliminado en " + origen);
                        } else {
                            System.out.println("\nNo estas registrado en " + origen);
                        }

                    }
                } // fin switch
            } while (opcion != 0);
        }catch (Exception e){
            System.out.println("Exception in client: " + e);
        }
        System.exit(0);
    }
}
