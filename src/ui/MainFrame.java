package ui;

import javax.swing.*;
import java.awt.*;
import algorithms.*;
import core.SortingAlgorithm;
import util.ArrayGenerator;
import java.util.Hashtable;

public class MainFrame extends JFrame {
    private SortingAlgorithm algorithm;
    private SortingPanel panel;
    private JComboBox<String> algorithmSelector;
    private JComboBox<String> arrayTypeSelector;
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
        algorithmSelector.addItem("Insertion Sort");
        algorithmSelector.addItem("Merge Sort");

        arrayTypeSelector = new JComboBox<>();
        arrayTypeSelector.addItem("Aleatorio");
        arrayTypeSelector.addItem("Crescente");
        arrayTypeSelector.addItem("Decrescente");

        String selectedType = (String) arrayTypeSelector.getSelectedItem();
        int[] array = ArrayGenerator.generateArray(50, selectedType);

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

            String currentType = (String) arrayTypeSelector.getSelectedItem();
            int[] newArray = ArrayGenerator.generateArray(50, currentType);

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            algorithm = createAlgorithm(selectedAlgorithm, newArray);
            panel.setAlgorithm(algorithm);
        });

        JSlider speedSlider = new JSlider(1, 500, 200);
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setMinorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(100, new JLabel("100"));
        labelTable.put(200, new JLabel("200"));
        labelTable.put(300, new JLabel("300"));
        labelTable.put(400, new JLabel("400"));
        labelTable.put(500, new JLabel("500"));
        speedSlider.setLabelTable(labelTable);

        speedSlider.addChangeListener(e -> {
            int delay = speedSlider.getValue();
            timer.setDelay(delay);
        });

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.add(new JLabel("Delay (ms)", SwingConstants.CENTER), BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.add(algorithmSelector);
        controls.add(arrayTypeSelector);
        controls.add(playButton);
        controls.add(pauseButton);
        controls.add(resetButton);
        controls.add(speedPanel);

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
            case "Insertion Sort":
                return new InsertionSort(array);
            case "Merge Sort":
                return new MergeSort(array);
            default:
                throw new IllegalArgumentException("Algoritmo desconhecido");
        }
    }
}