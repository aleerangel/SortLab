package ui;

import javax.swing.*;
import java.awt.*;
import algorithms.BubbleSort;
import algorithms.SelectionSort;
import core.SortingAlgorithm;

public class MainFrame extends JFrame {
    private SortingAlgorithm algorithm;
    private SortingPanel panel;
    private JComboBox<String> algorithmSelector;

    public MainFrame() {
        setTitle("SortLab");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        algorithmSelector = new JComboBox<>();
        algorithmSelector.addItem("Bubble Sort");
        algorithmSelector.addItem("Selection Sort");

        int[] array = generateRandomArray(50);
        String selected = (String) algorithmSelector.getSelectedItem();
        algorithm = createAlgorithm(selected, array);

        panel = new SortingPanel(algorithm);

        JButton nextButton = new JButton("New step");
        nextButton.addActionListener(e -> {
            algorithm.nextStep();
            panel.repaint();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            int[] newArray = generateRandomArray(50);

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            algorithm = createAlgorithm(selectedAlgorithm, newArray);
            panel.setAlgorithm(algorithm);
        });

        JPanel controls = new JPanel();
        controls.add(algorithmSelector);
        controls.add(nextButton);
        controls.add(resetButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }

    private SortingAlgorithm createAlgorithm(String name, int[] array) {
        switch(name) {
            case "Bubble Sort":
                return new BubbleSort(array);
            case "Selection Sort":
                return new SelectionSort(array);
            default:
                throw new IllegalArgumentException("Algoritmo desconhecido");
        }
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 100) + 1;
        }
        return array;
    }
}