from flask import Flask, request, jsonify
import requests
from itertools import cycle
from dotenv import load_dotenv
import os
import json

app = Flask(__name__)
load_dotenv()

@app.route('/')
def hello():
    return '¡Hola mundo! ¡Buscador de retail distribuido a su disposición!'

# Especificar los endpoints de los esclavos
esclavos = os.getenv("ESCLAVOS").split(',')
categorias_esclavos = {esclavo: [] for esclavo in esclavos}
productos_esclavos = {esclavo: [] for esclavo in esclavos}

# Obtener la lista de categorías y productos del archivo JSON
with open('../database/database.json') as f:
    data = json.load(f)
    products = data['products']
    categorias = set([product['categoria'] for product in products])
    pnames = set([product['pname'] for product in products])

# Asignar categorías y productos a los esclavos mediante el algoritmo round-robin
esclavo_cycle = cycle(esclavos)
for categoria in categorias:
    esclavo = next(esclavo_cycle)
    categorias_esclavos[esclavo].append(categoria)


esclavo_cycle = cycle(esclavos)
for product in pnames:
    esclavo = next(esclavo_cycle)
    productos_esclavos[esclavo].append(product)

# La ruta "/query" activará la función getProductsQuery()
@app.route('/query', methods=['GET'])
def getProductsQuery():
    # Verificar si se está realizando una búsqueda por categorías o productos
    if 'categorias' in request.args or 'productos' in request.args:
        # Obtener las categorías o productos de los argumentos de la solicitud
        if 'categorias' in request.args:
            consultas = request.args['categorias']
            argDeseado = 'categorias'
            esclavos_consulta = categorias_esclavos
        else:
            consultas = request.args['productos']
            argDeseado = 'productos'
            esclavos_consulta = productos_esclavos

        # Dividir las consultas en una lista
        consultas_list = consultas.split()
        
        # Realizar la búsqueda en los esclavos correspondientes
        results = []
        for esclavo, consultas_esclavo in esclavos_consulta.items():
            # Filtrar las consultas del esclavo que coinciden con las consultas de la solicitud
            consultas_filtradas = [consulta for consulta in consultas_list if consulta in consultas_esclavo]

            if consultas_filtradas:
                # Realizar la búsqueda en el esclavo correspondiente
                url = f'{esclavo}/query'
                params = {argDeseado: ' '.join(consultas_filtradas)}
                respuesta = requests.get(url, params=params)

                if respuesta.status_code == 200:
                    results += respuesta.json().get('results', [])

        # Devolver los resultados
        return jsonify({"results": results})

    else:
        return "No se encontraron productos."