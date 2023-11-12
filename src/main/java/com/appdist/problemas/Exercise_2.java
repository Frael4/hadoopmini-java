package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_2 {

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            @Override
            public void map(Tuple element, List<Tuple> output) {

                String[] words = element.getValue().toString().split(" ");
                
                int gifCount = 0; // Contador para las extensiones ".gif"

                for (String w : words) {
                    String new_w = w.toLowerCase();
                    if (new_w.contains(".gif")) {
                        gifCount++;
                    }
                }
                //Solo si contador es mayor a 0, imprimimos
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

                for (Integer item : list) {
                    count += item;
                }
                
                output.add(new Tuple(element.getKey(), count));
            }

        });

        tarea.setOutputFile("Resultado_2.txt");
        tarea.setInputFile("weblog.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de nodos para el trabajo: ");
        int nodos = scanner.nextInt();
        tarea.setNode(nodos);
        tarea.run();
        scanner.close();

    }
}
