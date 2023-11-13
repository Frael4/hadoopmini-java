package com.appdist.problemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_6 {

    private final static String enunciado = "6. Calcular la temperatura en superficie mínima y máxima.";

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            /**
             * @param element Tupla
             * @param output   ArrayList que permite agregar las Tuplas que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {

                List<Double> listNumeros = new ArrayList<>();
                String[] lines = element.getValue().toString().split(" ");
                //Para cada linea
                for (String line : lines) {
                    //Los seperamos por la coma
                    String[] lineData = line.split(",");
                    //Obtenemos la Tempertura
                    double temp = Double.parseDouble(lineData[8]);
                    //La agregamos a la lista
                    listNumeros.add(temp);

                }
                //Buscamos en las listas los valores minimos y maximos
                double min = Collections.min(listNumeros);
                double max = Collections.max(listNumeros);
                //Agregamos los valores a la salida
                output.add(new Tuple("min", min));
                output.add(new Tuple("max", max));
            }

        });

        tarea.setReduceFunction(new MyReduce() {

            /**
             * @param element Tuple
             * @param output  ArrayList que permite agregar las Tuples que queremos, en la
             *                cual será el resultado.
             */
            @Override
            public void reduce(Tuple element, List<Tuple> output) {

                String clave = (String) element.getKey();

                String valor = ": " + element.getValue().toString();//.replaceAll("[\\[\\]]", "");
                //Agrega la nueva tupla a la salida
                output.add(new Tuple(clave, valor));
            }

        });

        tarea.setOutputFile("Resultado_6.txt");
        tarea.setInputFile("JCMB_last31days.csv");        
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n"+ "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);        
        tarea.run();
    }
}
