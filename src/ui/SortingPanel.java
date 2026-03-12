package ui;

import javax.swing.*;
import java.awt.*;
import core.SortingAlgorithm;
import core.StepAction;
import core.PartitionedSort;

public class SortingPanel extends JPanel {
    private SortingAlgorithm algorithm;

    private static final double PARTITIONS_AREA_RATIO = 0.3;

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

        if (algorithm == null) return;

        int[] array = algorithm.getArray();
        int[] active = algorithm.getActiveIndices();
        StepAction action = algorithm.getCurrentAction();
        boolean[] partial = algorithm.getPartiallySortedIndices();
        boolean[] full = algorithm.getFullySortedIndices();

        boolean hasFloating = algorithm.hasFloatingKey();
        int floatingIdx = -1;
        int floatingValue = 0;
        if (hasFloating) {
            floatingIdx = algorithm.getFloatingKeyIndex();
            floatingValue = algorithm.getFloatingKeyValue();
        }

        int width = getWidth();
        int height = getHeight();

        int mainArrayHeight;
        int partitionsHeight = 0;
        if (algorithm instanceof PartitionedSort) {
            mainArrayHeight = (int) (height * (1 - PARTITIONS_AREA_RATIO));
            partitionsHeight = height - mainArrayHeight;
        } else {
            mainArrayHeight = height;
        }

        int max = 0;
        for (int value : array) {
            if (value > max) max = value;
        }
        if (hasFloating && floatingValue > max) {
            max = floatingValue;
        }

        drawArray(g, array, active, action, partial, full, hasFloating, floatingIdx, floatingValue, max, 0, mainArrayHeight, width);

        if (algorithm instanceof PartitionedSort) {
            PartitionedSort ps = (PartitionedSort) algorithm;
            drawPartitions(g, ps, mainArrayHeight, partitionsHeight, width, max, action);
        }
    }

    private void drawArray(Graphics g, int[] array, int[] active, StepAction action,
                           boolean[] partial, boolean[] full,
                           boolean hasFloating, int floatingIdx, int floatingValue,
                           int max, int yOffset, int height, int width) {
        int usableHeight = height - 10;

        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) ((double) array[i] / max * usableHeight);

            Color barColor = Color.WHITE;

            boolean isFloatingIndex = (hasFloating && i == floatingIdx);

            if (!isFloatingIndex && active != null) {
                for (int idx : active) {
                    if (i == idx) {
                        if (action == StepAction.COMPARANDO) {
                            barColor = Color.RED;
                        } else if (action == StepAction.TROCANDO) {
                            barColor = Color.ORANGE;
                        }
                        break;
                    }
                }
            }

            if (barColor == Color.WHITE && full[i]) {
                barColor = Color.GREEN;
            }

            if (barColor == Color.WHITE && partial[i]) {
                barColor = Color.BLUE;
            }

            g.setColor(barColor);

            int x1 = (int) ((double) i / array.length * width);
            int x2 = (int) ((double) (i + 1) / array.length * width);
            int barWidth = x2 - x1;

            g.fillRect(
                x1,
                yOffset + height - barHeight - 5, 
                barWidth - 2,
                barHeight
            );
        }

        if (hasFloating) {
            Graphics2D g2d = (Graphics2D) g.create();
            int barHeight = (int) ((double) floatingValue / max * usableHeight);
            int x1 = (int) ((double) floatingIdx / array.length * width);
            int x2 = (int) ((double) (floatingIdx + 1) / array.length * width);
            int barWidth = x2 - x1;
            int y = yOffset + height - barHeight - 5;

            Color floatingColor;
            if (action == StepAction.COMPARANDO) {
                floatingColor = Color.RED;
            } else {
                floatingColor = Color.MAGENTA;
            }
            g2d.setColor(floatingColor);
            g2d.fillRect(x1, y, barWidth - 2, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x1, y, barWidth - 2, barHeight);

            g2d.dispose();
        }
    }

    private void drawPartitions(Graphics g, PartitionedSort ps,
                                int yStart, int height, int width,
                                int globalMax, StepAction action) {
        int numPartitions = ps.getNumberOfPartitions();
        if (numPartitions == 0) return;

        int partitionHeight = height / numPartitions;
        int activePartition = ps.getActivePartition();

        for (int p = 0; p < numPartitions; p++) {
            int[] elements = ps.getPartition(p);
            int[] activeInPartition = ps.getActiveIndicesInPartition(p);
            boolean sorted = ps.isPartitionSorted(p);
            String label = ps.getPartitionLabel(p);
            int insertionIdx = ps.getInsertionIndexInPartition(p);
            int insertionVal = ps.getInsertionValueInPartition(p);

            int yPart = yStart + p * partitionHeight;

            if (p == activePartition) {
                g.setColor(new Color(60, 60, 60));
                g.fillRect(0, yPart, width, partitionHeight - 2);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, yPart, width, partitionHeight - 2);
            }

            g.setColor(Color.GRAY);
            g.drawLine(0, yPart, width, yPart);

            int x = 5; 
            int elemHeight = partitionHeight - 10;
            int elemY = yPart + 5;

            for (int i = 0; i < elements.length; i++) {
                int value = elements[i];
                int elemWidth = (int) ((double) value / globalMax * 50) + 20; 

                Color elemColor;
                if (p == activePartition && activeInPartition != null) {
                    boolean isActive = false;
                    for (int idx : activeInPartition) {
                        if (idx == i) {
                            isActive = true;
                            break;
                        }
                    }
                    if (isActive) {
                        if (action == StepAction.COMPARANDO) {
                            elemColor = Color.RED;
                        } else {
                            elemColor = Color.ORANGE;
                        }
                    } else if (i == insertionIdx) {
                        elemColor = Color.MAGENTA;
                    } else {
                        elemColor = sorted ? new Color(144, 238, 144) : Color.WHITE; 
                    }
                } else {
                    elemColor = sorted ? new Color(144, 238, 144) : Color.WHITE;
                }

                g.setColor(elemColor);
                g.fillRect(x, elemY, elemWidth - 2, elemHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, elemY, elemWidth - 2, elemHeight);

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(value), x + 5, elemY + elemHeight / 2 + 5);

                x += elemWidth;
                if (x > width) break; 
            }

            g.setColor(Color.LIGHT_GRAY);
            g.drawString(label, 5, yPart + 15);
        }
    }
}