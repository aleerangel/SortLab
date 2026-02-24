import algorithms.BubbleSort;
import algorithms.SelectionSort;
import core.SortingAlgorithm;
import core.StepAction;
import java.util.Arrays;

public class Main {
    public static void imprimirEstado(SortingAlgorithm algoritmo) {
        int[] arr = algoritmo.getArray();
        int[] ativos = algoritmo.getActiveIndices();
        StepAction acao = algoritmo.getCurrentAction();

        if(acao == StepAction.FINALIZADO) {
            System.out.println("Ordenado: " + Arrays.toString(arr));
            return;
        }

        System.out.printf("%-12s: ", acao); 
        for (int k = 0; k < arr.length; k++) {
            if (k == ativos[0] || k == ativos[1]) {
                System.out.print("[" + arr[k] + "] ");
            } else {
                System.out.print(" " + arr[k] + "  ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] vetorTeste = {5, 2, 9, 1, 5, 6};

        SortingAlgorithm algoritmo = new SelectionSort(vetorTeste);

        System.out.println("Vetor Inicial: " + Arrays.toString(vetorTeste));
        System.out.println("--------------------------------------------------");

        while (!algoritmo.isFinished()) {
            imprimirEstado(algoritmo);
            algoritmo.nextStep();
            
            try {
                Thread.sleep(400); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        imprimirEstado(algoritmo);
    }
}