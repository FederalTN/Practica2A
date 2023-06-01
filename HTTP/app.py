from flask import Flask, request, jsonify,json
import requests

app = Flask(__name__)

@app.route('/')
def hello():
    return 'Hola mundo!, buscador de retail distribuido a su disposiciòn!'

# Especificar los endpoints de los esclavos
esclavos = [
    'http://localhost:5002',
    'http://localhost:5003',
    'http://localhost:5004'
    
]
# Escalabilidad de esclavos:
# ,'http://localhost:5005'
# ,'http://localhost:5006'
# ,'http://localhost:5007'
# etc...

# El índice del esclavo que se usará para la próxima búsqueda
index_esclavo = 0

# La ruta "/query" activará la función getProductsQuery()
@app.route('/query', methods=['GET']) 
def getProductsQuery():
    global index_esclavo  # Declarar la variable global

    # Verifica si se esta realizando una busqueda por categoria o categoria
    if 'categorias' in request.args or 'productos' in request.args:
        # Si "categorias" o "productos" estan en los argumentos, se realizara la busqueda correspondiente
        if 'categorias' in request.args:
            consultas = request.args['categorias']
            argDeseado = 'categorias'
        else:
            consultas = request.args['productos']
            argDeseado = 'productos'

        # Se convierte la consulta en una lista
        consultas_list = consultas.split()

        # Distribuir los productos entre los esclavos usando el algoritmo round-robin
        resultados_esclavos = {esclavo: [] for esclavo in esclavos}
        for i, consulta in enumerate(consultas_list):
            # Obtener el esclavo que se usará para esta búsqueda
            esclavo = esclavos[index_esclavo]
            index_esclavo = (index_esclavo + 1) % len(esclavos)

            # Añadir el producto o categoria a la lista de busqueda en el esclavo correspondiente
            resultados_esclavos[esclavo].append(consulta)

        # Reset del indice (para mantener orden de las proximas busquedas)
        index_esclavo = 0

        # Realizar el query en cada esclavo de acuerdo a su lista de busqueda
        results = []
        for esclavo in esclavos:
            if len(resultados_esclavos[esclavo]) > 0:
                # Hacer la búsqueda en el esclavo correspondiente
                respuesta = requests.get(f'{esclavo}/query', params={argDeseado: ' '.join(resultados_esclavos[esclavo])})
                if respuesta.status_code == 200:
                    results += respuesta.json()['results']

        # Devolver los resultados
        return {"results": results}
    else:
        return "No se encontraron productos."
    