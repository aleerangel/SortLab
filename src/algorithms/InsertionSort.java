package algorithms;

import core.SortingAlgorithm;
import core.StepAction;

public class InsertionSort implements SortingAlgorithm {
    private int[] array;
    private int i = 1;
    private int j;
    private int chave;
    private boolean finished = false;
    private StepAction currentAction = StepAction.INICIADO;
    private int[] activeIndices;
    private boolean[] partiallySorted;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int swaps = 0;
    private SearchStrategy strategy;
    private int left, right;
    private int targetIndex = -1;
    private int shiftIndex = -1;
    private boolean searching = true;

    public InsertionSort(int[] array, SearchStrategy strategy) {
        this.array = array.clone();
        this.strategy = strategy;

        int n = array.length;
        this.partiallySorted = new boolean[n];
        this.fullySorted = new boolean[n];
        this.activeIndices = new int[] {-1, -1};
        chave = array[i];
        if(strategy == SearchStrategy.SEQUENTIAL) {
            j = i -1;
        } else {
            left = 0;
            right = i - 1;
        }
    }

    @Override
    public void nextStep() {
        if(finished) return;

        if(currentAction == StepAction.INICIADO) {
            currentAction = StepAction.COMPARANDO;
            if(strategy == SearchStrategy.SEQUENTIAL) {
                activeIndices = new int[] {j, i};
            } else {
                activeIndices = new int[] {left, right, i};
            }
            return;
        }

        if(i >= array.length) {
            finalizar();
            return;
        }

        if(strategy == SearchStrategy.SEQUENTIAL) {
            nextStepSequential();
        } else {
            nextStepAdvanced();
        }
    }

    private void nextStepSequential() {
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

    private void nextStepAdvanced() {
        if(searching) {
            if(left <= right) {
                currentAction = StepAction.COMPARANDO;

                if(strategy == SearchStrategy.BINARY) {
                    int mid = left + (right - left) / 2;
                    activeIndices = new int[]{mid, i};
                    comparisons++;

                    if(chave < array[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else if(strategy == SearchStrategy.TERNARY) {
                    int mid1 = left + (right - left) / 3;
                    int mid2 = right - (right - left) / 3;
                    activeIndices = new int[]{mid1, mid2, i};

                    comparisons++;
                    if(chave < array[mid1]) {
                        right = mid1 - 1;
                    } else {
                        comparisons++;
                        if(chave > array[mid2]) {
                            left = mid2 + 1;
                        } else {
                            left = mid1 + 1;
                            right = mid2 - 1;
                        }
                    }
                }
                return;
            } else {
                targetIndex = left;
                shiftIndex = i - 1;
                searching = false;
                currentAction = StepAction.TROCANDO;
                activeIndices = new int[]{shiftIndex};
                return;
            }
        } else {
            currentAction = StepAction.TROCANDO;
            if(shiftIndex >= targetIndex) {
                swaps++;
                array[shiftIndex + 1] = array[shiftIndex];
                activeIndices = new int[]{shiftIndex, shiftIndex + 1};
                shiftIndex--;
            } else {
                array[targetIndex] = chave;
                swaps++;

                for(int k = 0; k <= i; k++) {
                    partiallySorted[k] = true;
                }

                i++;
                if(i < array.length) {
                    chave = array[i];
                    left = 0;
                    right = i - 1;
                    searching = true;
                    currentAction = StepAction.COMPARANDO;
                    activeIndices = new int[]{left, right};
                } else {
                    finalizar();
                }
            }
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