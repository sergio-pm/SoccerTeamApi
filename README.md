# SoccerTeamApi
Esta es una api de equipos de futbol, que permite las operaciones de crear, buscar , modificar , eliminar ,listar y ordenar equipos, Con unos atributos determinados y aplicando unas reglas de validacion determinada, además cuenta con una documentacion trazas de log test de prueba y con un sistema de autenticación y autorización, todo ello usando Java 17 , Spring Boot y Maven.

Esquema de paquetes basado en api REST con servicio controlador y persistencia con una base de datos H2, también contará con carpetas de seguridad, documentación, exceptions, logs , sistema de validacion, dtos y mappers y los modelos.

Muy importante antes de poderla testear con postman revisar el apartado de seguridad para obtener los credenciales.

También es importante revisar el aplication properties en mi caso está configurado el puerto del servidor de Spring boot por defecto lo he dejado en 80808

y los parametros de la base de datos H2 en memoria , con su usuario y contraseña y su consola activa.

En la carpeta models como se ha solicitado contiene la entidad Equipo con los siguientes atributos @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Name("ID") private Long id; @NotNull @NotBlank @Name("Nombre") private String nombre; @Name("Ciudad") private String ciudad; @Name("Propietario") private String propietario; @Name("Capacidad del estadio") private int capacidadEstadio; @Name("División") private int division; @Name("Competición") private String competicion; @Name("Número de jugadores") private int numJugadores; @Name("Fecha de creación") private String fechaCreacion (está en formato yyyy-MM-dd ) ejemplo "2022-01-01" }

La aplicación valida estos campos. · Nombre (Obligatorio) · Capacidad del estadio (Positivo) · División (1, 2, 3) · Número de jugadores (Positivo) · Fecha de creación (Anterior a hoy)

            · si Division es 1 => Capacidad del estadio debe ser mayor de 50000
            · si Division es 2 => Capacidad del estadio debe ser mayor de 10000
            · si Division es 3 => Capacidad del estadio debe ser mayor de 3000
El sistema de reglas está alojado en la carpeta Validator y todas ellas estan testeadas en su correspondiente carpeta de test Validator.

He diseñado una exception llamada "ApiUnprocesable" que será remitido por la operación si se intenta crear o modificar un equipo violando las reglas solicitadas.

La url base del servicio es "/api/v1/equipos") , por ejemplo si lo ejecutas en localhost la url en mi caso

quedaría: http://localhost:8080/api/v1/equipos

El controlador permite realizar las siguientes operaciones:

crearEquipo es una operacion POST y esta alojada en /crear siguiendo el ejemplo http://localhost:8080/api/v1/equipos/crear

obtenerTodos es una operacion GET y esta alojada en /obtener/todos siguiendo el ejemplo http://localhost:8080/api/v1/equipos/obtener/todos

obtenerEquipo es una operacion GET y esta alojada en /obtener/{id} ya que necesita indicar el id ejemplo si quisieras obtener el 1 http://localhost:8080/api/v1/equipos/obtener/1

actualizarEquipo es una operacion PUT y esta alojada en /modificar/{id} ya que necesita indicar el id ejemplo si quisieras modificar el 1 http://localhost:8080/api/v1/equipos/modificar/1

eliminarEquipo es una operacion DELETE y esta alojada en /eliminar/{id} ya que necesita indicar el id ejemplo si quisieras modificar el 1 http://localhost:8080/api/v1/equipos/eliminar/1

obtenerTodosOrdenadosPorTamEstadio es una operacion GET y esta alojada en /obtener/todos/ordenados siguiendo el ejemplo http://localhost:8080/api/v1/equipos/obtener/todos/ordenados

He incluido un equipoDto que no contará con ID para el traspaso entre capas a servicio tras su mapeo se convertirá equipo y al insertar en la H2se le proporcionaID.

Todas estas operaciones se podrán visualizar ademas en los logs, definidos en la carpeta Logging incluyendo METHOD = {}; REQUESTURI= {}; REQUEST BODY ={}; RESPONSE CODE= {}; MENSAJE= {};" + "DATOS USUARIO= {}; DATOS PERMISOS= {}; AUTENTICADO= {};" ejemplo RESPONSE CODE= 200; MENSAJE= {"id":1,"nombre":"Esquivias","ciudad":"Esquivias","propietario":"Sergio","capacidadEstadio":7000,"division":3,"competicion":"futbol","numJugadores":23,"fechaCreacion":"2022-01-01"}; DATOS USUARIO= org.springframework.security.core.userdetails.User [Username=user, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_User]]; DATOS PERMISOS= [ROLE_User]; AUTENTICADO= true;

También para documentar mejor y visualizar la aplicacion se ha incluido swagger , está alojado en la carpeta SwaggerConfig y desde la url http://localhost:8080/swagger-ui.html#/ desde ahí puedes visualizar todas las operaciones del controlador asi como los modelos. y las entidades recuerda que previamente te pedirá usuario y contraseña.

Seguridad se ha incluido Basic Auth con dos roles diferentes user y admin. La password y la contraseña están definidas en WebSecurityConfig.

Proximos pasos añadir mas operaciones como filtrar por jugadores u obtener equipos y añadir atributos de presupuesto por ejemplo, asignar distintas operaciones a los roles y aplicarle la excepcion ApiUnhatorized incluir otra base de datos relacional o no y mas tablas, añadir token de seguridad, pintar logs en graficas y extracción de informes.
