package com.appdist.model;

import java.util.ArrayList;

/**
 * 
 */
public class BufferReducer {
    
    private int numReducer;
    private ArrayList<Tuple> tuplesList;

    public BufferReducer(int numReducer, ArrayList<Tuple> tuplesList) {
        this.numReducer = numReducer;
        this.tuplesList = tuplesList;
    }

    public int searchTupleInListOfTuples(Tuple tupla) {
        for (int i = 0; i < tuplesList.size(); i++) {
            String claveTpTmp = (String) tuplesList.get(i).getKey();
            if (claveTpTmp.compareTo((String) tupla.getKey()) == 0) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public void addTupleToListOfTuples(Tuple tupla) {
        int index = searchTupleInListOfTuples(tupla);
        if (index != -1) {
            Tuple tptmp = tuplesList.get(index);
            ArrayList<Object> lastTmp = (ArrayList<Object>) tptmp.getValue();
            lastTmp.add(tupla.getValue());
            tuplesList.set(index, new Tuple(tupla.getKey(), lastTmp));
        } else {
            ArrayList<Object> lstTmp = new ArrayList<>();
            lstTmp.add(tupla.getValue());
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
