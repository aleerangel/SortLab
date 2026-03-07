package util;

public class ArrayGenerator {
    public static int[] generateArray(int size, String type) {
        int[] array = new int[size];

        switch (type) {
            case "Crescente": 
                for(int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                break;
            case "Decrescente": 
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
            case "Aleatorio":
            default:
                for(int i = 0; i < size; i++) {
                    array[i] = (int) (Math.random() * 100) + 1;
                }
                break;
        }

        return array;
    }
}