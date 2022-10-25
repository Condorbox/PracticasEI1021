package Servidor;

import Comun.IntCallbackCliente;
import Comun.IntServidorViajes;
import Gestor.GestorViajes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ImplServidorViajes extends UnicastRemoteObject implements IntServidorViajes {
    GestorViajes gestorViajes;
    HashMap<String, List<IntCallbackCliente>> subscriptions;
    protected ImplServidorViajes() throws RemoteException {
        this.gestorViajes = new GestorViajes();
        this.subscriptions = new HashMap<>();
    }

    @Override
    public synchronized void guardarDatos() throws RemoteException {
        gestorViajes.guardaDatos();
    }

    @Override
    public synchronized JSONArray consultaViajes(String origen) throws RemoteException {
        return gestorViajes.consultaViajes(origen);
    }

    @Override
    public synchronized JSONObject reservaViaje(String codviaje, String codcli) throws RemoteException {
        return gestorViajes.reservaViaje(codviaje, codcli);
    }

    @Override
    public synchronized JSONObject anulaReserva(String codviaje, String codcli) throws RemoteException {
        return gestorViajes.anulaReserva(codviaje, codcli);
    }

    @Override
    public synchronized JSONObject ofertaViaje(String codcli, String origen, String destino, String fecha, long precio, long numplazas) throws RemoteException {
        JSONObject nuevoViaje = gestorViajes.ofertaViaje(codcli, origen, destino, fecha, precio, numplazas);
        if (!nuevoViaje.isEmpty())
            notificar(origen);
        return nuevoViaje;
    }

    @Override
    public synchronized JSONObject borraViaje(String codviaje, String codcli) throws RemoteException {
        return gestorViajes.borraViaje(codviaje, codcli);
    }

    @Override
    public synchronized void registerForCallback(IntCallbackCliente cliente, String origen) throws RemoteException {
        origen = origen.toLowerCase();
        if (!subscriptions.containsKey(origen)){
            List<IntCallbackCliente> clientsForCallback = new ArrayList<>();
            clientsForCallback.add(cliente);
            subscriptions.put(origen, clientsForCallback);
        } else {
            List<IntCallbackCliente> clientsForCallback = subscriptions.get(origen);
            if (!clientsForCallback.contains(cliente)) {
                clientsForCallback.add(cliente);
            }
        }
    }

    @Override
    public synchronized void unregisterForCallback(IntCallbackCliente cliente, String origen) throws RemoteException {
        origen = origen.toLowerCase();
        List<IntCallbackCliente> clientsForCallback = subscriptions.get(origen);
        if (clientsForCallback != null) {
            clientsForCallback.remove(cliente);
        }
    }

    private synchronized void notificar(String origen) throws RemoteException {
        String message = "Se ha creado un nuevo viaje con destino a " + origen;
        List<IntCallbackCliente> clientsForCallback = subscriptions.get(origen.toLowerCase());
        if (clientsForCallback != null) {
            Iterator<IntCallbackCliente> it = clientsForCallback.listIterator();
            while (it.hasNext()){
                IntCallbackCliente client = it.next();
                try {
                    client.notificame(message);
                } catch (RemoteException e) {
                    it.remove();
                }

            }
        }
    }
}
