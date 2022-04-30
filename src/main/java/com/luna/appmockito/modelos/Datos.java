package com.luna.appmockito.modelos;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Hector
 *
 */
public class Datos {

	
	public static final List<Examen>  EXAMENES= Arrays.asList(new Examen(1L,"Matematicas"),new Examen(2L,"Español"),new Examen(3L,"Ciencias naturales"),new Examen(4L,"Geografia"));
	
	public static final List<Examen>  EXAMENES_ID_NULL= Arrays.asList(new Examen(null,"Matematicas"),new Examen(null,"Español"),new Examen(null,"Ciencias naturales"),new Examen(null,"Geografia"));
	
	public static final List<Examen>  EXAMENES_ID_NEGATIVOS= Arrays.asList(new Examen(-1L,"Matematicas"),new Examen(-2L,"Español"),new Examen(-3L,"Ciencias naturales"),new Examen(-4L,"Geografia"));
	
	public static final List<String>  PREGUNTAS= Arrays.asList("aritmetica","integrales","diferenciales","trigonometria","geometria");
	
	public static final Examen EXAMEN= new Examen(null,"Fisica");
	
	
}
