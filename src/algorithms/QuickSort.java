package algorithms; 

import core.SortingAlgorithm;
import core.StepAction;
import util.LinkedStack;

public class QuickSort implements SortingAlgorithm {
    private int[] array;
    private boolean finished;
    private StepAction currentAction;
    private int[] activeIndices;
    private boolean[] partiallySorted;
    private boolean[] fullySorted;
    private int comparisons = 0;
    private int swaps = 0;
    private LinkedStack<QuickFrame> stack;
    private PivotStrategy strategy;
    private int pivotValue;
    private int pivotIndex;
    private boolean pivotAtivo = false;
    
    public QuickSort(int[] array, PivotStrategy strategy) {
        this.array = array.clone();
        this.strategy = strategy;
        
        int n = array.length;

        this.partiallySorted = new boolean[n];
        this.fullySorted = new boolean[n];
        this.activeIndices = new int[]{-1, -1};

        this.currentAction = StepAction.INICIADO;
        this.finished = false;

        stack = new LinkedStack<>();
        stack.push(new QuickFrame(0, n - 1));
    }

    private class QuickFrame {
        int low, high;
        int pivot;
        int i, j;
        boolean pivotEscolhido = false;
        boolean pivotMovido = false;
        boolean partitionStarted = false;

        QuickFrame(int low, int high) {
            this.low = low;
            this.high = high;
        }
    }

    @Override 
    public void nextStep() {
        if(finished) return;

        if(stack.isEmpty()) {
            finalizar();
            return;
        }

        QuickFrame frame = stack.peek();

        if(frame.low >= frame.high) {
            fullySorted[frame.low] = true;
            stack.pop();
            return;
        }

        if(!frame.pivotEscolhido) {
            frame.pivot = escolherPivo(frame.low, frame.high);

            pivotIndex = frame.pivot;
            pivotValue = array[pivotIndex];
            pivotAtivo = true;

            activeIndices = new int[]{pivotIndex};
            currentAction = StepAction.COMPARANDO;

            frame.pivotEscolhido = true;
            return;
        }

        if(!frame.pivotMovido){
            if (currentAction == StepAction.COMPARANDO) {
                activeIndices = new int[]{frame.pivot, frame.high};
                currentAction = StepAction.TROCANDO; 
                return;
            } else if (currentAction == StepAction.TROCANDO) {
                swap(frame.pivot, frame.high);
                swaps++;

                pivotIndex = frame.high;

                frame.i = frame.low - 1;
                frame.j = frame.low;

                frame.pivotMovido = true;
                frame.partitionStarted = true;
                
                currentAction = StepAction.COMPARANDO; 
                return;
            }
        }

        if(frame.j < frame.high) {
            if(currentAction == StepAction.COMPARANDO) {
                comparisons++;
                activeIndices = new int[] {frame.j, pivotIndex};

                if(array[frame.j] <= pivotValue) {
                    frame.i++;
                    currentAction = StepAction.TROCANDO;
                    activeIndices = new int[] {frame.i, frame.j};
                } else {
                    frame.j++;
                }
            } else if(currentAction == StepAction.TROCANDO) {
                swap(frame.i, frame.j);
                swaps++;
                frame.j++;
                currentAction = StepAction.COMPARANDO;
            }
            return;
        }

        if(currentAction == StepAction.COMPARANDO) {
            currentAction = StepAction.TROCANDO;
            activeIndices = new int[] {frame.i + 1, frame.high};
        } else if(currentAction == StepAction.TROCANDO) {
            int pivotFinal = frame.i + 1;

            swap(pivotFinal, frame.high);
            swaps++;

            pivotAtivo = false;
            fullySorted[pivotFinal] = true;

            stack.pop();

            if(pivotFinal + 1 < frame.high) {
                stack.push(new QuickFrame(pivotFinal + 1, frame.high));
            }

            if(frame.low < pivotFinal - 1) {
                stack.push(new QuickFrame(frame.low, pivotFinal - 1));
            }

            currentAction = StepAction.COMPARANDO;
            activeIndices = new int[] {};
        }
    }

    private int escolherPivo(int low, int high) {
        switch(strategy) {
            case LAST:
                return high;
            case MIDDLE: 
                return (low + high) / 2;
            case MEDIAN_OF_THREE:
                int mid = (low + high) / 2;

                int a = array[low];
                int b = array[mid];
                int c = array[high];

                if((a <= b && b <= c) || (c <= b && b <= a)) return mid;
                if((b <= a && a <= c) || (c <= a && a <= b)) return low;
                return high;
        }

        return high;
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void finalizar() {
        finished = true;
        currentAction = StepAction.FINALIZADO;

        for(int i = 0; i < fullySorted.length; i++) {
            fullySorted[i] = true;
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
        return partiallySorted;
    }

    @Override
    public boolean[] getFullySortedIndices() {
        return fullySorted;
    }

    @Override
    public boolean hasFloatingKey() {
        return pivotAtivo;
    }

    @Override
    public int getFloatingKeyValue() {
        return pivotValue;
    }

    @Override
    public int getFloatingKeyIndex() {
        return pivotIndex;
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