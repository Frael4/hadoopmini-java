package com.appdist.model;

import java.util.List;

public interface MyMap {
    
    /**
     * 
     * @param element
     * @param output
     */
    void map(Tuple element, List<Tuple> output);
}
