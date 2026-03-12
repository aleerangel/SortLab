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
        setSize(1280, 720);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
            case "Insertion Sort (Direta)":
                return new InsertionSort(array, SearchStrategy.SEQUENTIAL);
            case "Insertion Sort (Binaria)":
                return new InsertionSort(array, SearchStrategy.BINARY);
            case "Insertion Sort (Ternaria)":
                return new InsertionSort(array, SearchStrategy.TERNARY);
            case "Shell Sort":
                return new ShellSort(array);
            case "Heap Sort":
                return new HeapSort(array);
            case "Merge Sort":
                return new MergeSort(array);
            case "Quick Sort (Fim)":
                return new QuickSort(array, PivotStrategy.LAST);
            case "Quick Sort (Meio)":
                return new QuickSort(array, PivotStrategy.MIDDLE);
            case "Quick Sort (Med. de 3)":
                return new QuickSort(array, PivotStrategy.MEDIAN_OF_THREE);
            case "Bucket Sort":
                return new BucketSort(array);
            case "Radix Sort":
                return new RadixSort(array);
            default:
                throw new IllegalArgumentException("Algoritmo desconhecido");
        }
    }
}