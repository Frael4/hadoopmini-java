package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_2 {

    private final static String enunciado = "2. Utilizando el mismo log de servidor Web, calcular el número de peticiones de ficheros\r\n" + //
            "con extensión .gif";
    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            @Override
            public void map(Tuple element, List<Tuple> output) {

                String[] words = element.getValue().toString().split(" ");

                int gifCount = 0; // Contador para las extensiones ".gif"

                for (String w : words) {
                    
                    String new_w = w.toLowerCase();
                    //Si la palabra contiene .gif aumentamos el contador
                    if (new_w.contains(".gif")) {
                        gifCount++;
                    }
                }
                // Solo si contador es mayor a 0 agregamos a la salida
                if (gifCount > 0) {
                    output.add(new Tuple("El número de ficheros con extensión .gif es:", gifCount));
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
                //Por cada entero de la lista vamos a incrementar en el contador
                for (Integer item : list) {
                    count += item;
                }
                //Lo agregamos como tupla a la salida
                output.add(new Tuple(element.getKey(), count));
            }

        });

        tarea.setOutputFile("Resultado_2.txt");
        tarea.setInputFile("weblog.txt");
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n" + "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);
        tarea.run();

    }
}
