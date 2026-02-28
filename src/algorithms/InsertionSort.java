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

    public InsertionSort(int[] array) {
        activeIndices = new int[2];
        this.array = array;
        this.partiallySorted = new boolean[array.length];
        this.fullySorted = new boolean[array.length];
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(i >= array.length) {
            currentAction = StepAction.FINALIZADO;

            for(int k = 0; k < fullySorted.length; k++) {
                fullySorted[k] = true;
            }

            finished = true;
            return;
        }

        if(iniciandoIteracao) {
            chave = array[i];
            j = i - 1;
            iniciandoIteracao = false;
            return;
        }

        if(j >= 0 && array[j] > chave) {
            if(!mostrandoComparacao) {
                currentAction = StepAction.COMPARANDO;
                activeIndices[0] = j;
                activeIndices[1] = i;
                mostrandoComparacao = true;
                return;
            }

            currentAction = StepAction.TROCANDO;
            activeIndices[0] = j;
            activeIndices[1] = j + 1;
            array[j + 1] = array[j];
            j--;
            mostrandoComparacao = false;
            return;
        }

        array[j + 1] = chave;
        for(int k = 0; k < i; k++) {
            partiallySorted[k] = true;
        }
        i++;
        iniciandoIteracao = true;
        currentAction = StepAction.COMPARANDO;
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
}