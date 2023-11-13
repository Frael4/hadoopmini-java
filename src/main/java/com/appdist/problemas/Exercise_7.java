package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_7 {

    private final static String enunciado = "7. Calcular aquellas palabras extremadamente tristes, es decir, aquellas cuya felicidad media\r\n" + //
            "(happiness_average) está por debajo de 2 y que además tienen ranking de Twitter\r\n" + //
            "(twitter_rank es diferente de --)\n";

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            @Override
            public void map(Tuple element, List<Tuple> output) {
                //Se obtienen las lineas
                String[] lines = element.getValue().toString().split(" ");
                
                for (String line : lines) {
                    //Obtenemos cada palabra de cada linea
                    String[] lineData = line.split("\\t");
                    //Obtenes la felicidad promedio
                    double happinessAverage = Double.parseDouble(lineData[2]);
                    //Si la felicidad promedio es menor a 2 y el ranking es diferente de --
                    if (happinessAverage < 2 && !lineData[4].equals("--")) {
                        //Agregamos la tupla al resultado
                        output.add(new Tuple("Palabras extremandamente tristes: ", 1));
                    }

                }
            }

        });
        tarea.setReduceFunction(new MyReduce() {

            /**
             * @param element Tuple
             * @param output  ArrayList que permite agregar las Tuples que queremos, en la
             *                cual será el resultado.
             */
            @SuppressWarnings("unchecked")
            @Override
            public void reduce(Tuple element, List<Tuple> output) {

                ArrayList<Integer> list = (ArrayList<Integer>) element.getValue();
                int count = 0;
                //Para cada linea de la lista se suma la cantidad de ocurrencias de la palabra
                for (Integer line : list) {
                    count += line;
                }
                //Se agrega una nueva tupla
                output.add(new Tuple(element.getKey(), count));
            }

        });

        tarea.setOutputFile("Resultado_7.txt");
        tarea.setInputFile("happiness.txt");
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n"+ "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);
        tarea.run();
    }
}
