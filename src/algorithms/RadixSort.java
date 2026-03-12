package algorithms;

import core.StepAction;
import core.PartitionedSort;
import java.util.ArrayList;
import java.util.List;

public class RadixSort implements PartitionedSort {
    private int[] array;
    
    private List<List<Integer>> buckets;
    
    private static final int NUM_BUCKETS = 10;
    
    private boolean finished = false;
    
    private StepAction currentAction = StepAction.INICIADO;
    
    private int[] activeIndices;
    
    private boolean[] fullySorted;
    
    private int comparisons = 0;
    private int writes = 0;
    
    private int max;
    
    private int exp = 1;
    
    private int i = 0;
    private int currentDigit = 0;
    private int indexInBucket = 0;
    
    private enum RadixState { 
        FIND_MAX,
        SCATTER,
        GATHER,
        FINISHED
    }
    private RadixState state = RadixState.FIND_MAX;
    
    private int activePartition = -1;
    private int[] activeIndicesInPartition = new int[0];
    private int insertionIndexInPartition = -1;
    private int insertionValueInPartition = 0;
    
    public RadixSort(int[] array) {
        this.array = array.clone();
        this.fullySorted = new boolean[array.length];
        this.activeIndices = new int[]{-1};
        
        buckets = new ArrayList<>(NUM_BUCKETS);
        for (int j = 0; j < NUM_BUCKETS; j++) {
            buckets.add(new ArrayList<>());
        }
    }
    
    @Override
    public void nextStep() {
        if (finished) return;
        
        int n = array.length;
        
        switch (state) {
            case FIND_MAX:
                if (i == 0) {
                    max = array[0];
                }
                if (i < n) {
                    if (array[i] > max) {
                        max = array[i];
                    }
                    activeIndices = new int[]{i};
                    currentAction = StepAction.COMPARANDO;
                    i++;
                } else {
                    i = 0;
                    state = RadixState.SCATTER;
                    limparBuckets();
                }
                break;
                
            case SCATTER:
                if (i < n) {
                    int digito = (array[i] / exp) % 10;
                    
                    buckets.get(digito).add(array[i]);
                    writes++;
                    
                    activeIndices = new int[]{i};
                    activePartition = digito;
                    int posInBucket = buckets.get(digito).size() - 1;
                    activeIndicesInPartition = new int[]{posInBucket};
                    currentAction = StepAction.TROCANDO;
                    
                    i++;
                } else {
                    state = RadixState.GATHER;
                    i = 0;
                    currentDigit = 0;
                    indexInBucket = 0;
                }
                break;
                
            case GATHER:
                if (currentDigit < NUM_BUCKETS) {
                    List<Integer> bucket = buckets.get(currentDigit);
                    
                    if (indexInBucket < bucket.size()) {
                        int valor = bucket.get(indexInBucket);
                        array[i] = valor;
                        writes++;
                        
                        activeIndices = new int[]{i};
                        activePartition = currentDigit;
                        activeIndicesInPartition = new int[]{indexInBucket};
                        currentAction = StepAction.TROCANDO;
                        
                        i++;
                        indexInBucket++;
                    } else {
                        currentDigit++;
                        indexInBucket = 0;
                    }
                } else {
                    exp *= 10;
                    
                    if (max / exp > 0) {
                        limparBuckets();
                        i = 0;
                        state = RadixState.SCATTER;
                    } else {
                        finalizar();
                    }
                }
                break;
                
            case FINISHED:
                break;
        }
    }
    
    private void limparBuckets() {
        for (List<Integer> bucket : buckets) {
            bucket.clear();
        }
        activePartition = -1;
        activeIndicesInPartition = new int[0];
    }
    
    private void finalizar() {
        finished = true;
        currentAction = StepAction.FINALIZADO;
        for (int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
        }
        activeIndices = new int[]{};
        activePartition = -1;
        activeIndicesInPartition = new int[0];
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
        return NUM_BUCKETS;
    }
    
    @Override
    public int[] getPartition(int partition) {
        List<Integer> bucket = buckets.get(partition);
        int[] result = new int[bucket.size()];
        for (int j = 0; j < bucket.size(); j++) {
            result[j] = bucket.get(j);
        }
        return result;
    }
    
    @Override
    public int getActivePartition() {
        return activePartition;
    }
    
    @Override
    public int[] getActiveIndicesInPartition(int partition) {
        if (partition == activePartition) {
            return activeIndicesInPartition;
        }
        return new int[0];
    }
    
    @Override
    public boolean isPartitionSorted(int partition) {
        return false;
    }
    
    @Override
    public String getPartitionLabel(int partition) {
        return "Digito " + partition;
    }
    
    @Override
    public int getInsertionIndexInPartition(int partition) {
        return -1;
    }
    
    @Override
    public int getInsertionValueInPartition(int partition) {
        return 0;
    }
}
