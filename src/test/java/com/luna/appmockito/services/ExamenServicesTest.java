package com.luna.appmockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.luna.appmockito.modelos.Datos;
import com.luna.appmockito.modelos.Examen;
import com.luna.appmockito.repository.ExamenRepository;
import com.luna.appmockito.repository.PreguntasRepository;
import com.luna.appmockito.repository.impl.ExamenRepositoryImpl;
import com.luna.appmockito.repository.impl.PreguntasRepositoryImpl;
import com.luna.appmockito.services.impl.ExamenServicesImpl;

/**
 * Clase de pruebas para {@link ExamenRepositoryImpl}
 * @author Hector
 *
 */
@ExtendWith(MockitoExtension.class)
public class ExamenServicesTest {
	
	
	@Mock
	ExamenRepositoryImpl examenRepository;
	
	@Mock
	PreguntasRepositoryImpl preguntasRepository;
	
	@InjectMocks
	ExamenServicesImpl examenServices;
	
	
	/**
	 * Variable captor
	 */
	@Captor
	ArgumentCaptor<Long> captor;
	
	
	@BeforeEach
	void setUp(){
		//Para crear el mock 
		/*examenRepository= mock(ExamenRepositoryOtro.class);
		preguntasRepository= mock(PreguntasRepository.class);
		examenServices= new ExamenServicesImpl(examenRepository,preguntasRepository);*/
		//Para habilitar las anotaciones de Mock
		//MockitoAnnotations.openMocks(this);
		
	}
	
	
	@Test
	void testFindByExamenPorNombre() {

		//Para interpretar que debe hacer cuando se invoque el metodo findAll
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		
		Optional<Examen> examen= examenServices.findExamenPorNombre("Geografia");
		
		assertTrue(examen.isPresent());
		assertEquals(4L, examen.get().getId());
		assertEquals("Geografia",examen.get().getExamen());
		
	}
	
	@Test
	void testFindByExamenPorNombreListaVacia() {
		
		List<Examen>  data= Collections.emptyList();
		//Para interpretar que debe hacer cuando se invoque el metodo findAll
		when(examenRepository.findAll()).thenReturn(data);
		
		Optional<Examen> examen= examenServices.findExamenPorNombre("Geografia");
		
		assertFalse(examen.isPresent());
		
	}
	
	
	@Test
	void testPreguntas() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		Examen examen=examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		assertEquals(5,examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("aritmetica"));
	}
	

	@Test
	void testPreguntaExamenVerificar() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		Examen examen=examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		assertEquals(5,examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("aritmetica"));
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
	}
	
	@Test
	void testNoExisteExamenVerificar() {
		
		when(examenRepository.findAll()).thenReturn(Collections.emptyList());
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		Examen examen=examenServices.findExamenPorNombreConPreguntas("Matematicas2");
		
		assertNull(examen);
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
	}
	
	
	@Test
	void guardarExamen() {
		//Given
		Examen newExamen=Datos.EXAMEN; 
		newExamen.setPreguntas(Datos.PREGUNTAS);
		when(examenRepository.guardar(Mockito.any(Examen.class))).then(new Answer<Examen>() {

			Long secuencia=8L;
			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {
				Examen examen = invocation.getArgument(0);
				examen.setId(secuencia++);
				return examen;
			}
			
			
			
		});
		
		//When
		Examen examen = examenServices.guardarExamen(newExamen);
		
		//Then
		assertNotNull(examen.getId());
		assertEquals(8L,examen.getId());
		assertEquals("Fisica",examen.getExamen());
		
		verify(examenRepository).guardar(Mockito.any(Examen.class));
		verify(preguntasRepository).guardarVarias(Mockito.anyList());
		
		
	}
	
	@Test
	void testManejoExcepciones() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.isNotNull())).thenThrow(IllegalArgumentException.class);
		
		Exception e=assertThrows(IllegalArgumentException.class, ()->{
			examenServices.findExamenPorNombreConPreguntas("Matematicas");
		});
		
		assertEquals(IllegalArgumentException.class,e.getClass());
		
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.isNotNull());
		
	}
	
	
	@Test
	void testArgumentMatches() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		verify(examenRepository).findAll();
		//verify(preguntasRepository).findPreguntasPorExamenId(Mockito.argThat(arg-> arg!=null && arg.equals(1L)));
		//verify(preguntasRepository).findPreguntasPorExamenId(Mockito.eq(1L));
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.argThat(arg-> arg!=null && arg>=1));
		
		
	}
	

	@Test
	void testArgumentMatches2() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.argThat(new MiArgsMatchers()));
		
		
	}
	
	
	@Test
	void testArgumentMatches3() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
		when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		verify(examenRepository).findAll();
		verify(preguntasRepository).findPreguntasPorExamenId(Mockito.argThat((a)->a!=null && a>0));
		
		
	}
	
	
	public static class MiArgsMatchers implements ArgumentMatcher<Long>{

		
		private Long argument;
		
		
		@Override
		public boolean matches(Long argument) {
			this.argument= argument;
			return argument!=null && argument>0;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Es para un mensaje personalizado de error que imprime el mockito en caso de que falle el test "
					+ ", debe ser un entero positivo: "+this.argument;
		}
		
	}
	
	
	@Test
	void testArgumentCaptor() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		//when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		//ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
		verify(preguntasRepository).findPreguntasPorExamenId(captor.capture());
		
		assertEquals(1L,captor.getValue());
		
		
	}
	
	
	@Test
	void testDoThrow() {
		Examen examen= Datos.EXAMEN;
		examen.setPreguntas(Datos.PREGUNTAS);
		//Para los metodos void
		doThrow(IllegalArgumentException.class).when(preguntasRepository).guardarVarias(Mockito.anyList());
		
		assertThrows(IllegalArgumentException.class, ()->{
			examenServices.guardarExamen(examen);
		});
		
	}
	
	@Test
	void testDoAnswer() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		//Parecido al thenReturn
		doAnswer(invocation->{
			Long id= invocation.getArgument(0);
			return id==1L? Datos.PREGUNTAS: Collections.emptyList();
		}).when(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
		
		Examen examen= examenServices.findExamenPorNombreConPreguntas("Matematicas");
		assertEquals(5,examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("geometria"));
		assertEquals(1L,examen.getId());
		assertEquals("Matematicas",examen.getExamen());
		
	}
	
	
	/**
	 * Invocando el metodo real
	 */
	@Test
	void testDoCallRealMethod() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		//when(preguntasRepository.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(Datos.PREGUNTAS);
		
		doCallRealMethod().when(preguntasRepository).findPreguntasPorExamenId(Mockito.anyLong());
		
		Examen examen=examenServices.findExamenPorNombreConPreguntas("Matematicas");
		assertEquals(1L,examen.getId());
		assertEquals("Matematicas",examen.getExamen());
	}
	
	
	/**
	 * Es como entrar al metodo real de la implementacion
	 */
	@Test
	void testSpy(){
		//El spy se puede utilizar el metodo real o poder utilizarlo como simulador(mock)
		ExamenRepository examenRepo= spy(ExamenRepositoryImpl.class);
		PreguntasRepository preguntasRepo= 	spy(PreguntasRepositoryImpl.class);
		ExamenServices examenSer= new ExamenServicesImpl(examenRepo,preguntasRepo);
		
		
		List<String> preguntas= Arrays.asList("geologia");
		//Esto lo hace un mock
		//when(preguntasRepo.findPreguntasPorExamenId(Mockito.anyLong())).thenReturn(preguntas);
		doReturn(preguntas).when(preguntasRepo).findPreguntasPorExamenId(Mockito.anyLong());
		
		
		Examen examen= examenSer.findExamenPorNombreConPreguntas("Matematicas");
		
		assertEquals(1L,examen.getId());
		assertEquals("Matematicas",examen.getExamen());
		assertEquals(1,examen.getPreguntas().size());
		
		assertTrue(examen.getPreguntas().contains("geologia"));
		
		verify(examenRepo).findAll();
		verify(preguntasRepo).findPreguntasPorExamenId(Mockito.anyLong());
	}
	
	@Test
	void testOrderInvocaciones(){
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		examenServices.findExamenPorNombreConPreguntas("Geografia");
		
		//Para verificar el orden de las invicaciones 
		InOrder inOrder= inOrder(preguntasRepository);
		inOrder.verify(preguntasRepository).findPreguntasPorExamenId(1L);
		inOrder.verify(preguntasRepository).findPreguntasPorExamenId(4L);
	}
	
	@Test
	void testOrderInvocaciones2(){
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		examenServices.findExamenPorNombreConPreguntas("Geografia");
		
		//Para verificar el orden de las invicaciones 
		InOrder inOrder= inOrder(examenRepository,preguntasRepository);
		inOrder.verify(examenRepository).findAll();
		inOrder.verify(preguntasRepository).findPreguntasPorExamenId(1L);
		inOrder.verify(examenRepository).findAll();
		inOrder.verify(preguntasRepository).findPreguntasPorExamenId(4L);
	}
	
	
	@Test
	void testNumeroDeInvocaciones() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		
		verify(preguntasRepository).findPreguntasPorExamenId(1L);
		//Verificar se manda a llamar una sola vez
		verify(preguntasRepository,times(1)).findPreguntasPorExamenId(1L);
		//Se manda a llamar al menos una vez
		verify(preguntasRepository,atLeast(1)).findPreguntasPorExamenId(1L);
		verify(preguntasRepository,atLeastOnce()).findPreguntasPorExamenId(1L);
		
		//Se manda a llamar a lo mucho una vez
		verify(preguntasRepository,atMost(1)).findPreguntasPorExamenId(1L);
		verify(preguntasRepository,atMostOnce()).findPreguntasPorExamenId(1L);
	}
	
	@Test
	void testNumeroDeInvocaciones2() {
		
		when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		
		//verify(preguntasRepository).findPreguntasPorExamenId(1L);
		//Verificar se manda a llamar el numero que se le aigne al times
		verify(preguntasRepository,times(2)).findPreguntasPorExamenId(1L);
		//Se manda a llamar al menos una vez
		verify(preguntasRepository,atLeast(2)).findPreguntasPorExamenId(1L);
		verify(preguntasRepository,atLeastOnce()).findPreguntasPorExamenId(1L);
		
		//Se manda a llamar a lo mucho una vez
		verify(preguntasRepository,atMost(20)).findPreguntasPorExamenId(1L);
		//verify(preguntasRepository,Mockito.atMostOnce()).findPreguntasPorExamenId(1L);
		
	}
	
	
	/**
	 * Test para verificar que nunca se va a invocar el metodo
	 */
	@Test
	void testNumeroInvocaciones3() {
		
		when(examenRepository.findAll()).thenReturn(Collections.emptyList());
		
		examenServices.findExamenPorNombreConPreguntas("Matematicas");
		
		verify(preguntasRepository,never()).findPreguntasPorExamenId(1L);
		verifyNoInteractions(preguntasRepository);
		
		
		
	}
	
	
	

}	




















