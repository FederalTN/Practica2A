import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.*;

class ServicioChatImpl implements ServicioChat {
    private List<Cliente> l;
    private int numeroCorrelativo;

    ServicioChatImpl() throws RemoteException {
        l = new LinkedList<Cliente>();
        numeroCorrelativo = 0;
    }

    // Conexion con el cliente
    public void alta(Cliente c) throws RemoteException {
        l.add(c);
        int id = l.indexOf(c) + 1;
        // Fecha y hora de conexion
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
        String fechaFormateada = formatoFecha.format(fecha);
        String registro = "inicio de conexion;cliente" + id + ";" + fechaFormateada;
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
        // fecha y hora de desconexion
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
        String fechaFormateada = formatoFecha.format(fecha);
        String registro = "fin de conexion;cliente" + id + ";" + fechaFormateada;
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
        int id = l.indexOf(esc) + 1;
        // Distinciòn cliente
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
        String fechaFormateada = formatoFecha.format(fecha);
        String registroServidor = ";cliente" + id + ";" + fechaFormateada;
        // Acciòn Cliente
        String log = m + registroServidor;
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
        numeroCorrelativo++;
        // Enviar mensaje a los demás clientes
        for (Cliente c: l) {
            if (!c.equals(esc)) {
                c.notificacion(apodo, m);
            }
        }
    }
    // N* de chat actual (numero correlativo)
    public int numeroCorrelativoActual() throws RemoteException {
        return numeroCorrelativo;
    }
}