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


excel

https://docs.google.com/spreadsheets/d/1PV2GNYjxRvBUISbNzobZt0cxE3fzbOPv26CBV93WyR4/edit#gid=0

chatgpt

https://chat.openai.com/share/47384a47-2ff2-49e5-b717-4ca2debd6417

pptx

https://docs.google.com/presentation/d/1atJaqJfLb1QQOmbWB0JUmuxi0_-I8BxrcQy2Ffpgfn0/edit?usp=sharing

video riesgos

https://www.youtube.com/watch?v=qYWBhFbN-zs

ejercicio caso de uso

https://docs.google.com/document/d/1ukQwKOTW2MEvtoR2kw3DEyaAUljtlFiMtWDJVwoQXyA/edit

los kbros

https://docs.google.com/document/d/13fbaUQpOHVv4bbhuKazWeik-pLEk5yQuACHHFSU1sMA/edit?pli=1

desarrollo caso de uso licitacion

https://docs.google.com/document/d/1QuCikhXvDRhtNlyghx_JjpUQ4Uy25kWb5mnlxnqgB1s/edit?usp=sharing

excel editable

https://docs.google.com/spreadsheets/d/1EPgkZT-QxjTBY9BvK8NFBvRp49xWLsuGGUnfxiaWpkY/edit?usp=sharing
