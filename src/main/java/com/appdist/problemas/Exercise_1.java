package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_1 {

    public static void main(String[] args) {

        final String enunciado = "1. Calcular el número peticiones que han obtenido un\n"+
        "código 404 (Not Found). \n";
        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            /**
             * @param element Tuple
             * @param output   ArrayList que permite agregar las Tuples que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {
                // Separamos las palabras y almacenamos en un array
                String[] words = element.getValue().toString().split(" ");

                for (String w : words) {
                    // busca y elimina todos los caracteres que NO son letras, dígitos o guiones
                    // bajos en la cadena en minúsculas.
                    String new_w = w.toLowerCase().replaceAll("[^\\w]", "");
                    if (new_w.equals("404")) {
                        output.add(new Tuple(new_w, 1));
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

                final ArrayList<Integer> list = (ArrayList<Integer>) element.getValue();
                int count = 0;

                for (Integer item : list) {
                    count += item;
                }

                if (element.getKey().equals("404")) {

                    String mensaje = "El número de peticiones que han obtenido un código 404 (Not Found) es: " + count;
                    output.add(new Tuple("", mensaje));

                } else {
                    output.add(new Tuple(element.getKey(), count));
                }
            }

        });

        tarea.setOutputFile("Resultado_1.txt");
        tarea.setInputFile("weblog.txt");
       /*  Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la cantidad de nodos para el trabajo: "); */
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n"+ "Ingrese la cantidad de nodos para el trabajo: "));
        //int nodos = scanner.nextInt();
        tarea.setNode(nodos);
        //scanner.close();
        tarea.run();

    }
}
