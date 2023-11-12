package com.appdist.model;

import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Se encarga de realizar los procesos de Map y Reduce
 */
public class Task {

    private String inputFile;
    private String outputFile;
    private int node;

    private MyMap mapFunction;
    private MyReduce reduceFunction;
    private Object combinerFunction;

    public Task() {

        this.inputFile = "";
        this.outputFile = "";
        this.node = 0;
        this.mapFunction = null;
        this.reduceFunction = null;
        this.combinerFunction = "";
    }

    /**
     * Ejecuta la Tarea
     */
    public void run() {

        //Instanciamos el manejador de archivos con el archivo de entrada y salida
        FileHandler fileHandler = new FileHandler(this.inputFile, this.outputFile);
        //Instanciamos el buffer map
        BufferMap bufferMap = new BufferMap();
        ArrayList<Tuple> resultado = new ArrayList<>();

        List<Tuple> lstBuffers = fileHandler.generateBufferMaps(node);

        if (lstBuffers.isEmpty()) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "No se ha podido cargar el archivo");
            return;
        }

        System.out.println("Iniciando proceso de Map");
        long startTime = System.currentTimeMillis();

        for (Tuple tupla : lstBuffers) {
            ArrayList<Tuple> output = new ArrayList<>();
            mapFunction.map(tupla, output);
            bufferMap.splitBuffer(output, node);
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;

        System.out.println("El proceso de map tomo: " + duration + " segundos");

        System.out.println("Iniciando proceso de Ordenamiento");
        startTime = System.currentTimeMillis();

        bufferMap.sortBuffer();
        ArrayList<BufferReducer> sortedList = bufferMap.getSortedList();

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        System.out.println("El proceso de ordenamiento tomo: " + duration + " segundos");

        System.out.println("Iniciando proceso de Reduce");
        startTime = System.currentTimeMillis();

        for (BufferReducer bufferReducer : sortedList) {
            ArrayList<Tuple> lstTuplesReducer = bufferReducer.getTuplesList();

            for (Tuple tuplaReducer : lstTuplesReducer) {
                reduceFunction.reduce(tuplaReducer, resultado);
            }
        }

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        System.out.println("El proceso de reduce tomo: " + duration + " segundos");

        JOptionPane.showMessageDialog(null,"Los datos han sido guardados en: " + outputFile);

        fileHandler.saveResults(resultado);
    }

    // Getters and Setters
    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public MyMap getMapFunction() {
        return mapFunction;
    }

    public void setMapFunction(MyMap mapFunction) {
        this.mapFunction = mapFunction;
    }

    public MyReduce getReduceFunction() {
        return reduceFunction;
    }

    public void setReduceFunction(MyReduce reduceFunction) {
        this.reduceFunction = reduceFunction;
    }

    public Object getCombinerFunction() {
        return combinerFunction;
    }

    public void setCombinerFunction(Object combinerFunction) {
        this.combinerFunction = combinerFunction;
    }
}
