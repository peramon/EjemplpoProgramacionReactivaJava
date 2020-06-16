## Java con Spring Reactive
Este es un ejemplo de como usar la programación reactiva en java, el concepto de streams se maneja por medio de flux, esta enfocado a la creación y utilización de operadores, en donde dependiendo de cierta acción se va a realizar un procesamiento de datos y nos devolvera un nuevo conjunto de datos.

```
 src
│   └── main
│       ├── java
│       │   └── com
│       │       └── formacionbdi
│       │           └── springboot
│       │               └── app
│       │                   ├── ejemplo
|       |                       └── controller
│       │                   │       ├── PersonaController.java -> Rest y Programación reactiva con flux(streams)
│       │                   │   ├── models
│       │                   │       ├── Persona.java  -> Clase contiene objeto persona
│       └── resources 
│           └── application.properties
```
### Funciones dentro de la clase controller
Usando Flux para presentar una lista del objeto Persona

	// Creando servicio Rest para mostrar datos de una lista de Objetos Persona usando el concepto de Flux
	// Flux -> streams
	@GetMapping
	public Flux<Persona> listar(){
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1,"Paul"));
		personas.add(new Persona(2,"Ramon"));
		
		Flux<Persona> personasFlux =  Flux.fromIterable(personas);
		return personasFlux;
	}

 ### Aplicando el concepto de programación reactiva
 ```
	// Creando el mismo servicio aterior, aplicando el concepto de programación reactiva
	@GetMapping("/respuesta")
	public Mono<ResponseEntity<Flux<Persona>>> listarEntity(){
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1,"Paul"));
		personas.add(new Persona(2,"Ramon"));
		
		Flux<Persona> personasFlux =  Flux.fromIterable(personas);
		
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(personasFlux));
	}
```
