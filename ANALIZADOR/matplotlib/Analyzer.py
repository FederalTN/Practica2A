import matplotlib.pyplot as plt
import re

archivo = '../../RMI/appServer/logCentralizado.txt'

pruebas_categorias = []
pruebas_productos = []
indice_categorias = 0
indice_productos = 0
conteo = 0

with open(archivo, 'r') as f:
    for linea in f:
        if not linea.startswith('inicio de conexion'):
            conteo += 1
            if (conteo == 1):
                timestamps = re.findall(r'; (\d+)', linea)
                primer_timestamp = int(timestamps[0])
            elif (conteo == 6):
                timestamps = re.findall(r'; (\d+)', linea)
                ultimo_timestamp = int(timestamps[-1])

                conteo = 0

                if 'buscar por categorias' in linea:
                    indice_categorias += 1
                    pruebas_categorias.append((indice_categorias, ultimo_timestamp - primer_timestamp))
                elif 'buscar por productos' in linea:
                    indice_productos += 1
                    pruebas_productos.append((indice_productos, ultimo_timestamp - primer_timestamp))

print('Análisis de "buscar por categorías"')
print('timestamp:', pruebas_categorias)
print('---')

print('Análisis de "buscar por productos"')
print('timestamp:', pruebas_productos)
print('---')

# Graficar resultados para "buscar por categorías"
x_categorias = [tupla[0] for tupla in pruebas_categorias]
y_categorias = [tupla[1] for tupla in pruebas_categorias]

plt.plot(x_categorias, y_categorias, '-o')
plt.xlabel('Pruebas')
plt.ylabel('Tiempo (ms)')
plt.title('Gráfico de tiempo de pruebas - Buscar por categorías')
plt.text(0, 0.95, 'tamaños de database [100,500,1000,10000,100000,1000000,1500000]', transform=plt.gca().transAxes, fontsize=10, va='top')
plt.savefig('grafico_categorias.png')
plt.show()

# Graficar resultados para "buscar por productos"
x_productos = [tupla[0] for tupla in pruebas_productos]
y_productos = [tupla[1] for tupla in pruebas_productos]

plt.plot(x_productos, y_productos, '-o')
plt.xlabel('Pruebas')
plt.ylabel('Tiempo (ms)')
plt.title('Gráfico de tiempo de pruebas - Buscar por productos')
plt.text(0, 0.95, 'tamaños de database [100,500,1000,10000,100000,1000000,1500000]', transform=plt.gca().transAxes, fontsize=10, va='top')
plt.savefig('grafico_productos.png')
plt.show()
