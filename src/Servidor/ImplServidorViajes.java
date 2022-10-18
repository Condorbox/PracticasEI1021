package Servidor;

import Cliente.IntCallbackCliente;
import Comun.IntServidorViajes;
import Gestor.GestorViajes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
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
        return gestorViajes.ofertaViaje(codcli, origen, destino, fecha, precio, numplazas);
    }

    @Override
    public synchronized JSONObject borraViaje(String codviaje, String codcli) throws RemoteException {
        return gestorViajes.borraViaje(codviaje, codcli);
    }

    @Override
    public void registerForCallback(IntCallbackCliente cliente, String origen) throws RemoteException {
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
    public void unregisterForCallback(IntCallbackCliente cliente, String origen) throws RemoteException {
        origen = origen.toLowerCase();
        List<IntCallbackCliente> clientsForCallback = subscriptions.get(origen);
        if (clientsForCallback != null) {
            clientsForCallback.remove(cliente);
        }
    }
}
