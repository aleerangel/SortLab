package algorithms;

import core.SortingAlgorithm;
import core.StepAction; 

public class HeapSort implements SortingAlgorithm {
    private int[] array;
    private boolean finished = false;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices = new int[]{-1, -1};
    private boolean[] partiallySorted;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int swaps = 0;
    private int heapSize;
    private int i;
    private int root, left, right;
    private int largest;
    private boolean buildingHeap = true;

    public HeapSort(int[] array) {
        this.array = array.clone();
        int n = array.length;

        partiallySorted = new boolean[n];
        fullySorted = new boolean[n];

        heapSize = n;

        i = n/2 - 1;
    }

    @Override 
    public void nextStep() {
        if(finished) return;

        if(currentAction == StepAction.INICIADO) {
            if(buildingHeap) {
                root = i;
                prepareHeapify();
            }

            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            if(left < heapSize) {
                comparisons++;

                if(array[left] > array[largest]) {
                    largest = left;
                }
            }

            if(right < heapSize) {
                comparisons++;

                if(array[right] > array[largest]) {
                    largest = right;
                }
            }

            if(largest != root) {
                currentAction = StepAction.TROCANDO;
                activeIndices = new int[]{root, largest};
            } else {
                finishHeapify();
            }

            return;
        }

        if(currentAction == StepAction.TROCANDO) {
            int temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            swaps++;

            root = largest;

            prepareHeapify();
            
            return;
        }
    }

    private void prepareHeapify() {
        left = 2*root + 1;
        right = 2*root + 2;
        largest = root;

        currentAction = StepAction.COMPARANDO;

        if(left < heapSize) {
            activeIndices = new int[]{root, left};
        } else {
            finishHeapify();
        }
    }

    private void finishHeapify() {
        if(buildingHeap) {
            i--;

            if(i >= 0) {
                root = i;
                prepareHeapify();
                return;
            }

            buildingHeap = false;

            i = array.length - 1;
        }

        if(i <= 0) {
            finalizar();
            return;
        }

        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;

        swaps++;

        fullySorted[i] = true;

        heapSize--;

        root = 0;

        i--;

        prepareHeapify();
    }

    private void finalizar() {
        for(int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
        }
        
        finished = true;
        currentAction = StepAction.FINALIZADO;

        activeIndices = new int[]{};
    }

    @Override
    public boolean isFinished(){
        return finished;
    }

    @Override
    public int[] getArray(){
        return array;
    }

    @Override
    public StepAction getCurrentAction(){
        return currentAction;
    }

    @Override
    public int[] getActiveIndices(){
        return activeIndices;
    }

    @Override
    public boolean[] getPartiallySortedIndices(){
        return partiallySorted;
    }

    @Override
    public boolean[] getFullySortedIndices(){
        return fullySorted;
    }

    @Override
    public boolean hasFloatingKey(){
        return false;
    }

    @Override
    public int getFloatingKeyValue(){
        return -1;
    }

    @Override
    public int getFloatingKeyIndex(){
        return -1;
    }

    @Override
    public int getComparisons(){
        return comparisons;
    }

    @Override
    public int getSwaps(){
        return swaps;
    }
}