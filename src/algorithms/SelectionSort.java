package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class SelectionSort implements SortingAlgorithm {
    private int[] array;
    private int i = 0;
    private int j;
    private int minIndex;
    private boolean finished = false;
    private boolean[] sorted;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices;

    public SelectionSort(int[] array) {
        this.array = array.clone();
        sorted = new boolean[array.length];
        activeIndices = new int[2];
        j = i + 1;
        minIndex = i;
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(currentAction == StepAction.INICIADO) {
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[]{j, minIndex};
            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            if(j < array.length && array[j] < array[minIndex]) {
                minIndex = j;   
            }

            j++;

            if(j < array.length) {
                activeIndices = new int[]{j, minIndex};
            } else {
                currentAction = StepAction.TROCANDO;
                activeIndices = new int[]{j, minIndex};
            }
        } else if (currentAction == StepAction.TROCANDO) {
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;

            sorted[i] = true;

            i++;
            if(i < array.length - 1) {
                j = i + 1;
                minIndex = i;
                currentAction = StepAction.COMPARANDO;
                activeIndices = new int[]{j, minIndex};
            } else {
                for(int k = 0; k < sorted.length; k++) {
                    sorted[k] = true;
                }
                currentAction = StepAction.FINALIZADO;
                finished = true;
                activeIndices = new int[]{};
            }
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
        return sorted;
    }
}