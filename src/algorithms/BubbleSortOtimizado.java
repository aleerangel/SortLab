package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class BubbleSortOtimizado implements SortingAlgorithm {
    private int[] array;
    private int i = 0;
    private int j = 0;
    private boolean swapped = false;
    private boolean finished = false;
    private boolean[] sorted;
    private int comparisons = 0;
    private int swaps = 0;

    private StepAction currentAction = StepAction.INICIADO;

    public BubbleSortOtimizado(int[] array) {
        this.array = array.clone();
        sorted = new boolean[array.length];
    }

    @Override 
    public void nextStep() {
        if(finished) return;
        if(currentAction == StepAction.INICIADO) {
            currentAction = StepAction.COMPARANDO;
            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            comparisons++;
            if(array[j] > array[j + 1]) {
                currentAction = StepAction.TROCANDO;
            } else {
                avancar();
            }
        } else if (currentAction == StepAction.TROCANDO) {
            swapped = true;
            swaps++;
            int temp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = temp;

            currentAction = StepAction.COMPARANDO;
            avancar();
        }
    }

    public void avancar() {
        j++;
        if(j >= array.length - 1 - i) {
            if(!swapped) {
                finalizar();
            }

            j = 0;
            sorted[array.length - 1 - i] = true;
            i++;
            swapped = false;
        }
        if(i >= array.length - 1) {
            finalizar();
            return;
        }
    }

    private void finalizar() {
        currentAction = StepAction.FINALIZADO;

        for(int k = 0; k < sorted.length; k++) {
            sorted[k] = true;
        }

        finished = true;
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
        if(finished) {
            return new int[]{};
        }
        return new int[]{j, j + 1};
    }
    
    @Override
    public boolean[] getPartiallySortedIndices() {
        return new boolean[array.length];
    }

    @Override 
    public boolean[] getFullySortedIndices() {
        return sorted;
    }
    
    @Override 
    public int getComparisons() {
        return comparisons;
    }

    @Override 
    public int getSwaps() {
        return swaps;
    }
}