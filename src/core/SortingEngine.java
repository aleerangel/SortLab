package core;

import javax.swing.SwingUtilities;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.function.IntSupplier;

public class SortingEngine {
    private Thread sortingThread;
    private volatile boolean running = false;
    private SortingAlgorithm algorithm;
    private int vSyncDelay;

    private Runnable onFrameUpdate;
    private IntSupplier delaySupplier;

    public SortingEngine(Runnable onFrameUpdate, IntSupplier delaySupplier) {
        this.onFrameUpdate = onFrameUpdate;
        this.delaySupplier = delaySupplier; 

        int refreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();

        if(refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN || refreshRate <= 0) {
            refreshRate = 60;
        }

        this.vSyncDelay = 1000 / refreshRate;
    }

    public void setAlgorithm(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void start() {
        if (running || algorithm == null || algorithm.isFinished()) return;
        running = true;

        sortingThread = new Thread(() -> {
            while (running && !algorithm.isFinished()) {
                int requestedDelay = delaySupplier.getAsInt();
                int sleepTime = requestedDelay;
                int stepsPerFrame = 1;

                if(requestedDelay < vSyncDelay) {
                    sleepTime = vSyncDelay;

                    if(requestedDelay <= 2) {
                        int n = algorithm.getArray().length;
                        stepsPerFrame = Math.max(1, n / 50);
                    } else {
                        stepsPerFrame = Math.max(1, vSyncDelay / requestedDelay);
                    }
                }

                for(int i = 0; i < stepsPerFrame; i++) {
                    if(!algorithm.isFinished()) {
                        algorithm.nextStep();
                    }
                }

                SwingUtilities.invokeLater(onFrameUpdate);

                try{
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            running = false;
        });

        sortingThread.start();
    }

    public void stop() {
        running = false;
        if (sortingThread != null) {
            sortingThread.interrupt();
        }
    }

    public boolean isRunning() {
        return running;
    }
}