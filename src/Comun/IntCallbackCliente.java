package Comun;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntCallbackCliente extends Remote {
    void notificame(String message) throws RemoteException;
}
