package com.ejemplo.controller;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.model.Persona;

import ch.qos.logback.classic.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/personas/")
public class PersonaController {
	private static final Logger Log = (Logger) LoggerFactory.getLogger(PersonaController.class);
	
	// Creando servicio Rest para Mostrar los datos de una instancia de la clase Persona
	@GetMapping("/mostrar")
	public Mono<Persona> mostrar(){
		return Mono.just(new Persona(1, "Paul Ramon"));
	}
	
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
	
	// Creando el mismo servicio aterior, aplicando el concepto de programación reactiva
	@GetMapping("/respuesta")
	public Mono<ResponseEntity<Flux<Persona>>> listarEntity(){
		List<Persona> personas = new ArrayList<>();
		personas.add(new Persona(1,"Paul"));
		personas.add(new Persona(2,"Ramon"));
		
		Flux<Persona> personasFlux =  Flux.fromIterable(personas);
		
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(personasFlux));
	}
	
	
	// Aplicando el concepto de operadores, en base a una acción se va arealizar el trabajo de los datos 
	// y me va a devolver un mensaj Http
	@DeleteMapping("/{modo}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("modo") Integer modo){
		return buscarPersona(modo)	// Comienza operador para devolver ña respuesta Http
				.flatMap(p -> {
					return eliminar(p)
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
				}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
	
	public Mono<Void> eliminar(Persona p){
		Log.info("Eliminando" + p.getIdPersona() + " - " + p.getNombre());
		return Mono.empty();
	}
	
	public Mono<Persona> buscarPersona(Integer modo){
		if(modo == 1)
			return Mono.just(new Persona(1, "Raul"));
		else
			return Mono.empty();
	}
	
}
