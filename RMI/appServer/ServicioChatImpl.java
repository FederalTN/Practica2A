import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.*;

class ServicioChatImpl implements ServicioChat {
    private List<Cliente> l;

    ServicioChatImpl() throws RemoteException {
        l = new LinkedList<Cliente>();
    }

    // Conexion con el cliente
    public void alta(Cliente c) throws RemoteException {
        l.add(c);
        int id = l.indexOf(c) + 1;
        // timestamp de conexion
        long timestamp = System.currentTimeMillis() / 1000;
        String registro = "inicio de conexion;cliente" + id + "; " + timestamp;
        System.out.println(registro);
        // Guardar registro en archivo
        FileWriter fw;
        try {
            fw = new FileWriter("log.txt", true); // true indica que se agregará al final del archivo
            fw.write(registro + "\n"); // agregar salto de línea para separar registros
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Desconexion con el cliente
    public void baja(Cliente c) throws RemoteException {
        int id = l.indexOf(c) + 1;
        l.remove(c);
        // timestamp de desconexion
        long timestamp = System.currentTimeMillis() / 1000;
        String registro = "fin de conexion;cliente" + id + ";" + timestamp;
        System.out.println(registro);
        // Guardar registro en archivo
        FileWriter fw;
        try {
            fw = new FileWriter("log.txt", true); // true indica que se agregará al final del archivo
            fw.write(registro + "\n"); // agregar salto de línea para separar registros
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void envio(Cliente esc, String apodo, String m) throws RemoteException {
        // Timestamp recepcion
        long timestamp = System.currentTimeMillis() / 1000;

        // Acciòn Cliente
        String log = m + "; " + timestamp;
        System.out.println(log);
        // Guardar registro en archivo
        FileWriter fw;
        try {
            fw = new FileWriter("log.txt", true); // true indica que se agregará al final del archivo
            fw.write(log + "\n"); // agregar salto de línea para separar registros
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}