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
    private Timer timer;
    private boolean running = false;

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

        timer = new Timer(200, e -> {
            if(!algorithm.isFinished()) {
                algorithm.nextStep();
                panel.repaint();
            } else {
                timer.stop();
                running = false;
            }
        });


        JButton playButton = new JButton("Play");
        playButton.addActionListener( e-> {
            if(!running) {
                timer.start();
                running = true;
            }
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e-> {
            timer.stop();
            running = false;
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            timer.stop();
            running = false;

            int[] newArray = generateRandomArray(50);

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            algorithm = createAlgorithm(selectedAlgorithm, newArray);
            panel.setAlgorithm(algorithm);
        });

        JSlider speedSlider = new JSlider(1, 200, 50);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            int min = speedSlider.getMinimum();
            int max = speedSlider.getMaximum();
            int value = speedSlider.getValue();

            int inverted = max - value + min;

            timer.setDelay(inverted);
        });

        JPanel controls = new JPanel();
        controls.add(algorithmSelector);
        controls.add(playButton);
        controls.add(pauseButton);
        controls.add(resetButton);
        controls.add(speedSlider);

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