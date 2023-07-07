~~~~BUSCADOR DE RETAIL DISTRIBUIDO A LOGGER DE BUSQUEDAS A LOG CENTRALIZADO MEDIANTE RMI~~~~

LEER LOS ARCHIVOS README.md DE HTTP Y RMI PARA LA INSTALACION, CONFIGURACION DE LOS ARCHIVOS Y METODOS DE EJECUCION.

--ORDEN DE DESPLIEGUE--

1) ESCLAVOS DE HTTP
2) MASTER DE HTTP
3) RMI REGISTRY EN 4002
4) SERVIDOR RMI
5) CLIENTES RMI

---

Los esclavos del buscador de retail distribuidos del problema 1 de la prueba 1 guardan los logs de su busqueda en el path LOGS,
luego cada 5 segundos cada cliente escanea un archivo log asignado en config, marca su timestamp y lo envia al logCentralizado.

Formato de los log de busqueda:

timestamp(esclavo); nombre de función; inicio o fin según corresponda; timestamp(RMIclient); nombre cliente RMI; timestamp (logCentralizado)