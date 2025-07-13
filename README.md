***Microservicios: API Clientes - API Productos Financieros - API Backend For Frontend (BFF)***

**Descripción:**

    El sistema está compuesto por tres microservicios independientes: 
    API de Clientes / API de Productos Financieros / API Backend For Frontend (BFF)

    Cada microservicio implementa su propio CRUD y mantiene una base de datos independiente.
    El microservicio BFF se encarga de orquestar las llamadas hacia los servicios de clientes 
    y productos financieros, combinando 
    la información en estructuras tipo DTO para exponerla al frontend de manera eficiente y
    desacoplada.

    Los mapeos entre entidades y DTOs fueron implementados manualmente, sin uso de librerías externas
    como MapStruct o ModelMapper.

**Tecnologías Usadas**

    Java 17
     Lenguaje de programación orientado a objetos, robusto y ampliamente utilizado en el desarrollo empresarial por su seguridad.

    Spring Boot
    Framework para crear aplicaciones backend de forma rápida y con configuración mínima.

    Spring Data JPA
    Módulo de Spring Framework que funciona como una abstracción sobre JPA para facilitar la persistencia 
    de datos.

    Spring Webflux
    Programación reactiva y asincrónica para mejorar el rendimiento en entornos de alta concurrencia. Además de comunicación
    entre los microservicios con la clase Webclient.

    PostgreSQL
    Base de datos relacional ideal para aplicaciones empresariales.

    Lombok
    Elimina código repetitivo como getters, setters, builders, etc., mediante anotaciones.

    Spring Validation
    Validación declarativa de campos en DTOs usando anotaciones.

    Swagger / OpenAPI
    Documentación interactiva de endpoints REST directamente desde el código.

    SLF4J + Logback
    Logging flexible y personalizable para auditoría y depuración.

 **Buenas prácticas**

    Uso de archivos .yml para centralizar configuraciones como puertos, credenciales de base de datos, etc.

    Inyección de dependencias a través de constructores utilizando Lombok (@RequiredArgsConstructor) 
    para evitar acoplamiento directo con el framework y facilitar las futuras pruebas unitarias.

    Manejo centralizado de excepciones.

    Documentación automática y actualizada de los endpoints REST con Swagger / OpenAPI.

    Uso de DTOs para transferir datos entre cliente y servidor.

    Validación de datos de entrada en los DTOs utilizando anotaciones como @NotBlank, @Size, 
    @Valid, entre otras.

    Implementación de paginación en endpoints que devuelven listas límitadas de datos para 
    mejorar rendimiento y escalabilidad.

    Diagrama de arquitectura y flujo de comunicación entre microservicios para una mejor comprensión técnica.
    
    Arquitectura de capas bien definida para cada microservicio: controller, service, repository, dto, mapper, 
    exception, domain, mapper, criteria.

    Uso de nombres descriptivos para variables, constantes, métodos, clases, paquetes 
    e interfaces.

    Aplicación del principio de responsabilidad única (SRP) del conjunto SOLID para mantener 
    clases y métodos mantenibles.

    Mapeos limpios y desacoplados entre entidades y DTOs utilizando MapStruct.

    Logging estructurado con SLF4J y Logback para monitorear el flujo y los errores.

 **Diagrama**
  ![arquitectura-customer-products-bff](https://github.com/user-attachments/assets/1440148f-6aeb-4795-9372-606161f6bd9d)

