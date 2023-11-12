package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_3 {

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            /**
             * @param element Tuple
             * @param output   ArrayList que permite agregar las Tuples que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {

                String[] words = element.getValue().toString().split(" ");

                for (String word : words) {

                    if (word.startsWith("[") && word.endsWith("]")) {
                        String hora = word.split(":")[1];
                        output.add(new Tuple(hora, 1));
                    }

                }
            }

        });
        tarea.setReduceFunction(new MyReduce() {
            
            /**
             * @param element Tuple
             * @param output   ArrayList que permite agregar las Tuples que queremos, en la
             *                 cual será el resultado.
             */
            @SuppressWarnings("unchecked")
            @Override
            public void reduce(Tuple element, List<Tuple> output) {

                ArrayList<Integer> list = (ArrayList<Integer>) element.getValue();
                int count = 0;

                for (Integer item : list) {
                    count += item;
                }
                // Modificar el mensaje para mostrar la hora y el número de accesos
                String mensaje = "A las " + element.getKey() + " horas" + " el número de accesos es: " + count;
                output.add(new Tuple(" ", mensaje));
                
            }

        });

        tarea.setOutputFile("Resultado_3.txt");
        tarea.setInputFile("weblog.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la cantidad de nodos para el trabajo: ");
        int nodos = scanner.nextInt();
        tarea.setNode(nodos);
        scanner.close();
        tarea.run();
    }
}
