

# Ejercicio de Arquitectura
Se presenta la primera parte, donde el propósito es contruir un web services para realizar

- Listado con capacidad de paginación, filtrado y ordenamiento
- Agregar, editar y borrar
- Autorización utilizando JWT (Sessionless)

# Backend

- El backend funciona utiliza Vertx
- Es reactivo y no bloquea las operacions I/O (Base de datos, acceso a archivos, acceso a web services)
- Listo para funcionar en cluster
 - Listo para funcionar en nube
 - Listo para funcionar Docker y con Docker-Composer balanceado con NGINX
 - Backend demo hospedado en DigitalOcean
 - Utiliza como base de datos postgres
 - Tiene EventBus para intercomunicacion entre servicios e instancias funcionando en el cluster
 - Api para webservices. Para cada uno de las entidades consideradas existe un "end point" estandarizado para listar con paginado translate e integrados parametros de consulta y filtrado por los campos de las entidades, crear, actualizar y borrar.


Para obtener el token ejecutar de autorizacion 
```
curl 'https://lonpe.com.mx/login' -X POST -H 'Content-Type: application/json' --data-raw '{"username":"admin","password":"1234"}'

```
Lo cual deb de devolver el token de autorización

Para generar un Alumno

```
curl 'https://lonpe.com.mx/crud/alumno/s' -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Authorization: Bearer elToken' --data-raw '{"pname":"Jose","activo":1,"primer_apellido":"Lopez","segundo_apellido":"Peres","pkey":"clavedeJose"}'

```

Para generar una Materia

```
curl 'https://lonpe.com.mx/crud/materia/s' -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Authorization: Bearer elToken' --data-raw '{"pkey":"Mat101","pname":"Matemáticas 1"}'

```

Para generar una Calificación

```
curl 'https://lonpe.com.mx/crud/calificacion/s' -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Authorization: Bearer elToken' --data-raw '{"pkey":"clave_calificacion","alumno_id":8 , "materia_id":9, "calificacion":8, "fecha":"2022-11-16"}'

```

Para listar las Calificaciones para el alumno con id = 1
```
curl 'https://lonpe.com.mx/crud/calificacion/l?max=8&offset=0&withCount=1&alumno_id=1' -H 'Authorization: Bearer elToken'
```

Para obtener las estadisticas el la calificacion del alumno con id = 1
```
curl 'https://lonpe.com.mx/crud/calificacion/lztat?alumno_id=1' -H 'Authorization: Bearer elToken'
```
```
Para borrar la calificación con id =1
curl 'https://lonpe.com.mx/crud/calificacion/delete?id=1' -X DELETE -H 'Authorization: Bearer elToken' 
```




# Backend for basic crud application


Este es un backend con servicios rest. 

Esta aplicación son los servicios web generados para crear, listar, buscar, filtrar, cargar y descargar desde excel 
 

Con vertx, se tiene un a infraestructura lista para cluster, y con la base de datos en memoria
de Hazelcast

Los pojos y servicios se generan desde un script de generación de código.

To create the database:
```
createdb <dbaseName>
```

To populate the database
```
psql  <dbaseName> < initSql.sql
```

To package your application:
```
./gradlew clean assemble
```

To run your application:
```
./gradlew clean run
```

##Running with Docker Composer

Descargar docker compose
bash 
```
sudo curl -L --fail https://github.com/docker/compose/releases/download/1.29.2/run.sh -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```
view https://docs.docker.com/compose/install/


### Creating image
docker build -t vxlon .

### Running 

bash 
```
docker-compose up --scale vxlon=2

```
