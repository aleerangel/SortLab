package core;

public interface SortingAlgorithm {
    void nextStep();
    boolean isFinished();
    int[] getArray();
    StepAction getCurrentAction();
    int[] getActiveIndices();
    boolean[] getPartiallySortedIndices();
    boolean[] getFullySortedIndices();
    int getComparisons();
    int getSwaps();
    default boolean hasFloatingKey() {return false;}
    default int getFloatingKeyValue() {return 0;}
    default int getFloatingKeyIndex() {return -1;}
}