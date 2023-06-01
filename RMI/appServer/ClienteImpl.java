import java.rmi.*;
import java.rmi.server.*;

class ClienteImpl extends UnicastRemoteObject implements Cliente {
    private int id;
    // Constructor del server
    ClienteImpl(int id) throws RemoteException {
        this.id = id;
    }
    // Constructor del cliente
    ClienteImpl() throws RemoteException {
    }
    // Respuesta del server (notificacion)
    public void notificacion(String apodo, String m) throws RemoteException {
        System.out.println("\n" + m);
    }
    // Obtencion del id de cada cliente
    public int getId() throws RemoteException {
        return id;
    }
}

