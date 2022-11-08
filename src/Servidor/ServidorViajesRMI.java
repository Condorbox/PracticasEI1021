package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorViajesRMI {
    public static void main(String[] args) {
        try {
            System.out.println("By default, the RMIregistry port number is 1099.");
            int RMIPortNum = 1099;

            startRegistry(RMIPortNum);

            ImplServidorViajes exportedObj = new ImplServidorViajes();
            String registryURL = "rmi://localhost:" + RMIPortNum + "/viajes";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Server registered. Registry contains:");
            // list names currently in the registry
            listRegistry(registryURL);
            System.out.println("Viajes Server ready.");
        }// end try

        catch (Exception re) {
            System.out.println("Exception in ServidorViajesRMI.main: " + re);
        } // end catch
    } // end main
    private static void startRegistry(int RMIPortNum)
            throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();  // This call will throw an
            //exception if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    } // end startRegistry

    //This method lists the names registered with a Registry
    private static void listRegistry(String registryURL)
            throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (String name : names) System.out.println(name);
    } //end listRegistry
}
