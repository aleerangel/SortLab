package algorithms;

import core.SortingAlgorithm;
import core.StepAction;
import java.util.ArrayList;
import java.util.List;

public class BucketSort implements SortingAlgorithm {
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
    private enum BucketState {SCATTER, SORT_BUCKETS, GATHER}
    private BucketState state = BucketState.SCATTER;
    private int i = 0;
    private int currentBucket = 0;
    private int indexInBucket = 0;
    private int writeIndex = 0;

    public BucketSort(int[] array) {
        this.array = array.clone();
        int n = array.length;
        this.fullySorted = new boolean[n];
        this.activeIndices = new int[] {-1};

        maxVal = array[0];
        for(int val : array) {
            if(val > maxVal) maxVal = val;
        }

        numBuckets = (int) Math.sqrt(n);
        if(numBuckets < 1) numBuckets = 1;

        buckets = new ArrayList<>(numBuckets);
        for(int j = 0; j < numBuckets; j++) {
            buckets.add(new ArrayList<>());
        }
    }  

    @Override 
    public void nextStep() {
        if(finished) return;

        switch(state) {
            case SCATTER:
                if(i < array.length) {
                    currentAction = StepAction.COMPARANDO;
                    activeIndices = new int[]{i};

                    int bucketIndex = (array[i] * numBuckets) / (maxVal + 1);
                    buckets.get(bucketIndex).add(array[i]);

                    comparisons++;
                    i++;
                } else {
                    state = BucketState.SORT_BUCKETS;
                }
                break;
            case SORT_BUCKETS:
                for(List<Integer> bucket : buckets) {
                    for(int k = 1; k < bucket.size(); k++) {
                        int chave = bucket.get(k);
                        int j = k -1;

                        comparisons++;
                        while(j >= 0 && bucket.get(j) > chave) {
                            j--;
                            if(j >= 0) comparisons++;
                        }
                        bucket.set(j + 1, chave);
                    }
                }
                state = BucketState.GATHER;
                currentAction = StepAction.TROCANDO;
                break;
            case GATHER: 
                if(currentBucket < numBuckets) {
                    List<Integer> bucket = buckets.get(currentBucket);

                    if(indexInBucket < bucket.size()) {
                        activeIndices = new int[]{writeIndex};
                        currentAction = StepAction.TROCANDO;

                        array[writeIndex] = bucket.get(indexInBucket);
                        fullySorted[writeIndex] = true;
                        writes++;

                        indexInBucket++;
                        writeIndex++;
                    } else {
                        currentBucket++;
                        indexInBucket = 0;
                    }
                } else {
                    finalizar();
                }
                break;
        }
    }

    private void finalizar() {
        finished = true;
        currentAction = StepAction.FINALIZADO;
        activeIndices = new int[]{};
        for(int k = 0; k < fullySorted.length; k++) {
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
}