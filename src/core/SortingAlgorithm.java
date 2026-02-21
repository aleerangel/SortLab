package core;

public interface SortingAlgorithm {
    void nextStep();
    boolean isFinished();
    int[] getArray();
    StepAction getCurrentAction();
    int[] getActiveIndices();
}