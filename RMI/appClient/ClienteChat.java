import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

            String apodo = args[0];
            System.out.print("Inicializado, cliente: " + apodo + "\n");
            String msg;
            String msgExit = "EXIT";
            
            // Ruta al archivo .log
            String archivoLog = "../../LOGS/log_1.log";  
            // 5 segundos en milisegundos
            int tiempoEspera = 5000;  
            leerArchivoLog(srv, c, apodo, archivoLog, tiempoEspera);

            // Avisa desconexión del cliente al servidor
            srv.baja(c);
            System.exit(0);
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en Cliente:");
            e.printStackTrace();
        }
    }

    private static void leerArchivoLog(ServicioChat srv, ClienteImpl cliente, String nombreCliente, String archivoLog, int tiempoEspera) throws RemoteException {
        long ultimaLineaLeida = 0;  // Variable para almacenar el número de la última línea leída

        while (true) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoLog))) {
                // Saltar las líneas previamente leídas
                for (long i = 0; i < ultimaLineaLeida; i++) {
                    br.readLine();
                }

                String linea;
                while ((linea = br.readLine()) != null) {
                    // Registro correlativo
                    int numeroCorrelativo = srv.numeroCorrelativoActual();
                    // Registro fecha acción
                    Date fecha = new Date();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
                    String fechaFormateada = formatoFecha.format(fecha);
                    String mensajeRegistro = linea + ";" + fechaFormateada + "; cliente" + nombreCliente;
                    // Envío de mensaje
                    srv.envio(cliente, nombreCliente, mensajeRegistro);
                    System.out.println(mensajeRegistro);

                    ultimaLineaLeida++;  // Actualizar el número de la última línea leída
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo " + archivoLog);
                e.printStackTrace();
            }

            try {
                Thread.sleep(tiempoEspera);  // Esperar el tiempo especificado antes de la próxima lectura
            } catch (InterruptedException e) {
                System.err.println("Error al esperar el tiempo especificado.");
                e.printStackTrace();
            }
        }
    }
}
