package com.luna.appmockito.services;

import java.util.Optional;

import com.luna.appmockito.modelos.Examen;

/**
 * Interfaz para el negocio de examen
 * @author Hector
 *
 */
public interface ExamenServices {
	
	
	/**
	 * Buscar examen por nombre	
	 * @param nombre
	 * @return
	 */
	Optional<Examen> findExamenPorNombre(String nombre);
	
	/**
	 * 
	 * @param nombre
	 * @return
	 */
	Examen findExamenPorNombreConPreguntas(String nombre);
	
	/**
	 * 
	 * @param examen
	 * @return
	 */
	Examen guardarExamen(Examen examen);
	

}
