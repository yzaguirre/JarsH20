package fiusac.ia1.search;
import java.util.*;

import fiusac.ia1.jarras.Jarra;
import fiusac.ia1.jarras.JarraException;
public class Nodo{
	private Jarra jarra4L, jarra3L;
	private Nodo padre;
	/*
	* Este Constructor es utilizado unica y exclusivamente para el nodo inicial
	*/
	public Nodo(){
		jarra4L = new Jarra(4);
		jarra3L = new Jarra(3);
		padre = null;
	}
	/*
	* Este Constructor es utilizado para crear nodos sucesores
	* @param jarr4L Jarra de 4 Litros de capacidad
	* @param jarr3L Jarra de 3 Litros de capacidad
	*/
	public Nodo(Jarra jarra4L, Jarra jarra3L){
		this.jarra4L = jarra4L;
		this.jarra3L = jarra3L;
	}
	/*
	* Genera sucesores del Nodo actual
	* @return Lista de sucedores creados segun el orden de las reglas
	*/
	public List<Nodo> generarSucesores(){
		List<Nodo> sucesores = new ArrayList<>(6);
		Nodo n = null;
		for (Reglas regla:NoInformado.ordenReglas){
			try{
				//System.out.println("Testeando regla: " + regla);
				switch(regla){
					case LLENAR4:
						//llenar Jarra de 4L
						n = new Nodo(jarra4L.llenar(), jarra3L);//(4L, 3L)
						break;
					case LLENAR3:
						//llenar Jarra de 3L
						n = new Nodo(jarra4L, jarra3L.llenar());//(4L, 3L)
						break;
					case VACIAR4:
						//vaciar Jarra de 4L
						n = new Nodo(jarra4L.vaciar(), jarra3L);//(4L, 3L)
						break;
					case VACIAR3:
						//vacia Jarra de 3L
						n = new Nodo(jarra4L, jarra3L.vaciar());//(4L, 3L)
						break;
					case TRASLADO43:
						//traladar Jarra de 4L a Jarra de 3L
						List<Jarra> retorno = jarra4L.traslado(jarra3L);
						n = new Nodo(retorno.get(0), retorno.get(1));//(4L, 3L)
						break;
					case TRASLADO34:
						//traladar Jarra de 3L a Jarra de 4L
						retorno = jarra3L.traslado(jarra4L);
						n = new Nodo(retorno.get(1), retorno.get(0)); //(4L, 3L)
						break;
				}
				n.setPadre(this);
				sucesores.add(n);
			} catch(JarraException je){
				// System.out.println("Regla \"" + regla + "\" no aplicable para " + je.jarra + ". Estado: " + je.msg);
			}
			
		}
		return sucesores;
	}
	public boolean isObjetivo(){
		return jarra4L.getOcupacidad() == 2;
	}
	public Nodo getPadre(){
		return this.padre;
	}
	private void setPadre(Nodo padre){
		this.padre = padre;
	}
	@Override
	public String toString(){
		return String.format("Jarra 4L {%s} Jarra 3L {%s}", jarra4L, jarra3L);
	}
}