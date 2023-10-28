package com.appdist.model;

import java.util.List;

public interface MyReduce {
    
    /**
     * 
     * @param element
     * @param output
     */
    void reduce(Tuple element, List<Tuple> output);
}
