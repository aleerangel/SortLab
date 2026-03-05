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
        boolean[] partial = algorithm.getPartiallySortedIndices();
        boolean[] full = algorithm.getFullySortedIndices();

        boolean hasFloating = algorithm.hasFloatingKey();
        int floatingIdx = -1;
        int floatingValue = 0;
        if(hasFloating) {
            floatingIdx = algorithm.getFloatingKeyIndex();
            floatingValue = algorithm.getFloatingKeyValue();
        }

        int width = getWidth();
        int height = getHeight();

        int max = 0;
        for(int value : array) {
            if(value > max) max = value;
        }
        if (hasFloating && floatingValue > max) {
            max = floatingValue;
        }

        int paddingTop = 40;
        int usableHeight = height - paddingTop;

        for(int i = 0; i < array.length; i++) {
            int barHeight = (int) ((double) array[i] / max * usableHeight);

            Color barColor = Color.WHITE;

            boolean isFloatingIndex = (hasFloating && i == floatingIdx);

            if(!isFloatingIndex && active != null) {
                for(int idx : active) {
                    if(i == idx) {
                        if(action == StepAction.COMPARANDO) {
                            barColor = Color.RED;
                        } else if (action == StepAction.TROCANDO) {
                            barColor = Color.ORANGE;
                        }
                        break;
                    }
                }
            }

            if(barColor == Color.WHITE && full[i]) {
                barColor = Color.GREEN;
            }

            if(barColor == Color.WHITE && partial[i]) {
                barColor = Color.BLUE;
            }

            g.setColor(barColor);   

            int x1 = (int) ((double) i / array.length * width);
            int x2 = (int) ((double) (i + 1) / array.length * width);
            int barWidth = x2 - x1;

            g.fillRect(
                x1,
                height - barHeight,
                barWidth - 2,
                barHeight
            );
        }

        if(hasFloating) {
                Graphics2D g2d = (Graphics2D) g.create();
                //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

                int barHeight = (int) ((double) floatingValue / max * usableHeight);
                int x1 = (int) ((double) floatingIdx / array.length * width);
                int x2 = (int) ((double) (floatingIdx + 1) / array.length * width);
                int barWidth = x2 - x1;
                int y = height - barHeight;

                Color floatingColor;
                if(action == StepAction.COMPARANDO) {
                    floatingColor = Color.RED;
                } else {
                    floatingColor = Color.MAGENTA;
                }
                g2d.setColor(floatingColor);
                g2d.fillRect(
                    x1,
                    y,
                    barWidth - 2,
                    barHeight
                );

                //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2d.setColor(Color.BLACK);
                g2d.drawRect(
                    x1, 
                    y,
                    barWidth - 2,
                    barHeight
                );

                g2d.dispose();
            }
    }
}