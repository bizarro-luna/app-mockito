package com.luna.appmockito.repository.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.luna.appmockito.modelos.Datos;
import com.luna.appmockito.modelos.Examen;
import com.luna.appmockito.repository.ExamenRepository;

public class ExamenRepositoryImpl implements ExamenRepository {

	@Override
	public List<Examen> findAll() {
		System.out.println("Entrando al metodo real de ExamenRepository findAll");
		try {
			TimeUnit.SECONDS.sleep(5L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Datos.EXAMENES;
	}

	@Override
	public Examen guardar(Examen examen) {
		System.out.println("Entrando al metodo real de ExamenRepository guardar");
		return Datos.EXAMEN;
	}

	

}
