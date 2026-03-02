# 📊 Visualizador de Algoritmos de Ordenação - Java

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-blue.svg)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow.svg)
![Project](https://img.shields.io/badge/Projeto-F%C3%A9rias-purple.svg)

## 🚀 Em Desenvolvimento

Este projeto está atualmente em fase ativa de desenvolvimento.

---

Este repositório contém a implementação de um **Visualizador de Algoritmos de Ordenação** desenvolvido em Java.

O projeto tem como objetivo simular **passo a passo** o funcionamento interno de algoritmos clássicos de ordenação, permitindo visualizar comparações e trocas em tempo real.

Mais do que apenas implementar algoritmos, o foco está na construção de uma **arquitetura organizada**, baseada em máquina de estados, separando claramente lógica, controle e interface gráfica.

---

## 🧠 Estrutura do Projeto

```
SortLab/
├── src/
│   ├── Main.java
│   │
│   ├── core/
│   │   ├── SortingAlgorithm.java
│   │   └── StepAction.java
│   │
│   ├── algorithms/
│   │   ├── BubbleSort.java
│   │   ├── SelectionSort.java
│   │   ├── InsertionSort.java
│   │
│   └── ui/
│       ├── MainFrame.java
│       └── SortingPanel.java
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

- ✔ Bubble Sort
- ✔ Selection Sort
- ✔ Insertion Sort

Cada algoritmo executa **uma única ação por chamada de `nextStep()`**, permitindo controle total da animação.

---

### 🖥️ ui
Camada responsável pela visualização gráfica utilizando Swing:

- `MainFrame` → Janela principal da aplicação
- `SortingPanel` → Painel responsável por desenhar o vetor como barras verticais

A interface consulta:

- `getArray()`
- `getActiveIndices()`
- `getCurrentAction()`

E redesenha a cada passo executado.

---

## 🚀 Conceitos Aplicados

| Categoria | Aplicação no Projeto |
|------------|----------------------|
| Programação Orientada a Objetos | Encapsulamento, interfaces e separação de responsabilidades |
| Máquina de Estados | Controle de execução via enum `StepAction` |
| Arquitetura em Camadas | Separação entre núcleo lógico (`core`), algoritmos e interface |
| Execução Passo a Passo | Método `nextStep()` permite simulação controlada |


---

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido durante o período de férias com o objetivo de:

- Aprofundar o entendimento de algoritmos de ordenação
- Estudar visualização de algoritmos
- Explorar arquitetura limpa em aplicações Java
- Evoluir progressivamente para uma ferramenta mais robusta

O desenvolvimento seguirá em fases, evoluindo para:

- Execução automática com controle de velocidade
- Métricas (comparações, trocas, tempo)
- Mais algoritmos (Insertion, Merge, Quick, etc.)

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

- ✔ Bubble, Selection e Insertion funcionais

- ✔ Interface gráfica básica implementada

- 🔄 Melhorias visuais em andamento

- 🔄 Novos algoritmos em planejamento

---

## 👨‍💻 Autor

**Alexandre Cesar de Souza Rangel**  
Aluno de Ciência da Computação - UFES Alegre

---

Projeto pessoal desenvolvido durante o período de férias — 2026.
