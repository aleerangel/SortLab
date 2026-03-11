package ui;

import javax.swing.*;
import java.awt.*;

public class MetricsPanel extends JPanel {
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;

    public MetricsPanel() {
        comparisonsLabel = new JLabel("Comparacoes: 0");
        swapsLabel = new JLabel("Trocas / Escritas: 0");

        comparisonsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        swapsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        add(comparisonsLabel);
        add(swapsLabel);
    }

    public void updateMetrics(int comparisons, int swaps) {
        comparisonsLabel.setText("Comparacoes: " + comparisons);
        swapsLabel.setText("Trocas / Escritas: " + swaps);
    }

    public void reset() {
        updateMetrics(0,0);
    }
}