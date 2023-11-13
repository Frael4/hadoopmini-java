package com.appdist.model;

import java.util.ArrayList;

/**
 * Clase BufferMap
 * Realiza el particionamiento y ordenado de datos arrojados por los mappers
 */
public class BufferMap {
    
    //Almacenará las tuplas divididas durante el proceso Map.
    private final ArrayList<Tuple> splittedList = new ArrayList<>();
    //Almacenará los objetos BufferReducer ordenados.
    private final ArrayList<BufferReducer> sortedList = new ArrayList<>();

    public ArrayList<BufferReducer> getSortedList() {
        return sortedList;
    }

    /**
     * Divide la lista y lo almacena en la lista splittedList
     * basados en el numero de nodos y el hashCode
     * @param lstTuples 
     * @param nodos cantidad de nodos
     */
    public void splitBuffer(ArrayList<Tuple> lstTuples, int nodos) {
        //Recorremos la lista de Tuplas
        for (Tuple tupla : lstTuples) {
            //Creamos una clave del modulo entre el hashCode y el numero de nodos
            int nodoReducer = tupla.getKey().hashCode() % nodos;
            //Agregamos la nueva Tupla a splittedList
            splittedList.add(new Tuple(nodoReducer, tupla));
        }
    }

    /**
     * Ordena la lista divida (splittedList)
     * y almacena en la lista ordenada (sortedList)
     */
    public void sortBuffer() {

        //Recorremos la lista dividida
        for (Tuple tupla : splittedList) {

            //Obtenemos la clave
            int numReducer = (int) tupla.getKey();
            //Obtenemos la posicion del buffer
            int posicion = searchBufferReducer(numReducer);
            //Obtenemos el valor del Buffer como una nueva Tupla
            Tuple tuplaDelaTuple = (Tuple) tupla.getValue();

            //Si hay elemento entonces
            if (posicion != -1) {
                //Obtenemos nodo reducer de la lista ordenada
                BufferReducer bfr = sortedList.get(posicion);
                //Agregamos el buffer a la lista de tuplas del reducer
                bfr.addTupleToListOfTuples(tuplaDelaTuple);
                //Seteamos los nuevos valores de la Tupla en la lista de ordenados
                sortedList.set(posicion, bfr);

            } else {

                ArrayList<Object> tmpLstValores = new ArrayList<>();
                //Agregamos el valor en la lista temporal
                tmpLstValores.add(tuplaDelaTuple.getValue());
                //Se crea otra lista temporal tmp
                ArrayList<Tuple> tmp = new ArrayList<>();
                //Contiene una nueva tupla con la clave de la tupla original y la lista de valores temporal.
                tmp.add(new Tuple(tuplaDelaTuple.getKey(), tmpLstValores));
                //Se crea un nuevo objeto BufferReducer con la clave (numReducer) y la lista temporal tmp.
                //Este nuevo BufferReducer se agrega a la lista ordenada sortedList
                sortedList.add(new BufferReducer(numReducer, tmp));
            }
        }
    }

    /**
     * Busca el nodo reducer
     * si la lista esta vacia retorna -1
     * @param reducer
     * @return
     */
    public int searchBufferReducer(int reducer) {
        //Si la lista tiene elementos la iteramos
        for (int i = 0; i < sortedList.size(); i++) {
            //Almacenamos los elementos el la lista ordenada
            BufferReducer bufferReducer = sortedList.get(i);
            //Si el numero del nodo reducer es igual al reducer retormanos
            if (bufferReducer.getNumReducer() == reducer) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Tuple> getSplittedList() {
        return splittedList;
    }
}
