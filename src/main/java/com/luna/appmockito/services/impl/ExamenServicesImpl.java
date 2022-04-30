package com.luna.appmockito.services.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.luna.appmockito.modelos.Examen;
import com.luna.appmockito.repository.ExamenRepository;
import com.luna.appmockito.repository.PreguntasRepository;
import com.luna.appmockito.services.ExamenServices;

/**
 * Implementacion de la interfaz {@link ExamenServices}
 * @author Hector
 *
 */
@Service
public class ExamenServicesImpl implements ExamenServices {

	
	private ExamenRepository examenRepository;
	
	
	private PreguntasRepository preguntasRepository;
	

	public ExamenServicesImpl(ExamenRepository examenRepository) {
		this.examenRepository = examenRepository;
	}

	public ExamenServicesImpl(ExamenRepository examenRepository, PreguntasRepository preguntasRepository) {
		this.examenRepository = examenRepository;
		this.preguntasRepository = preguntasRepository;
	}





	/*
	 * (non-Javadoc)
	 * @see com.luna.appmockito.services.ExamenServices#findExamenPorNombre(java.lang.String)
	 */
	@Override
	public Optional<Examen> findExamenPorNombre(String nombre) {
		return examenRepository.findAll().stream().filter(e-> e.getExamen().contains(nombre))
		.findFirst();
	}




	/*
	 * (non-Javadoc)
	 * @see com.luna.appmockito.services.ExamenServices#findExamenPorNombreConPreguntas(java.lang.String)
	 */
	@Override
	public Examen findExamenPorNombreConPreguntas(String nombre) {
		
		Optional<Examen> examenOptional= findExamenPorNombre(nombre);
		Examen examen=null;
		if(examenOptional.isPresent()) {
			examen= examenOptional.get();
			List<String> preguntas = preguntasRepository.findPreguntasPorExamenId(examen.getId());
			 preguntasRepository.findPreguntasPorExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}
		
		return examen;
	}

	/*
	 * (non-Javadoc)
	 * @see com.luna.appmockito.services.ExamenServices#guardarExamen(com.luna.appmockito.modelos.Examen)
	 */
	@Override
	public Examen guardarExamen(Examen examen) {
		
		if(!examen.getPreguntas().isEmpty()) {
			preguntasRepository.guardarVarias(examen.getPreguntas());
		}
		
		return examenRepository.guardar(examen);
	}
	
	

}

























