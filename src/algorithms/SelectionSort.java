package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class SelectionSort implements SortingAlgorithm {
    private int[] array;
    private int i = 0;
    private int j = 1;
    private int minIndex = 0;
    private boolean finished = false;
    private boolean[] sorted;

    private StepAction currentAction = StepAction.INICIADO;

    public SelectionSort(int[] array) {
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
            if(j < array.length && array[j] < array[minIndex]) {
                minIndex = j;   
            }

            j++;

            avancar();
        } else if (currentAction == StepAction.TROCANDO) {
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;

            sorted[i] = true;

            i++;
            j = i + 1;
            minIndex = i;

            currentAction = StepAction.COMPARANDO;

            avancar();
        }
    }

    public void avancar() {
        if(j >= array.length) {
            currentAction = StepAction.TROCANDO;
        }

        if(i >= array.length - 1) {
            finished = true;
            currentAction = StepAction.FINALIZADO;
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
        if(currentAction == StepAction.COMPARANDO) {
            return new int[]{j, minIndex};
        }
        
        if(currentAction == StepAction.TROCANDO) {
            return new int[]{i, minIndex};
        }
        
        return new int[]{};
    }

    @Override 
    public boolean[] getSortedIndices() {
        return sorted;
    }
}