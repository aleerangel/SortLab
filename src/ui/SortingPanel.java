package ui;

import javax.swing.*;
import java.awt.*;
import core.SortingAlgorithm;
import core.StepAction;

public class SortingPanel extends JPanel {
    private SortingAlgorithm algorithm;

    public SortingPanel(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
        setBackground(Color.BLACK);
    }

    public void setAlgorithm(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
        repaint();
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(algorithm == null) return;

        int[] array = algorithm.getArray();
        int[] active = algorithm.getActiveIndices();
        StepAction action = algorithm.getCurrentAction();
        boolean[] sorted = algorithm.getSortedIndices();

        int width = getWidth();
        int height = getHeight();

        int barWidth = width / array.length;

        int max = 0;
        for(int value : array) {
            if(value > max) max = value;
        }

        for(int i = 0; i < array.length; i++) {
            int paddingTop = 40;
            int usableHeight = height - paddingTop;

            int barHeight = (int) ((double) array[i] / max * usableHeight);

            Color barColor = Color.WHITE;

            if(action == StepAction.FINALIZADO || sorted[i]) {
                barColor = Color.GREEN;
            } else {
                for(int idx : active) {
                    if(i == idx) {
                        if(action == StepAction.COMPARANDO) {
                        barColor = Color.RED;
                    } else if(action == StepAction.TROCANDO) {
                        barColor = Color.ORANGE;
                    }
                    }
                }
            }

            g.setColor(barColor);

            g.fillRect(
                i * barWidth,
                height - barHeight,
                barWidth - 2,
                barHeight
            );
        }
    }
}