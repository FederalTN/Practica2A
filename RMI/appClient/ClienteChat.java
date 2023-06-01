import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;
import java.text.SimpleDateFormat;

class ClienteChat {

    private static final int PORT = 4002;

    static public void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Uso: ClienteChat apodo");
            return;
        }

        try {
            // Conexion con el servidor
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", PORT);
            ServicioChat srv = (ServicioChat) registry.lookup("Chat");

            ClienteImpl c = new ClienteImpl();

            srv.alta(c);

            Scanner ent = new Scanner(System.in);

            String apodo = args[0];
            System.out.print(apodo + "! te doy la bienvenida al chat!" + "\n" + "Escriba algo -> ");
            String msg;
            String msgExit = "EXIT";
            // Input del chat
            while (ent.hasNextLine()) {
                msg = ent.nextLine();
                // Desconexión del cliente
                if (msg.equals(msgExit)) {
                    break;
                } else {
                    // Registro correlativo
                    int numeroCorrelativo = srv.numeroCorrelativoActual();
                    // Registro fecha acciòn
                    Date fecha = new Date();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
                    String fechaFormateada = formatoFecha.format(fecha);
                    String MensajeRegistro = numeroCorrelativo + ";" + fechaFormateada + ";" + apodo + ": " + msg;
                    // Envio de mensaje
                    srv.envio(c, apodo, MensajeRegistro);
                    System.out.println(MensajeRegistro);
                }
            }
            // Avisa desconexión del cliente al servidor
            srv.baja(c);
            System.exit(0);
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en ClienteChat:");
            e.printStackTrace();
        }
    }
}
