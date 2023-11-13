package com.appdist.problemas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_3 {

    private final static String enunciado = "3. Utilizando el mismo log de servidor Web, calcular el número de accesos por horas,\r\n"
            + //
            "para saber a qué horas se concentra el mayor número de peticiones";

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            /**
             * @param element Tuple
             * @param output  ArrayList que permite agregar las Tuples que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {
                //Obtenemos las palabras de la linea
                String[] words = element.getValue().toString().split(" ");

                for (String word : words) {
                    //Si la palabra estra entre corchetes
                    if (word.startsWith("[") && word.endsWith("]")) {
                        //Obtenemos la hora
                        String hora = word.split(":")[1];
                        //Agregamos a la salida
                        output.add(new Tuple(hora, 1));
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
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n" + "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);
        tarea.run();
    }
}
