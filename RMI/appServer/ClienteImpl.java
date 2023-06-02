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
}

