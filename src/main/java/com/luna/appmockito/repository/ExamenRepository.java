package com.luna.appmockito.repository;

import java.util.List;


import com.luna.appmockito.modelos.Examen;

/**
 * Interfaz para la conexion a la base de datos de {@link Examen}
 * @author Hector
 *
 */
public interface ExamenRepository {
	
	/**
	 * Obtener todos los examenes
	 * @return
	 */
	List<Examen> findAll();
	
	
	Examen guardar(Examen examen);

}
