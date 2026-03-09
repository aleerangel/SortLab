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
    private JSpinner sizeSpinner;
    private JSlider speedSlider;
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;
    private Thread sortingThread;
    private boolean running = false;
    private int vSyncDelay;

    public MainFrame() {
        setTitle("SortLab");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int refreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        if(refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN || refreshRate <= 0) {
            refreshRate = 60;
        }

        vSyncDelay = 1000 / refreshRate;

        algorithmSelector = new JComboBox<>();
        algorithmSelector.addItem("Bubble Sort");
        algorithmSelector.addItem("Selection Sort");
        algorithmSelector.addItem("Insertion Sort");
        algorithmSelector.addItem("Merge Sort");

        arrayTypeSelector = new JComboBox<>();
        arrayTypeSelector.addItem("Aleatorio");
        arrayTypeSelector.addItem("Crescente");
        arrayTypeSelector.addItem("Decrescente");

        SpinnerModel sizeModel = new SpinnerNumberModel(50, 10, 200, 10);
        sizeSpinner = new JSpinner(sizeModel);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(new JLabel("Tamanho (N)", SwingConstants.CENTER), BorderLayout.NORTH);
        sizePanel.add(sizeSpinner, BorderLayout.CENTER);

        String selectedType = (String) arrayTypeSelector.getSelectedItem();
        int initialSize = (int) sizeSpinner.getValue();
        int[] array = ArrayGenerator.generateArray(initialSize, selectedType);

        String selected = (String) algorithmSelector.getSelectedItem();
        algorithm = createAlgorithm(selected, array);

        panel = new SortingPanel(algorithm);

        JButton playButton = new JButton("Play");
        playButton.addActionListener( e-> {
            if(!running && !algorithm.isFinished()) {
                startSorting();
            }
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e-> {
            running = false;
            if(sortingThread != null) {
                sortingThread.interrupt();
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            running = false;
            if(sortingThread != null) {
                sortingThread.interrupt();
            }

            comparisonsLabel.setText("Comparacoes: 0");
            swapsLabel.setText("Trocas / Escritas: 0");

            String currentType = (String) arrayTypeSelector.getSelectedItem();
            int currentSize = (int) sizeSpinner.getValue();
            int[] newArray = ArrayGenerator.generateArray(currentSize, currentType);

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            algorithm = createAlgorithm(selectedAlgorithm, newArray);
            panel.setAlgorithm(algorithm);
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

        comparisonsLabel = new JLabel("Comparacoes: 0");
        comparisonsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        swapsLabel = new JLabel("Trocas / Escritas : 0");
        swapsLabel.setFont(new Font("Arial", Font.BOLD, 14));

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

    private void startSorting() {
        running = true;

        sortingThread = new Thread(() -> {
            while(running && !algorithm.isFinished()) {
                int requestedDelay = speedSlider.getValue();
                int sleepTime = requestedDelay;
                int stepsPerFrame = 1;

                if(requestedDelay < vSyncDelay) {
                    sleepTime = vSyncDelay;

                    if(requestedDelay <= 2) {
                        int n = algorithm.getArray().length;

                        stepsPerFrame = Math.max(1, n / 50);
                    } else {
                        stepsPerFrame = vSyncDelay / requestedDelay;
                        if(stepsPerFrame < 1) stepsPerFrame = 1;
                    }
                }

                for(int i = 0; i < stepsPerFrame; i++) {
                    if(!algorithm.isFinished()) {
                        algorithm.nextStep();
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    panel.repaint();
                    comparisonsLabel.setText("Comparacoes: " + algorithm.getComparisons());
                    swapsLabel.setText("Trocas / Escritas: " + algorithm.getSwaps());
                });

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            running = false;
        });

        sortingThread.start();
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