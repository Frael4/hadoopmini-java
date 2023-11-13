package com.appdist.model;

import java.util.ArrayList;

/**
 * Clase BufferReducer
 * Encargado de realizar el seteo de los valores
 */
public class BufferReducer {
    
    private int numReducer;
    private ArrayList<Tuple> tuplesList;

    /**
     * 
     * @param numReducer
     * @param tuplesList
     */
    public BufferReducer(int numReducer, ArrayList<Tuple> tuplesList) {
        this.numReducer = numReducer;
        this.tuplesList = tuplesList;
    }

    /**
     * Busca una Tupla en la lista de Tuplas
     * @param tupla
     * @return indice si encuentra
     */
    public int searchTupleInListOfTuples(Tuple tupla) {

        //Recorremos la lista de tuplas
        for (int i = 0; i < tuplesList.size(); i++) {

            //Almacenamos la clave de la tupla
            String claveTpTmp = (String) tuplesList.get(i).getKey();
            //Si es igual al la clave de la tupla dada entonces devolvemos el indice
            if (claveTpTmp.compareTo((String) tupla.getKey()) == 0) {
                return i;
            }

        }
        return -1;
    }

    /**
     * Agrega una Tupla a la lista de Tuplas
     * @param tupla
     */
    @SuppressWarnings("unchecked")
    public void addTupleToListOfTuples(Tuple tupla) {

        //Primero buscamos la Tupla en la lista por si existe
        int index = searchTupleInListOfTuples(tupla);
        // Si existe, entonces la obtenemos
        if (index != -1) {
            
            //La obtenemos
            Tuple tptmp = tuplesList.get(index);
            //Obtenemos el valor, el cual es una lista de objetos.
            ArrayList<Object> lastTmp = (ArrayList<Object>) tptmp.getValue();
            //Agregamos el nuevo valor de la nueva en la Tupla encontrada
            lastTmp.add(tupla.getValue());
            //Seteamos la Tupla en la lista con los nuevos valores
            tuplesList.set(index, new Tuple(tupla.getKey(), lastTmp));

        } else { // Si no existe la agregamos
            // Creamos una lista de objetos
            ArrayList<Object> lstTmp = new ArrayList<>();
            //Agregamos el valor de la nueva Tupla a la lista de objetos
            lstTmp.add(tupla.getValue());
            //Creamos y agregamos una tupla con la llave de la nueva Tupla y la lista de objetos como valor
            tuplesList.add(new Tuple(tupla.getKey(), lstTmp));

        }
    }

    public int getNumReducer() {
        return numReducer;
    }

    public ArrayList<Tuple> getTuplesList() {
        return tuplesList;
    }
}
