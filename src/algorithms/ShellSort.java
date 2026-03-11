package algorithms;

import core.SortingAlgorithm;
import core.StepAction; 

public class ShellSort implements SortingAlgorithm {
    private int[] array;
    private int gap;
    private int i, j;
    private int chave;  
    private boolean finished = false;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices = new int[]{-1, -1};
    private boolean[] partiallySorted;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int swaps = 0;

    public ShellSort(int[] array) {
        this.array = array.clone();

        int n = array.length;

        partiallySorted = new boolean[n];
        fullySorted = new boolean[n];

        gap = n / 2;

        i = gap;

        if(i < n) {
            chave = this.array[i];
            j = i - gap;
        }
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(currentAction == StepAction.INICIADO) {
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[]{j, i};
            return;
        }

        if(i >= array.length) {
            gap /= 2;

            if(gap == 0) {
                finalizar();
                return;
            }

            i = gap;
            chave = array[i];
            j = i - gap;

            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[]{j, i};
            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            if(j >= 0 && array[j] > chave) comparisons++;

            if(j >= 0 && array[j] > chave) {
                currentAction = StepAction.TROCANDO;
                activeIndices = new int[]{j, j + gap};
            } else {
                array[j + gap] = chave;
                swaps++;
                if(gap == 1) {
                    for(int k = 0; k <= i; k++) {
                        partiallySorted[k] = true;
                    }
                }
                i++;
                if(i < array.length) {
                    chave = array[i];
                    j = i - gap;
                    currentAction = StepAction.COMPARANDO;
                    activeIndices = new int[] {j, i};
                } else {
                    currentAction = StepAction.COMPARANDO;
                }
            }
        } else if(currentAction == StepAction.TROCANDO) {
            array[j + gap] = array[j];
            swaps++;
            j -= gap;
            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[] {j, i};
        }
    }

    private void finalizar() {
        currentAction = StepAction.FINALIZADO;

        for(int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
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
    public boolean hasFloatingKey(){
        return !finished && gap == 1 && i < array.length;
    }

    @Override
    public int getFloatingKeyValue(){
        return chave;
    }

    @Override
    public int getFloatingKeyIndex(){
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