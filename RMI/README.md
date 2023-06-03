-----RMI LOG CENTRALIZADO-----

log guardado en -> appServer/logCentralizado.txt

---PRE-REQUISITO, INSTALAR DEPENDENCIAS ( Linux-Ubuntu )---

Java development kit -> sudo apt-get install default-jdk

--- CONFIGURACION DE CLIENTES ---

Los clientes tienen su configuracion en el archivo config.properties, el cual tendra el siguiente formato:

#Thu Jun 01 22:03:23 PDT 2023   / Momento en el que se realizo el ultimo scan del log

path=../../LOGS/                / Path del archivo log

archivoLog=log_x.log            / nombre del archivo log asignado

ultimaLineaLeida=0              / Memoria del cliente: mantiene constancia de la ultima linea
                                / que leyo en caso de que el cliente se caiga.


--- EJECUCION EN EL SISTEMA (Linux-Ubuntu ) ---

1) crear los .class de cada ejemplo creado en java, puede hacerlo ejecutando.

```
cd appServer
javac Cliente.java
javac ClienteImpl.java
javac ServicioChat.java
javac ServicioChatImpl.java
javac ServidorChat.java
```

cd ..

cd appClient1

javac ClienteScanner.java

```

cd ..

cd appClient2

javac ClienteScanner.java

```

cd ..

cd appClient3

javac ClienteScanner.java

```

(opcional en caso de que se escale los slaves)

cd ..

cd appClient4

javac ClienteScanner.java

```

2) copiar appServer/cliente.class, appServer/clienteImp.class y appServer/ServicioChat.class a appClient1, 2, 3 y 4


3) abrir 4 consolas

4) en consola(1) debe ejecutar el siguiente comando, el cual habilita la escucha del puerto 4002 (para este ejemplo) para RMI. Esto debe realizarlo en el servidor.

linux
```
 cd appServer
rmiregistry 4002
```
5) en consola(2) ejecutar

```
cd appServer
java ServidorChat
```

6) en la consola(3, 4 y 5) ejecutar el cliente del chat, ingresar tu apodo y luego enviar mensajes

```
cd appClient1
java ClienteScanner 1
```

cd appClient2
java ClienteScanner 2
```

cd appClient3
java ClienteScanner 3
````

Nota: Para añadir mas clientes, en el caso de que se añadio mas slaves en HTTP, se puede copiar y pegar una carpeta de cliente y configurar el archivo config con el path correspondiente al log del slave que se añadio.

IMPORTANTE!: debe haber igual cantidad de slaves de HTTP, como clientes de RMI.