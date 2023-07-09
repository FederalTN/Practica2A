import matplotlib.pyplot as plt
import re

archivo = '../../RMI/appServer/logCentralizado.txt'

pruebas = []
indice = 0
conteo = 0
with open(archivo, 'r') as f:
    for linea in f:
        # Ignorar líneas de inicio de conexión de cliente
        if not linea.startswith('inicio de conexion'):
            conteo += 1
            if (conteo == 1):
                # Buscar los timestamps en la línea
                timestamps = re.findall(r'; (\d+)', linea)
                
                # Extraer el primer timestamp como entero
                primer_timestamp = int(timestamps[0])
            elif (conteo == 6):
                # Buscar los timestamps en la línea
                timestamps = re.findall(r'; (\d+)', linea)

                # Extraer el ultimo timestamp como entero
                ultimo_timestamp = int(timestamps[-1])

                # Se añaden al arreglo
                indice += 1
                pruebas.append((indice, ultimo_timestamp - primer_timestamp))

                conteo = 0
                # Hacer lo que necesites con los timestamps extraídos
                print('timestamp:', pruebas)
                print('---')
            
# ../../RMI/appServer/logCentralizado.txt

# Separa los valores de x (ID de pruebas) y y (tiempo en milisegundos)
x = [tupla[0] for tupla in pruebas]
y = [tupla[1] for tupla in pruebas]

# Crea el gráfico con los puntos juntos
plt.plot(x, y, '-o')

# Agrega etiquetas y título
plt.xlabel('ID de pruebas')
plt.ylabel('Tiempo (ms)')
plt.title('Gráfico de tiempo de pruebas')

# Se guarda la imagen
plt.savefig('grafico.png')

# Muestra el gráfico
plt.show()
