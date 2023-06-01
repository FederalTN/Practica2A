from flask import Flask, request, jsonify, json
from datetime import datetime
import logging

app = Flask(__name__)

# Configurar el archivo de registro
log_filename = "log_file.log"  # Nombre del archivo de registro

logging.basicConfig(filename=log_filename, level=logging.INFO, format='%(asctime)s %(message)s', datefmt='%s;')


# Desactivar los registros de los GET requests en Flask
werkzeug_logger = logging.getLogger('werkzeug')
werkzeug_logger.setLevel(logging.WARNING)


# Leer los productos del archivo JSON
# formato ejemplo: {"id": 1, "pname": "camisa", "price": 20, "categoria": "ropa"}
with open("../database/database.json", "r") as file:
    products = json.load(file)["products"]

# Aquí se inicia la aplicación Flask
# El parámetro "port" especifica en qué puerto se ejecutará la aplicación
app.run(port=5002, debug=True)

@app.route('/')
def hello():
    return 'Hola, soy el esclavo 1!'

# La ruta "/query" activará la función getProductsQuery()
@app.route('/query', methods=['GET']) 
def getProductsQuery():
    
    # Verificar si se está realizando una búsqueda por categoría o producto
    if 'categorias' in request.args or 'productos' in request.args:
        # Si "categorias" o "productos" están en los argumentos, se realizará la búsqueda correspondiente
        if 'categorias' in request.args:
            consultas = request.args['categorias']
            itemDeseado = 'categoria'
        else:
            consultas = request.args['productos']
            itemDeseado = 'pname'
        # Registrar el inicio de la función de búsqueda en el archivo de registro
        log_message = f"buscar por {list(request.args.keys())[0]}; ini"
        logging.info(log_message)

        
        # Convertir la consulta en una lista
        consultas_list = consultas.split()

        # Realizar la búsqueda
        results = []
        for consulta in consultas_list:
            for item in products:
                if consulta.lower() in item[itemDeseado].lower():
                    results.append(item)
        
        # Registrar el fin de la función de búsqueda en el archivo de registro
        log_message = f"buscar por {list(request.args.keys())[0]}; fin"
        logging.info(log_message)

        # Devolver los resultados
        return {"results": results}
    else:
        return "No se encontró."