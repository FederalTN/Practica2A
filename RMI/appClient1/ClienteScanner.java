import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;

class ClienteScanner {

    private static final int PORT = 4002;

    static public void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Uso: ClienteScanner codigoCliente");
            return;
        }

        try {
            // Conexion con el servidor
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", PORT);
            ServicioChat srv = (ServicioChat) registry.lookup("Chat");

            ClienteImpl c = new ClienteImpl();

            srv.alta(c);

            String apodo = args[0];
            System.out.print("Inicializado el cliente: " + apodo + "\n");

            // Archivo de configuracion
            Properties prop = new Properties();
            FileInputStream configInputStream = new FileInputStream("config.properties");
            prop.load(configInputStream);
            configInputStream.close();

            // Obtener el archivo .log y ultimaLineaLeida (memoria) desde el archivo de configuración
            String path = prop.getProperty("path");
            String archivoName = prop.getProperty("archivoLog");
            String archivoLog = path + archivoName;
            long ultimaLineaLeida = Long.parseLong(prop.getProperty("ultimaLineaLeida"));

            // 5 segundos en milisegundos
            int tiempoEspera = 5000;
            leerArchivoLog(srv, c, apodo, archivoLog, tiempoEspera, ultimaLineaLeida, prop);

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

    private static void leerArchivoLog(ServicioChat srv, ClienteImpl cliente, String nombreCliente, String archivoLog, int tiempoEspera, long ultimaLineaLeida, Properties prop) throws RemoteException {
        while (true) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoLog))) {
                // Saltar las líneas previamente leídas usando la memoria del archivo configuracion
                for (long i = 0; i < ultimaLineaLeida; i++) {
                    br.readLine();
                }

                String linea;
                while ((linea = br.readLine()) != null) {
                    // Registro timestamp
                    long timestamp = System.currentTimeMillis() / 1000;
                    String mensajeRegistro = linea + "; " + timestamp + "; cliente" + nombreCliente;
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
                // Guardar la última línea leída en el archivo de configuración
                prop.setProperty("ultimaLineaLeida", String.valueOf(ultimaLineaLeida));
                FileOutputStream configOutputStream = new FileOutputStream("config.properties");
                prop.store(configOutputStream, null);
                configOutputStream.close();

                Thread.sleep(tiempoEspera);  // Esperar el tiempo especificado antes de la próxima lectura
            } catch (InterruptedException e) {
                System.err.println("Error al esperar el tiempo especificado.");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error al guardar la última línea leída en el archivo de configuración.");
                e.printStackTrace();
            }
        }
    }
}
