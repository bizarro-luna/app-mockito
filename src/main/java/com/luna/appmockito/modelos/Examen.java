package com.luna.appmockito.modelos;

import java.util.ArrayList;
import java.util.List;

public class Examen {
	
	private Long id;
	
	private String examen;
	
	
	private List<String> preguntas;
	
	


	public Examen(Long id, String examen) {
		super();
		this.id = id;
		this.examen = examen;
		this.preguntas= new ArrayList<String>();
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the examen
	 */
	public String getExamen() {
		return examen;
	}


	/**
	 * @param examen the examen to set
	 */
	public void setExamen(String examen) {
		this.examen = examen;
	}


	/**
	 * @return the preguntas
	 */
	public List<String> getPreguntas() {
		return preguntas;
	}


	/**
	 * @param preguntas the preguntas to set
	 */
	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}
	
	
	

}
