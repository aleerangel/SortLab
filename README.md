# 📊 Visualizador de Algoritmos de Ordenação - Java

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-blue.svg)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow.svg)
![Project](https://img.shields.io/badge/Projeto-F%C3%A9rias-purple.svg)

Este repositório contém a implementação de um **Visualizador de Algoritmos de Ordenação** desenvolvido em Java.

O projeto tem como objetivo simular **passo a passo** o funcionamento interno de algoritmos clássicos de ordenação, permitindo visualizar comparações e trocas em tempo real.

Mais do que apenas implementar algoritmos, o foco está na construção de uma **arquitetura organizada**, baseada em máquina de estados, separando claramente lógica, controle e interface gráfica.

---

## 🧠 Estrutura do Projeto

```
meu-visualizador-ordenacao/
├── src/
│   ├── Main.java
│   │
│   ├── core/
│   │   ├── SortingAlgorithm.java
│   │   └── StepAction.java
│   │
│   ├── algorithms/
│   │   ├── BubbleSort.java
│   │   ├── QuickSort.java        (em desenvolvimento)
│   │   └── MergeSort.java        (em desenvolvimento)
│   │
│   └── ui/
│       ├── VisualizerFrame.java
│       └── ArrayPanel.java
│
└── README.md
```

---

## 🔹 Camadas do Projeto

### 📦 core
Contém o “coração” da aplicação:

- `SortingAlgorithm` → Interface base que define o contrato dos algoritmos.
- `StepAction` → Enum que representa o estado atual da execução:
  - `COMPARANDO`
  - `TROCANDO`
  - `FINALIZADO`

Essa abordagem transforma cada algoritmo em uma **máquina de estados pausável**.

---

### 🧮 algorithms
Implementações concretas dos algoritmos.

Atualmente:

- ✔ Bubble Sort (implementado passo a passo)
- 🔄 Quick Sort (planejado)
- 🔄 Merge Sort (planejado)

Cada algoritmo executa **uma única ação por chamada de `nextStep()`**, permitindo controle total da animação.

---

### 🖥️ ui
Camada responsável pela visualização gráfica utilizando Swing:

- `VisualizerFrame` → Janela principal
- `ArrayPanel` → Painel que desenha as barras do vetor

A UI consulta o estado atual do algoritmo e redesenha a cada passo.

---

## 🚀 Conceitos Aplicados

| Categoria | Aplicação no Projeto |
|------------|----------------------|
| Programação Orientada a Objetos | Encapsulamento, interfaces e separação de responsabilidades |
| Máquina de Estados | Controle de execução via enum `StepAction` |
| Arquitetura em Camadas | Separação entre núcleo lógico (`core`), algoritmos e interface |
| Execução Passo a Passo | Método `nextStep()` permite simulação controlada |
| Clonagem de Dados | Preservação do vetor original ao iniciar algoritmo |

---

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido durante o período de férias com o objetivo de:

- Aprofundar o entendimento de algoritmos de ordenação
- Estudar visualização de algoritmos
- Explorar arquitetura limpa em aplicações Java
- Evoluir progressivamente para uma ferramenta mais robusta

O desenvolvimento seguirá em fases, começando pela implementação básica do Bubble Sort e evoluindo para múltiplos algoritmos, controle de velocidade, métricas e melhorias arquiteturais.

---

## ⚙️ Como Compilar e Executar

### 1️⃣ Compilar o Código

No terminal, na raiz do projeto:

```bash
javac -d out src/**/*.java
```

### 2️⃣ Executar

```bash
java -cp out Main
```

---

## 📌 Status Atual

- ✔ Estrutura base definida
- ✔ Máquina de estados implementada
- ✔ Bubble Sort passo a passo funcional
- 🔄 Interface gráfica em desenvolvimento
- 🔄 Novos algoritmos em planejamento

---

## 👨‍💻 Autor

**Alexandre Cesar de Souza Rangel**  
Aluno de Ciência da Computação - UFES Alegre

---

Projeto pessoal desenvolvido durante o período de férias — 2026.
