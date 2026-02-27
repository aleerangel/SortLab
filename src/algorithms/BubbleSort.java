package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class BubbleSort implements SortingAlgorithm {
    private int[] array;
    private int i = 0;
    private int j = 0;
    private boolean finished = false;
    private boolean[] sorted;

    private StepAction currentAction = StepAction.INICIADO;

    public BubbleSort(int[] array) {
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
            if(array[j] > array[j + 1]) {
                currentAction = StepAction.TROCANDO;
            } else {
                avancar();
            }
        } else if (currentAction == StepAction.TROCANDO) {
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
            j = 0;
            i++;
            sorted[array.length - i] = true;
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
        if(finished) {
            return new int[]{};
        }
        return new int[]{j, j + 1};
    }
    
    @Override 
    public boolean[] getSortedIndices() {
        return sorted;
    }
}

