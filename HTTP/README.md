~~~~BUSCADOR DE RETAIL DISTRIBUIDO~~~~

Instalacion en orden para las dependencias( LINUX-UBUNTU ):

Python   -> sudo apt-get update
            sudo apt-get install python3

pip      -> sudo apt-get install python3-pip

Flask    -> pip3 install flask

requests -> pip3 install requests


~~ORDEN DE EJECUCION~~

~EJECUTAR ESCLAVOS

export FLASK_APP=esclavo1.py
flask run --port=5002

export FLASK_APP=esclavo2.py
flask run --port=5003

export FLASK_APP=esclavo3.py
flask run --port=5004


~EJECUTAR ESCLAVOS EXTRA

export FLASK_APP=esclavo4.py
flask run --port=5005
.
.
.
export FLASK_APP=esclavo(n).py
flask run --port=500(n+1)

~EJECUTAR MAESTRO

export FLASK_APP=app
flask run -p 5001

si no se agrega el "-p 5001", por defecto corre en el puerto 5000

~CONSULTAS RELEVANTES

http://localhost:5001/query?productos=pantalon+camisa

http://localhost:5001/query?categorias=ropa+calzado

http://localhost:5001/query?categorias=ropa+calzado+mobiliaria+domestico+accesorios

http://localhost:5001/query?productos=pantalon+camisa+zapatos+sombrero+lenovo+hp+mueble+silla