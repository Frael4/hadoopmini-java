package com.appdist.model;

import java.util.ArrayList;

/**
 * Realiza el particionamiento y ordenado de datos arrojados por los mappers
 */
public class BufferMap {
    
    private final ArrayList<Tuple> splittedList = new ArrayList<>();
    private final ArrayList<BufferReducer> sortedList = new ArrayList<>();

    public ArrayList<BufferReducer> getSortedList() {
        return sortedList;
    }

    public void splitBuffer(ArrayList<Tuple> lstTuples, int nodos) {
        for (Tuple tupla : lstTuples) {
            int nodoReducer = tupla.getKey().hashCode() % nodos;
            splittedList.add(new Tuple(nodoReducer, tupla));
        }
    }

    public void sortBuffer() {
        for (Tuple tupla : splittedList) {
            int clave1 = (int) tupla.getKey();
            int posicion = searchBufferReducer(clave1);
            Tuple tuplaDelaTuple = (Tuple) tupla.getValue();

            if (posicion != -1) {
                BufferReducer bfr = sortedList.get(posicion);
                bfr.addTupleToListOfTuples(tuplaDelaTuple);
                sortedList.set(posicion, bfr);
            } else {
                ArrayList<Object> tmpLstValores = new ArrayList<>();
                tmpLstValores.add(tuplaDelaTuple.getValue());
                ArrayList<Tuple> tmp = new ArrayList<>();
                tmp.add(new Tuple(tuplaDelaTuple.getKey(), tmpLstValores));
                sortedList.add(new BufferReducer(clave1, tmp));
            }
        }
    }

    public int searchBufferReducer(int reducer) {
        for (int i = 0; i < sortedList.size(); i++) {
            BufferReducer bufferReducer = sortedList.get(i);
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
