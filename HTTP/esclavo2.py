from flask import Flask, request, jsonify,json

app = Flask(__name__)

# Leer los productos del archivo JSON
# formato ejemplo: {"id": 1, "pname": "camisa", "price": 20, "categoria": "ropa"}
with open("database.json", "r") as file:
    products = json.load(file)["products"]

# Aquí se inicia la aplicación Flask
# El parámetro "port" especifica en qué puerto se ejecutará la aplicación
app.run(port=5003, debug=True)

@app.route('/')
def hello():
    return 'Hola, soy el esclavo 1!'

# La ruta "/query" activará la función getProductsQuery()
@app.route('/query', methods=['GET']) 
def getProductsQuery():
    # Verifica si se esta realizando una busqueda por categoria o categoria
    if 'categorias' in request.args or 'productos' in request.args:
        # Si "categorias" o "productos" estan en los argumentos, se realizara la busqueda correspondiente
        if 'categorias' in request.args:
            consultas = request.args['categorias']
            itemDeseado = 'categoria'
        else:
            consultas = request.args['productos']
            itemDeseado = 'pname'
        # Se convierte la consulta en una lista
        consultas_list = consultas.split()

        # Se realiza la búsqueda
        results = []
        for consulta in consultas_list:
            for item in products:
                if consulta.lower() in item[itemDeseado].lower():
                    results.append(item)
        
        # Devolver los resultados
        return {"results": results}
    else:
        return "No se encontro."