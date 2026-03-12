package core;

public interface PartitionedSort extends SortingAlgorithm {
    int getNumberOfPartitions();
    int[] getPartition(int partition);
    int getActivePartition();
    int[] getActiveIndicesInPartition(int partition);
    boolean isPartitionSorted(int partition);
    String getPartitionLabel(int partition);
    int getInsertionIndexInPartition(int partition);
    int getInsertionValueInPartition(int partition);
}