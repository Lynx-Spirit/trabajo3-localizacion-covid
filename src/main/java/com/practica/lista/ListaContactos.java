package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;
import java.util.LinkedList;

public class ListaContactos {
	private LinkedList<NodoTemporal> listaNodos = new LinkedList<>();
	
	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas. 
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada 
	 * en un instante 
	 */
	public void insertarNodoTemporal (PosicionPersona p) {
		boolean encontrado;
		/**
		 * Busco la posición adecuada donde meter el nodo de la lista, excepto
		 * que esté en la lista. Entonces solo añadimos una coordenada.
		 */
		encontrado = buscar(p);
		/**
		 * No hemos encontrado ninguna posición temporal, así que
		 * metemos un nodo nuevo en la lista
		 */
		if(!encontrado) {
			insertarNodo(p);
		}

	}

	private boolean buscar(PosicionPersona p) {
		boolean encontrado = false, salir = false;
		NodoTemporal aux;
		int i = 0;
		while (i < tamanioLista() && !salir) {
			aux = listaNodos.get(i++);
			if(aux.getFecha().compareTo(p.getFechaPosicion())==0) {
				encontrado = true;
				salir = true;
				/**
				 * Insertamos en la lista de coordenadas
				 */
				NodoPosicion npActual = aux.getListaCoordenadas();
				NodoPosicion npAnt=null;
				boolean npEncontrado = false;
				while (npActual!=null && !npEncontrado) {
					if(npActual.getCoordenada().equals(p.getCoordenada())) {
						npEncontrado=true;
						npActual.setNumPersonas(npActual.getNumPersonas()+1);
					}else {
						npAnt = npActual;
						npActual = npActual.getSiguiente();
					}
				}
				if(!npEncontrado) {
					NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(),1, null);
					if(aux.getListaCoordenadas()==null)
						aux.setListaCoordenadas(npNuevo);
					else
						npAnt.setSiguiente(npNuevo);
				}
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())>0) {
				salir=true;
			}
		}
		return encontrado;
	}

	private void insertarNodo(PosicionPersona p) {
		NodoTemporal nuevo = new NodoTemporal();
		nuevo.setFecha(p.getFechaPosicion());
		NodoPosicion npActual = nuevo.getListaCoordenadas();
		NodoPosicion npAnt=null;
		boolean npEncontrado = false;
		while (npActual!=null && !npEncontrado) {
			if(npActual.getCoordenada().equals(p.getCoordenada())) {
				npEncontrado=true;
				npActual.setNumPersonas(npActual.getNumPersonas()+1);
			}else {
				npAnt = npActual;
				npActual = npActual.getSiguiente();
			}
		}
		if(!npEncontrado) {
			NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(),  1, null);
			if(nuevo.getListaCoordenadas()==null)
				nuevo.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
		listaNodos.add(nuevo);
	}
	
	public int personasEnCoordenadas () {
		NodoPosicion aux = this.listaNodos.getFirst().getListaCoordenadas();
		int cont = 0;
		while(aux != null) {
			cont += aux.getNumPersonas();
			aux=aux.getSiguiente();
		}
		return cont;
	}
	
	public int tamanioLista () {
		return listaNodos.size();
	}

	public String getPrimerNodo() {
		NodoTemporal aux = listaNodos.getFirst();
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		NodoTemporal aux;
		int cont = 0, i = 0;
		while (i < tamanioLista()) {
			aux = listaNodos.get(i++);
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while(nodo!=null) {
					cont = cont + nodo.getNumPersonas();
					nodo = nodo.getSiguiente();
				}
			}
		}
		return cont;
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		NodoTemporal aux;
		int cont = 0, i = 0;
		while (i < tamanioLista()) {
			aux = listaNodos.get(i++);
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while(nodo!=null) {
					cont = cont + 1;
					nodo = nodo.getSiguiente();
				}
			}
		}
		return cont;
	}

	@Override
	public String toString() {
		String cadena="";
		NodoTemporal aux;
		for(int cont= 0; cont<tamanioLista(); cont++) {
			aux = listaNodos.get(cont);
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
		}
		return cadena;
	}
}
