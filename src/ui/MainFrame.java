package ui;

import javax.swing.*;
import java.awt.*;
import algorithms.BubbleSort;
import core.SortingAlgorithm;

public class MainFrame extends JFrame {
    private SortingAlgorithm algorithm;
    private SortingPanel panel;

    public MainFrame() {
        setTitle("SortLab");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int[] array = generateRandomArray(50);

        algorithm = new BubbleSort(array);
        panel = new SortingPanel(algorithm);

        JButton nextButton = new JButton("New step");
        nextButton.addActionListener(e -> {
            algorithm.nextStep();
            panel.repaint();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            algorithm = new BubbleSort(generateRandomArray(50));
            panel.setAlgorithm(algorithm);
        });

        JPanel controls = new JPanel();
        controls.add(nextButton);
        controls.add(resetButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 100) + 1;
        }
        return array;
    }
}