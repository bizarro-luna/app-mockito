package com.luna.appmockito.repository;

import java.util.List;

public interface PreguntasRepository {
	
	List<String> findPreguntasPorExamenId(Long id);
	
	void guardarVarias(List<String> preguntas);

}
