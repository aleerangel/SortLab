package util;

public class LinkedStack<T> {
    private Node<T> topo;
    private int tamanho;

    private static class Node<T> {
        T dado;
        Node<T> proximo;
        Node(T dado) {
            this.dado = dado;
        }
    }

    public LinkedStack() {
        topo = null;
        tamanho = 0;
    }

    public void push(T elemento) {
        Node<T> novo = new Node<>(elemento);
        novo.proximo = topo;
        topo = novo;
        tamanho++;
    }

    public T pop() {
        if(isEmpty()) {
            throw new IllegalStateException("Pilha vazia");
        }
        T dado = topo.dado;
        topo = topo.proximo;
        tamanho--;
        return dado;
    }

    public T peek() {
        if(isEmpty()) {
            throw new IllegalStateException("Pilha vazia");
        }
        return topo.dado;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public int size() {
        return tamanho;
    }
}