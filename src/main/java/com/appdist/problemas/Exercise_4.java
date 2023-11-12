package com.appdist.problemas;

import java.util.List;

import javax.swing.JOptionPane;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_4 {

    private final static String enunciado = "4. Filtar el fichero y quedarnos únicamente con las entradas en las que la\r\n" + //
            "temperatura en superficie (surface temperature) es diferente de la sensación térmica\r\n" + //
            "(wind chill).";

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {

            /**
             * @param elemento Tupla
             * @param output   ArrayList que permite agregar las tuplas que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {

                // Asegurarse de que los índices correspondan a los campos correctos
                String[] line = element.getValue().toString().split(" ");

                for (String item : line) {

                    String[] lineData = item.split(",");

                    if (!lineData[8].equals(lineData[12])) {
                        output.add(new Tuple(item, 1));
                    }

                }

            }

        });
        tarea.setReduceFunction(new MyReduce() {

            /**
             * @param elemento Tupla
             * @param output   ArrayList que permite agregar las tuplas que queremos, en la cual será el resultado.
             */
            @Override
            public void reduce(Tuple element, List<Tuple> output) {
                output.add(new Tuple(element.getKey(), ""));
            }

        });

        tarea.setOutputFile("Resultado_4.txt");
        tarea.setInputFile("JCMB_last31days.csv");
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Ingrese la cantidad de nodos para el trabajo: ");
        //int nodos = scanner.nextInt();
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n"+ "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);
        //scanner.close();
        tarea.run();
    }

}
