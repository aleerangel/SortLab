package ui;

import javax.swing.*;
import java.awt.*;

import algorithms.*;
import core.SortingAlgorithm;
import core.SortingEngine;
import util.ArrayGenerator;

public class MainFrame extends JFrame {
    private SortingAlgorithm algorithm;
    private SortingEngine engine;

    private SortingPanel panel;
    private ControlPanel controls;
    private MetricsPanel metrics;

    public MainFrame() {
        setTitle("SortLab");
        setSize(2560, 1600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controls = new ControlPanel();
        metrics = new MetricsPanel();

        int[] array = ArrayGenerator.generateArray(50, "Aleatorio");
        algorithm = createAlgorithm("Bubble Sort", array);

        panel = new SortingPanel(algorithm);

        engine = new SortingEngine(
            () -> {
                panel.repaint();
                metrics.updateMetrics(
                    algorithm.getComparisons(),
                    algorithm.getSwaps()
                );
            },
            () -> controls.getSpeed()
        );

        engine.setAlgorithm(algorithm);

        configureEvents();

        setLayout(new BorderLayout());
        add(metrics, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }

    private void configureEvents() {
        controls.onPlay(() -> engine.start());
        controls.onPause(() -> engine.stop());
        controls.onReset(() -> {
            engine.stop();
            metrics.reset();

            int size = controls.getArraySize();
            String type = controls.getArrayType();
            String algorithmName = controls.getSelectedAlgorithm();

            int[] newArray = ArrayGenerator.generateArray(size, type);

            algorithm = createAlgorithm(algorithmName, newArray);

            panel.setAlgorithm(algorithm);
            engine.setAlgorithm(algorithm);

            panel.repaint();
        });
    }

    private SortingAlgorithm createAlgorithm(String name, int[] array) {
        switch(name) {
            case "Bubble Sort":
                return new BubbleSort(array);
            case "Bubble Sort (Otimizado)":
                return new BubbleSortOtimizado(array);
            case "Selection Sort":
                return new SelectionSort(array);
            case "Insertion Sort":
                return new InsertionSort(array);
            case "Shell Sort":
                return new ShellSort(array);
            case "Merge Sort":
                return new MergeSort(array);
            default:
                throw new IllegalArgumentException("Algoritmo desconhecido");
        }
    }
}