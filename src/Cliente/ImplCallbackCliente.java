package Cliente;

import Comun.IntCallbackCliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplCallbackCliente extends UnicastRemoteObject  implements IntCallbackCliente {
    protected ImplCallbackCliente() throws RemoteException {
        super();
    }

    @Override
    public void notificame(String message) throws RemoteException {
        System.out.println(message);
    }
}
