package algorithms;

import core.SortingAlgorithm;
import core.StepAction;
import core.PartitionedSort;
import java.util.ArrayList;
import java.util.List;

public class BucketSort implements PartitionedSort {
    private int[] array;                     
    private boolean finished = false;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices;             
    private boolean[] fullySorted;            

    private int comparisons = 0;
    private int writes = 0;

    private List<List<Integer>> buckets;
    private int numBuckets;
    private int maxVal;

    private enum BucketState { SCATTER, SORT_BUCKETS, GATHER }
    private BucketState state = BucketState.SCATTER;


    private int scatterIndex = 0;              

    private int currentBucket = 0;              
    private int insertionIndexInBucket = -1;    
    private int insertionValue;                  
    private int compareIndex;                    
    private int gatherBucket = 0;                
    private int gatherIndexInBucket = 0;          
    private int writeIndex = 0;                   

    private int activePartition = -1;            
    private int[] activeIndicesInPartition;      
    public BucketSort(int[] array) {
        this.array = array.clone();
        int n = array.length;
        this.fullySorted = new boolean[n];
        this.activeIndices = new int[]{-1};

        maxVal = array[0];
        for (int val : array) {
            if (val > maxVal) maxVal = val;
        }

        numBuckets = (int) Math.sqrt(n);
        if (numBuckets < 1) numBuckets = 1;

        buckets = new ArrayList<>(numBuckets);
        for (int j = 0; j < numBuckets; j++) {
            buckets.add(new ArrayList<>());
        }

        activeIndicesInPartition = new int[0];
    }

    @Override
    public void nextStep() {
        if (finished) return;

        switch (state) {
            case SCATTER:
                stepScatter();
                break;
            case SORT_BUCKETS:
                stepSortBuckets();
                break;
            case GATHER:
                stepGather();
                break;
        }
    }

    private void stepScatter() {
        if (scatterIndex < array.length) {
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[]{scatterIndex};
            activePartition = -1; 

            int bucketIndex = (array[scatterIndex] * numBuckets) / (maxVal + 1);
            buckets.get(bucketIndex).add(array[scatterIndex]);

            comparisons++; 
            writes++; 

            scatterIndex++;
        } else {
            state = BucketState.SORT_BUCKETS;
            currentBucket = 0;
            prepareNextBucket();
        }
    }

    private void prepareNextBucket() {
        if (currentBucket >= numBuckets) {
            state = BucketState.GATHER;
            gatherBucket = 0;
            gatherIndexInBucket = 0;
            writeIndex = 0;
            activePartition = -1;
            activeIndicesInPartition = new int[0];
            return;
        }

        insertionIndexInBucket = -1; 
        
        List<Integer> bucket = buckets.get(currentBucket);
        if (bucket.size() <= 1) {
            currentBucket++;
            prepareNextBucket();
        } else {
            insertionIndexInBucket = 1;
            insertionValue = bucket.get(1);
            compareIndex = 0; 
            activePartition = currentBucket;
            activeIndicesInPartition = new int[]{compareIndex};
            currentAction = StepAction.COMPARANDO;
        }
    }

    private void stepSortBuckets() {
        if (currentBucket >= numBuckets) {
            state = BucketState.GATHER;
            gatherBucket = 0;
            gatherIndexInBucket = 0;
            writeIndex = 0;
            activePartition = -1;
            activeIndicesInPartition = new int[0];
            return;
        }

        List<Integer> bucket = buckets.get(currentBucket);

        if (insertionIndexInBucket == -1) {
            currentBucket++;
            prepareNextBucket();
            return;
        }

        if (compareIndex >= 0 && bucket.get(compareIndex) > insertionValue) {
            currentAction = StepAction.TROCANDO;
            activeIndicesInPartition = new int[]{compareIndex, compareIndex + 1};

            bucket.set(compareIndex + 1, bucket.get(compareIndex));
            writes++;

            compareIndex--;
        } else {
            currentAction = StepAction.TROCANDO;
            activeIndicesInPartition = new int[]{compareIndex + 1};

            bucket.set(compareIndex + 1, insertionValue);
            writes++;

            insertionIndexInBucket++;

            if (insertionIndexInBucket < bucket.size()) {
                insertionValue = bucket.get(insertionIndexInBucket);
                compareIndex = insertionIndexInBucket - 1;
                activeIndicesInPartition = new int[]{compareIndex};
                currentAction = StepAction.COMPARANDO;
            } else {
                currentBucket++;
                prepareNextBucket();
            }
        }
    }

    private void stepGather() {
        if (gatherBucket >= numBuckets) {
            finalizar();
            return;
        }

        List<Integer> bucket = buckets.get(gatherBucket);

        if (gatherIndexInBucket < bucket.size()) {
            activePartition = gatherBucket;
            activeIndicesInPartition = new int[]{gatherIndexInBucket};
            activeIndices = new int[]{writeIndex};
            currentAction = StepAction.TROCANDO;

            array[writeIndex] = bucket.get(gatherIndexInBucket);
            fullySorted[writeIndex] = true;
            writes++;

            gatherIndexInBucket++;
            writeIndex++;
        } else {
            gatherBucket++;
            gatherIndexInBucket = 0;
        }
    }

    private void finalizar() {
        finished = true;
        currentAction = StepAction.FINALIZADO;
        activeIndices = new int[]{};
        activePartition = -1;
        activeIndicesInPartition = new int[0];
        for (int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public StepAction getCurrentAction() {
        return currentAction;
    }

    @Override
    public int[] getActiveIndices() {
        return activeIndices;
    }

    @Override
    public boolean[] getPartiallySortedIndices() {
        return new boolean[array.length];
    }

    @Override
    public boolean[] getFullySortedIndices() {
        return fullySorted;
    }

    @Override
    public boolean hasFloatingKey() {
        return false;
    }

    @Override
    public int getFloatingKeyValue() {
        return 0;
    }

    @Override
    public int getFloatingKeyIndex() {
        return -1;
    }

    @Override
    public int getComparisons() {
        return comparisons;
    }

    @Override
    public int getSwaps() {
        return writes;
    }

    @Override
    public int getNumberOfPartitions() {
        return numBuckets;
    }

    @Override
    public int[] getPartition(int partition) {
        List<Integer> bucket = buckets.get(partition);
        int[] arr = new int[bucket.size()];
        for (int i = 0; i < bucket.size(); i++) {
            arr[i] = bucket.get(i);
        }
        return arr;
    }

    @Override
    public int getActivePartition() {
        return activePartition;
    }

    @Override
    public int[] getActiveIndicesInPartition(int partition) {
        if (partition != activePartition) {
            return new int[0];
        }
        return activeIndicesInPartition.clone();
    }

    @Override
    public boolean isPartitionSorted(int partition) {
        if (state == BucketState.SCATTER) {
            return false;
        }
        if (state == BucketState.SORT_BUCKETS) {
            return currentBucket > partition;
        }
        if (state == BucketState.GATHER) {
            return true;
        }
        return false;
    }

    @Override
    public String getPartitionLabel(int partition) {
        return "Bucket " + partition;
    }

    @Override
    public int getInsertionIndexInPartition(int partition) {
        if (partition == activePartition && insertionIndexInBucket != -1) {
            return insertionIndexInBucket;
        }
        return -1;
    }

    @Override
    public int getInsertionValueInPartition(int partition) {
        if (partition == activePartition && insertionIndexInBucket != -1) {
            return insertionValue;
        }
        return 0;
    }
}