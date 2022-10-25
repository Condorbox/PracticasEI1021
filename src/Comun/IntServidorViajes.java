package Comun;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorViajes extends Remote {
    void guardarDatos() throws RemoteException;
    JSONArray consultaViajes(String origen) throws RemoteException;
    JSONObject reservaViaje(String codviaje, String codcli) throws RemoteException;
    JSONObject anulaReserva(String codviaje, String codcli) throws RemoteException;
    JSONObject ofertaViaje(String codcli, String origen, String destino, String fecha, long precio, long numplazas) throws RemoteException;
    JSONObject borraViaje(String codviaje, String codcli) throws RemoteException;

    boolean registerForCallback(IntCallbackCliente cliente, String origen) throws RemoteException;
    boolean unregisterForCallback(IntCallbackCliente cliente, String origen) throws RemoteException;
}
