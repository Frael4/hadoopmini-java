package com.appdist.problemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
                String[] line = element.getValue().toString().split(" ");

                for (String item : line) {

                    String[] lineData = item.split(",");
                    double temp = Double.parseDouble(lineData[8]);
                    listNumeros.add(temp);

                }

                double min = Collections.min(listNumeros);
                double max = Collections.max(listNumeros);
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

                String valor = clave + ": " + element.getValue().toString().replaceAll("[\\[\\]]", "");

                output.add(new Tuple(clave, valor));
            }

        });

        tarea.setOutputFile("Resultado_6.txt");
        tarea.setInputFile("JCMB_last31days.csv");
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Ingrese la cantidad de nodos para el trabajo: ");
        //int nodos = scanner.nextInt();
        Integer nodos = Integer.parseInt(JOptionPane.showInputDialog(enunciado + "\n"+ "Ingrese la cantidad de nodos para el trabajo: "));
        tarea.setNode(nodos);
        scanner.close();
        //tarea.run();
    }
}
