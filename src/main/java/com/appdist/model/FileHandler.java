package com.appdist.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Se encarga de manejar los archivos de entrada y salida
 */
public class FileHandler {
    
    private String inputFile;
    private String outputFile;

    public FileHandler(String inputFile, String outputFile) {
        this.inputFile = "../data/" + inputFile;
        this.outputFile = "../data/results/" + outputFile;
    }

    public ArrayList<Tuple> generateBufferMaps(int nodos) {
        System.out.println("Examinando archivo");
        ArrayList<Tuple> listaTuples = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try {
            File file = new File(inputFile);

            if (file.exists()) {
                try (BufferedReader input = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        sb.append(line.trim()).append(" ");
                    }
                }

                int hash = sb.hashCode() % nodos;
                listaTuples.add(new Tuple(hash, sb.toString()));
            }
        } catch (IOException ex) {
           System.out.println("Error en : "+ ex.getMessage());
        }

        return listaTuples;
    }

    public void saveResults(ArrayList<Tuple> resultado) {
        try {
            File file = new File(outputFile);

            if (file.exists()) {
                file.delete();
            }
            FileWriter myWriter = new FileWriter(outputFile);

            for (Tuple tupla : resultado) {
                myWriter.write(tupla.getKey() + " " + tupla.getValue() + "\n");
            }
            myWriter.close();
            System.out.println("Archivo creado exitosamente.");

        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo." + e.getMessage());
        }
    }

}
