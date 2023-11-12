package com.appdist.problemas;

import java.util.List;

import javax.swing.JOptionPane;

import com.appdist.model.MyMap;
import com.appdist.model.MyReduce;
import com.appdist.model.Task;
import com.appdist.model.Tuple;

public class Exercise_5 {

    private final static String enunciado = "5. Filtrar las entradas y\r\n" + //
            "quedarse únicamente con aquellas que tienen precipitaciones (rainfall) mayores\r\n" + //
            "que 0. Además de la cantidad de precipitaciones, para cada una de ellas queremos\r\n" + //
            "quedarnos únicamente con la humedad relativa (relative humidity) y la\r\n" + //
            "sensación térmica (wind chill). Es decir, la salida serán ternas del tipo (0.4, 80.9,\r\n" + //
            "15,4).";

    public static void main(String[] args) {

        Task tarea = new Task();

        tarea.setMapFunction(new MyMap() {
            /**
             * @param elemento Tupla
             * @param output   ArrayList que permite agregar las tuplas que queremos.
             */
            @Override
            public void map(Tuple element, List<Tuple> output) {

                String[] line = element.getValue().toString().split(" ");

                for (String item : line) {

                    String[] lineData = item.split(",");
                    double rainFall = Double.parseDouble(lineData[5]);

                    if (rainFall > 0) {

                        String rainfall = lineData[5];
                        String relativeHumidity = lineData[9];
                        String windChill = lineData[12];

                        String outputLine = "(" + rainfall + ", " + relativeHumidity + ", " + windChill + ")";
                        output.add(new Tuple(outputLine, 1));
                    }
                }
            }

        });

        tarea.setReduceFunction(new MyReduce() {

            /**
             * @param elemento Tupla
             * @param output   ArrayList que permite agregar las tuplas que queremos, en la
             *                 cual será el resultado.
             */
            @Override
            public void reduce(Tuple element, List<Tuple> output) {
                output.add(new Tuple(element.getKey(), ""));
            }

        });

        tarea.setOutputFile("Resultado_5.txt");
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
