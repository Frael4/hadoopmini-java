package com.appdist.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import java.util.ArrayList;

/**
 * Se encarga de manejar los archivos de entrada y salida
 */
public class FileHandler {

    private String inputFile;
    private String outputFile;

    /**
     * Constructor
     * @param inputFile nombre archivo entrada
     * @param outputFile nombre archivo salida (resultado)
     */
    public FileHandler(String inputFile, String outputFile) {
        this.inputFile = "./src/main/java/com/appdist/data/" + inputFile;
        this.outputFile = "./src/main/java/com/appdist/data/results/" + outputFile;
    }

    /**
     * Genera la lectura del archivo de entrada
     * y lo almacena en una varible StringBuilder
     */
    public ArrayList<Tuple> generateBufferMaps(int nodos) {

        Logger.getAnonymousLogger().log(Level.INFO,"Examinando archivo");
        //Lista de almacenara el contenido
        ArrayList<Tuple> listaTuples = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try {
            File file = new File(inputFile);

            //Si el archivo existe
            if (file.exists()) {
                //Lo leemos
                try (BufferedReader input = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    //Almacenamos cada linea en line y mientras no sea null
                    while ((line = input.readLine()) != null) {
                        //Agregamos la linea al String Builder con un espacio
                        sb.append(line.trim()).append(" ");
                    }
                }

                //Obtenemos el modulo entre el hashCode del SB y el numero de nodos
                int hash = sb.hashCode() % nodos;
                //Add una nueva Tupla con el hash como clave y el StringBuilder - (Todo el contenido del archivo)
                listaTuples.add(new Tuple(hash, sb.toString()));

            } else {
                Logger.getAnonymousLogger().log(Level.INFO, "Archivo no existe");
            }
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error en : " + ex.getMessage());
        }

        return listaTuples;
    }

    /**
     * Guarda los resultados de la lista de Tuplas
     * @param resultado
     */
    public void saveResults(ArrayList<Tuple> resultado) {

        try {
            File file = new File(outputFile);
            //Si existe archivo eliminamos
            if (file.exists()) {
                file.delete();
            }
            //Abrimos el archivo en modo escritura
            FileWriter myWriter = new FileWriter(outputFile);

            for (Tuple tupla : resultado) {
                myWriter.write(tupla.getKey() + " " + tupla.getValue() + "\n");
            }
            //Cerramos el archivo
            myWriter.close();

            JOptionPane.showMessageDialog(null, "Archivo creado exitosamente.");

        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"Ocurrió un error al escribir el archivo." + e.getMessage());
        }
    }

}
