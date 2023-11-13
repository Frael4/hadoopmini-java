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

    /**
     * 
     */
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

        //Generamos el Buffer Map/ Lectura del archivo de entrada
        List<Tuple> lstBuffers = fileHandler.generateBufferMaps(node);

        //Verficamos si el archivo de entrada es vacio
        if (lstBuffers.isEmpty()) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "No se ha podido cargar el archivo");
            return;
        }

        System.out.println("Iniciando proceso de Map");
        long startTime = System.currentTimeMillis();

        // Recorremos la lista de Buffers / la entrada
        for (Tuple tupla : lstBuffers) {
            //Creamos una lista de Tuplas -> Salida
            ArrayList<Tuple> output = new ArrayList<>();
            //Aplicamos la funcion map
            mapFunction.map(tupla, output);
            //Pasamos la salida para la division
            bufferMap.splitBuffer(output, node);
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;

        System.out.println("El proceso de map tomo: " + duration + " segundos");

        System.out.println("Iniciando proceso de Ordenamiento");
        startTime = System.currentTimeMillis();
        //Se ordena el resultado del proceso de mapeo utilizando el BufferMap.
        bufferMap.sortBuffer();
        //Se obtiene la lista ordenada de objetos BufferReducer (sortedList).
        ArrayList<BufferReducer> sortedList = bufferMap.getSortedList();

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        System.out.println("El proceso de ordenamiento tomo: " + duration + " segundos");

        System.out.println("Iniciando proceso de Reduce");
        startTime = System.currentTimeMillis();

        //Se itera sobre cada objeto BufferReducer en la lista ordenada.
        for (BufferReducer bufferReducer : sortedList) {
            //Para cada BufferReducer, se obtienen las tuplas asociadas.
            ArrayList<Tuple> lstTuplesReducer = bufferReducer.getTuplesList();
            //Se aplica la función de reducción (reduceFunction) a cada tupla, y los resultados se agregan a la lista resultado
            for (Tuple tuplaReducer : lstTuplesReducer) {
                reduceFunction.reduce(tuplaReducer, resultado);
            }
        }

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        System.out.println("El proceso de reduce tomo: " + duration + " segundos");

        //Guardamos los resultados
        fileHandler.saveResults(resultado);
        
        JOptionPane.showMessageDialog(null,"Los datos han sido guardados en: " + outputFile);
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
