package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class InsertionSort implements SortingAlgorithm {
    private int[] array;
    private int i = 1;
    private int j;
    private int chave;
    private boolean iniciandoIteracao = true;
    private boolean mostrandoComparacao = false;
    private boolean finished = false;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices;
    private boolean[] partiallySorted;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int swaps = 0;

    public InsertionSort(int[] array) {
        this.array = array.clone();
        this.partiallySorted = new boolean[array.length];
        this.fullySorted = new boolean[array.length];
        this.activeIndices = new int[] {-1, -1};
        j = i - 1;
        chave = array[i];
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(currentAction == StepAction.INICIADO) {
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[] {j, i};
            return;
        }

        if(i >= array.length) {
            finalizar();
            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            if(j > 0) comparisons++;
            if(j >= 0 && array[j] > chave) {
                currentAction = StepAction.TROCANDO;
                activeIndices = new int[]{j, j + 1};
            } else {
                array[j + 1] = chave;
                for(int k = 0; k <= i; k++) {
                    partiallySorted[k] = true;
                }
                i++;
                if(i < array.length) {
                    j = i - 1;
                    chave = array[i];
                    currentAction = StepAction.COMPARANDO;
                    activeIndices = new int[] {j, i};
                } else {
                    finalizar();
                }
            }
        } else if (currentAction == StepAction.TROCANDO) {
            swaps++;
            array[j + 1] = array[j];
            j--;
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[] {j, i};
        }
    }

    private void finalizar() {
        for(int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
        }
        currentAction = StepAction.FINALIZADO;
        finished = true;
        activeIndices = new int[]{};
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
        return partiallySorted;
    }

    @Override 
    public boolean[] getFullySortedIndices() {
        return fullySorted;
    }

    @Override 
    public boolean hasFloatingKey() {
        return !finished && i < array.length;
    }

    @Override
    public int getFloatingKeyValue() {
        return chave;
    }

    @Override
    public int getFloatingKeyIndex() {
        return i;
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