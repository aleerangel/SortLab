package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ControlPanel extends JPanel {
    public JComboBox<String> algorithmSelector;
    public JComboBox<String> arrayTypeSelector;
    public JSpinner sizeSpinner;
    public JSlider speedSlider;

    public JButton playButton;
    public JButton pauseButton;
    public JButton resetButton;

    public ControlPanel() {
        algorithmSelector = new JComboBox<>();
        algorithmSelector.addItem("Bubble Sort");
        algorithmSelector.addItem("Bubble Sort (Otimizado)");
        algorithmSelector.addItem("Selection Sort");
        algorithmSelector.addItem("Insertion Sort");
        algorithmSelector.addItem("Shell Sort");
        algorithmSelector.addItem("Merge Sort");

        arrayTypeSelector = new JComboBox<>();
        arrayTypeSelector.addItem("Aleatorio");
        arrayTypeSelector.addItem("Crescente");
        arrayTypeSelector.addItem("Decrescente");

        sizeSpinner = new JSpinner(new SpinnerNumberModel(50, 10, 300, 10));

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        speedSlider = new JSlider(1, 500, 200); 

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("Turbo"));
        labelTable.put(500, new JLabel("Lento"));

        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(new JLabel("Tamanho (N)", SwingConstants.CENTER), BorderLayout.NORTH);
        sizePanel.add(sizeSpinner, BorderLayout.CENTER); 

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.add(new JLabel("Velocidade", SwingConstants.CENTER), BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        add(algorithmSelector);
        add(arrayTypeSelector);
        add(sizePanel);
        add(playButton);
        add(pauseButton);
        add(resetButton);
        add(speedPanel);
    }

    public void onPlay(Runnable action) {
        playButton.addActionListener(e -> action.run());
    }

    public void onPause(Runnable action) {
        pauseButton.addActionListener(e -> action.run());
    } 

    public void onReset(Runnable action) {
        resetButton.addActionListener(e -> action.run());
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }

    public String getArrayType() {
        return (String) arrayTypeSelector.getSelectedItem();
    }

    public int getArraySize() {
        return (int) sizeSpinner.getValue();
    }

    public int getSpeed() {
        return speedSlider.getValue();
    }
}