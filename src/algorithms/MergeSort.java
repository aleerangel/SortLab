package algorithms;

import core.SortingAlgorithm;
import core.StepAction;
import util.LinkedStack;

public class MergeSort implements SortingAlgorithm {
    private int[] array;                   
    private boolean finished;               
    private StepAction currentAction;       
    private int[] activeIndices;            
    private boolean[] partiallySorted;      
    private boolean[] fullySorted;          

    private LinkedStack<MergeFrame> stack;  

    public MergeSort(int[] array) {
        this.array = array.clone();
        this.finished = false;
        this.currentAction = StepAction.INICIADO;
        this.activeIndices = new int[] {-1, -1}; 
        this.partiallySorted = new boolean[array.length];
        this.fullySorted = new boolean[array.length];

        stack = new LinkedStack<>();
        stack.push(new MergeFrame(0, array.length - 1));
    }

    private class MergeFrame {
        int left, right;          
        int mid;                  
        int i, j, k;              
        int[] temp;                
        boolean dividindo;        
        int copyIdx;

        MergeFrame(int left, int right) {
            this.left = left;
            this.right = right;
            this.dividindo = true; 
            this.copyIdx = 0;
        }
    }

    @Override
    public void nextStep() {
        if (finished) return;

        if (stack.isEmpty()) {
            finished = true;
            currentAction = StepAction.FINALIZADO;
            for (int i = 0; i < fullySorted.length; i++) {
                fullySorted[i] = true;
            }
            activeIndices = new int[] {};
            return;
        }

        MergeFrame frame = stack.peek();

        if (frame.dividindo) {
            if (frame.left >= frame.right) {
                stack.pop();
                partiallySorted[frame.left] = true;
                return; 
            }

            int mid = frame.left + (frame.right - frame.left) / 2;
            frame.mid = mid;
            
            frame.dividindo = false;

            MergeFrame leftFrame = new MergeFrame(frame.left, mid);
            MergeFrame rightFrame = new MergeFrame(mid + 1, frame.right);

            stack.push(rightFrame);
            stack.push(leftFrame);

            activeIndices = new int[]{};
            return;
        }

        else {
            if (frame.temp == null) {
                frame.i = frame.left;
                frame.j = frame.mid + 1;
                frame.k = 0;
                frame.copyIdx = 0;
                frame.temp = new int[frame.right - frame.left + 1];
                activeIndices = new int[] {frame.i, frame.j};
                currentAction = StepAction.COMPARANDO;
                return;
            }

            if (frame.k < frame.temp.length) {
                if (frame.i <= frame.mid && frame.j <= frame.right) {
                    activeIndices = new int[] {frame.i, frame.j};
                    if (array[frame.i] <= array[frame.j]) {
                        frame.temp[frame.k] = array[frame.i];
                        frame.i++;
                    } else {
                        frame.temp[frame.k] = array[frame.j];
                        frame.j++;
                    }
                    frame.k++;
                    currentAction = StepAction.COMPARANDO;
                }
                else if (frame.i <= frame.mid) {
                    activeIndices = new int[] {frame.i};
                    frame.temp[frame.k] = array[frame.i];
                    frame.i++;
                    frame.k++;
                    currentAction = StepAction.TROCANDO;
                }
                else if (frame.j <= frame.right) {
                    activeIndices = new int[] {frame.j};
                    frame.temp[frame.k] = array[frame.j];
                    frame.j++;
                    frame.k++;
                    currentAction = StepAction.TROCANDO;
                }
                return;
            } 

            if (frame.copyIdx < frame.temp.length) {
                int targetIndex = frame.left + frame.copyIdx;
                array[targetIndex] = frame.temp[frame.copyIdx];
                
                activeIndices = new int[] {targetIndex};
                currentAction = StepAction.TROCANDO; 
                frame.copyIdx++;
                return;
            }

            for (int idx = frame.left; idx <= frame.right; idx++) {
                partiallySorted[idx] = true;
            }

            stack.pop();

            if (stack.isEmpty()) {
                finished = true;
                currentAction = StepAction.FINALIZADO;
                for (int i = 0; i < fullySorted.length; i++) {
                    fullySorted[i] = true;
                }
                activeIndices = new int[] {};
            } else {
                currentAction = StepAction.COMPARANDO;
                activeIndices = new int[] {};
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
        return partiallySorted;
    }

    @Override
    public boolean[] getFullySortedIndices() {
        return fullySorted;
    }
}