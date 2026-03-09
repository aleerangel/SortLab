package ui;

import javax.swing.*;
import java.awt.*;
import algorithms.*;
import core.SortingAlgorithm;
import core.SortingEngine;
import util.ArrayGenerator;
import java.util.Hashtable;

public class MainFrame extends JFrame {
    private SortingAlgorithm algorithm;
    private SortingPanel panel;
    private JComboBox<String> algorithmSelector;
    private JComboBox<String> arrayTypeSelector;
    private JSpinner sizeSpinner;
    private JSlider speedSlider;
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;
    private SortingEngine engine;

    public MainFrame() {
        setTitle("SortLab");
        setSize(2560, 1600);
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

        SpinnerModel sizeModel = new SpinnerNumberModel(50, 10, 300, 10);
        sizeSpinner = new JSpinner(sizeModel);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(new JLabel("Tamanho (N)", SwingConstants.CENTER), BorderLayout.NORTH);
        sizePanel.add(sizeSpinner, BorderLayout.CENTER);

        comparisonsLabel = new JLabel("Comparacoes: 0");
        comparisonsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        swapsLabel = new JLabel("Trocas / Escritas : 0");
        swapsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String selectedType = (String) arrayTypeSelector.getSelectedItem();
        int initialSize = (int) sizeSpinner.getValue();
        int[] array = ArrayGenerator.generateArray(initialSize, selectedType);

        String selected = (String) algorithmSelector.getSelectedItem();
        algorithm = createAlgorithm(selected, array);

        panel = new SortingPanel(algorithm);

        engine = new SortingEngine(
            () -> {
                panel.repaint();
                comparisonsLabel.setText("Comparacoes: " + algorithm.getComparisons());
                swapsLabel.setText("Trocas / Escritas: " + algorithm.getSwaps());
            }, 
            () -> speedSlider.getValue()
        ); 

        engine.setAlgorithm(algorithm);

        JButton playButton = new JButton("Play");
        playButton.addActionListener( e-> {
            engine.start();
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e-> {
            engine.stop();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            engine.stop();

            comparisonsLabel.setText("Comparacoes: 0");
            swapsLabel.setText("Trocas / Escritas: 0");

            String currentType = (String) arrayTypeSelector.getSelectedItem();
            int currentSize = (int) sizeSpinner.getValue();
            int[] newArray = ArrayGenerator.generateArray(currentSize, currentType);

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            algorithm = createAlgorithm(selectedAlgorithm, newArray);

            panel.setAlgorithm(algorithm);
            engine.setAlgorithm(algorithm);
            panel.repaint();
        });

        speedSlider = new JSlider(1, 500, 200);
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setMinorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("Turbo"));
        labelTable.put(100, new JLabel("100"));
        labelTable.put(200, new JLabel("200"));
        labelTable.put(300, new JLabel("300"));
        labelTable.put(400, new JLabel("400"));
        labelTable.put(500, new JLabel("500"));
        speedSlider.setLabelTable(labelTable);

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.add(new JLabel("Delay (ms)", SwingConstants.CENTER), BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel metricsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        metricsPanel.add(comparisonsLabel);
        metricsPanel.add(swapsLabel);

        JPanel controls = new JPanel();
        controls.add(algorithmSelector);
        controls.add(arrayTypeSelector);
        controls.add(sizePanel);
        controls.add(playButton);
        controls.add(pauseButton);
        controls.add(resetButton);
        controls.add(speedPanel);

        setLayout(new BorderLayout());
        add(metricsPanel, BorderLayout.NORTH);
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