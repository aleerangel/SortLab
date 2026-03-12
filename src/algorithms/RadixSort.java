package algorithms;

import core.SortingAlgorithm;
import core.StepAction;
import java.util.Arrays;

public class RadixSort implements SortingAlgorithm {
    private int[] array;
    private int[] output;
    private int[] count;
    private boolean finished = false;
    private int[] activeIndices;
    private StepAction currentAction = StepAction.INICIADO;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int writes = 0;
    private int max;
    private int exp = 1;
    private int i = 0;
    private enum RadixState {FIND_MAX, COUNTING, ACCUMULATING, BUILDING, COPYING};
    private RadixState state = RadixState.FIND_MAX;

    public RadixSort(int[] array) {
        this.array = array.clone();
        int n = array.length;
        this.output = new int[n];
        this.count = new int[10];
        this.fullySorted = new boolean[n];
        this.activeIndices = new int[]{-1};
    }

    @Override
    public void nextStep() {
        if(finished) return;

        int n = array.length;

        switch(state) {
            case FIND_MAX:
                if(i == 0) max = array[0];
                if(i < n) {
                    if(array[i] > max) max = array[i];
                    activeIndices = new int[]{i};
                    currentAction = StepAction.COMPARANDO;
                    i++;
                } else {
                    i = 0;
                    state = RadixState.COUNTING;
                }
                break;
            case COUNTING: 
                if(i < n) {
                    int digit = (array[i] / exp) % 10;
                    count[digit]++;
                    activeIndices = new int[]{i};
                    currentAction = StepAction.COMPARANDO;
                    i++;
                } else {
                    state = RadixState.ACCUMULATING;
                }
                break;
            case ACCUMULATING:
                for(int j = 1; j < 10; j++) {
                    count[j] += count[j - 1];
                }
                i = n - 1;
                state = RadixState.BUILDING;
                break;
            case BUILDING: 
                if(i >= 0) {
                    activeIndices = new int[] {i};
                    currentAction = StepAction.COMPARANDO;

                    int digit = (array[i] / exp) % 10;
                    output[count[digit] - 1] = array[i];
                    count[digit]--;
                    i--;
                } else {
                    i = 0;
                    state = RadixState.COPYING;
                }
                break;
            case COPYING:
                if(i < n) {
                    if(currentAction == StepAction.COMPARANDO) {
                        currentAction = StepAction.TROCANDO;
                        activeIndices = new int[]{i};
                    } else if(currentAction == StepAction.TROCANDO) {
                        array[i] = output[i];
                        writes++;
                        i++;
                        currentAction = StepAction.COMPARANDO;
                    }
                } else {
                    exp *= 10;
                    if(max / exp > 0) {
                        Arrays.fill(count, 0);
                        Arrays.fill(output, 0);
                        i = 0;
                        state = RadixState.COUNTING;
                    } else {
                        finalizar();
                    }
                }
                break;
        }
    }

    public void finalizar() {
        finished = true;
        currentAction = StepAction.FINALIZADO;
        for(int k = 0; k < fullySorted.length; k++) {
            fullySorted[k] = true;
        }
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