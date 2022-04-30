package com.luna.appmockito.repository.impl;

import java.util.List;

import com.luna.appmockito.modelos.Datos;
import com.luna.appmockito.repository.PreguntasRepository;

public class PreguntasRepositoryImpl implements PreguntasRepository {

	@Override
	public List<String> findPreguntasPorExamenId(Long id) {
		System.out.println("Entrando al metodo real PreguntasRepository findPreguntasPorExamenId");
		return Datos.PREGUNTAS;
	}

	@Override
	public void guardarVarias(List<String> preguntas) {
		System.out.println("Entrando al metodo real PreguntasRepository  guardarVarias");
		
	}

}
