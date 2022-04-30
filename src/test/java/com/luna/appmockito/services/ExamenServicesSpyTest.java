package com.luna.appmockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luna.appmockito.modelos.Datos;
import com.luna.appmockito.modelos.Examen;
import com.luna.appmockito.repository.impl.ExamenRepositoryImpl;
import com.luna.appmockito.repository.impl.PreguntasRepositoryImpl;
import com.luna.appmockito.services.impl.ExamenServicesImpl;

/**
 * Clase de pruebas para {@link ExamenRepositoryImpl}
 * @author Hector
 *
 */
@ExtendWith(MockitoExtension.class)
public class ExamenServicesSpyTest {
	
	
	@Spy
	ExamenRepositoryImpl examenRepository;
	
	@Spy
	PreguntasRepositoryImpl preguntasRepository;
	
	@InjectMocks
	ExamenServicesImpl examenServices;
	
	
	
	/**
	 * Es como entrar al metodo real de la implementacion
	 */
	@Test
	void testSpy(){
		//El spy se puede utilizar el metodo real o poder utilizarlo como simulador(mock)
//		ExamenRepository examenRepo= spy(ExamenRepositoryImpl.class);
//		PreguntasRepository preguntasRepo= 	spy(PreguntasRepositoryImpl.class);
//		ExamenServices examenSer= new ExamenServicesImpl(examenRepo,preguntasRepo);
		
		
		List<String> preguntas= Arrays.asList("geologia");
		//Esto lo hace un mock
		//when(preguntasRepo.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(preguntas);
		doReturn(preguntas).when(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
		
		
		Examen examen= examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		assertEquals(1L,examen.getId());
		assertEquals("Matematicas",examen.getExamen());
		assertEquals(1,examen.getPreguntas().size());
		
		assertTrue(examen.getPreguntas().contains("geologia"));
		
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
	}
	
	
	
	
	

}	




















