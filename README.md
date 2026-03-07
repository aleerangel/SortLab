# 📊 Visualizador de Algoritmos de Ordenação - Java

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-blue.svg)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow.svg)
![Project](https://img.shields.io/badge/Projeto-F%C3%A9rias-purple.svg)

## 🚀 Em Desenvolvimento

Este projeto está atualmente em fase ativa de desenvolvimento.

---

Este repositório contém a implementação de um **Visualizador de Algoritmos de Ordenação** desenvolvido em Java.

O projeto tem como objetivo simular **passo a passo** o funcionamento interno de algoritmos clássicos de ordenação, permitindo visualizar comparações, trocas e movimentações de memória em tempo real.

Mais do que apenas implementar algoritmos, o foco está na construção de uma **arquitetura organizada**, baseada em máquina de estados, separando claramente lógica, controle de tempo e interface gráfica.

---

## 🧠 Estrutura do Projeto

A arquitetura foi desenhada em camadas para facilitar a expansão: 

```
SortLab/
├── src/
│   ├── algorithms/
│   │   ├── BubbleSort.java
│   │   ├── InsertionSort.java
│   │   ├── MergeSort.java
│   │   └── SelectionSort.java
│   ├── core/
│   │   ├── SortingAlgorithm.java
│   │   └── StepAction.java
│   ├── ui/
│   │   ├── MainFrame.java
│   │   └── SortingPanel.java
│   ├── util/
│   │   └── LinkedStack.java
│   └── Main.java
│── Makefile
└── README.md
```

---

## 🔹 Camadas e Funcionalidades

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
- ✔ Insertion Sort (com vsualização didática de chave flutuante)
- ✔ Merge Sort (implementação iterativa utilizndo pilha explicita para simular a árvore de recursão frame a frame)

Cada algoritmo executa **uma única ação por chamada de `nextStep()`**, permitindo controle total da animação.

---

### 🛠️ util
Estruturas de dados auxiliares construídas do zero:

- `LinkedStack` → Pilha encadeada genérica utilizada para manter o estado da recursão em algoritmos complexos (como o Merge Sort) sem travar a interface gráfica.

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
| Estrutura de Dados | Implementação e uso de Pilha  `LinkedStack` | 
| Máquina de Estados | Controle de execução via enum `StepAction` |
| Simulação de Recursão | Uso de pilha explícita para manter contexto de Call Stack | 
| Arquitetura em Camadas | Separação entre núcleo lógico (`core`), algoritmos e interface |
| Execução Passo a Passo | Método `nextStep()` permite simulação controlada |


---

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido durante o período de férias com o objetivo de:

- Aprofundar o entendimento de algoritmos de ordenação
- Estudar visualização de algoritmos
- Explorar arquitetura limpa em aplicações Java
- Evoluir progressivamente para uma ferramenta mais robusta

Próximos passos planejados:

- Métricas (comparações, trocas, tempo)
- Refinamentos adicionais na UI
- Novos algoritmos

---

## ⚙️ Como Compilar e Executar

Este projeto utiliza **Makefile** para automatizar a compilação e execução.

### ✅ 1️⃣ Compilar

Na raiz do projeto:

```bash
make
```

ou

```bash
make compile
```

Isso irá:

- Criar o diretório `bin/` (caso não exista)
- Compilar todos os arquivos `.java`

---

### ▶ 2️⃣ Executar

```bash
make run
```

O comando irá:

- Compilar automaticamente (caso necessário)
- Executar a classe `Main`

---

### 🧹 3️⃣ Limpar Arquivos Compilados

```bash
make clean
```

Remove completamente o diretório `bin/`.

---

### 💡 Observação

O `Makefile` atual está configurado para ambiente Windows (uso de `dir`, `mkdir`, `rmdir`).

Em sistemas Linux/macOS será necessário adaptar os comandos.

---

## 📌 Status Atual

- ✔ Estrutura base definida

- ✔ Máquina de estados implementada

- ✔ Pilha para recursão implementada

- ✔ Bubble, Selection, Insertion e Merge funcionais

- ✔ Interface gráfica básica implementada

- 🔄 Melhorias visuais em andamento

- 🔄 Novos algoritmos em planejamento

---

## 👨‍💻 Autor

**Alexandre Cesar de Souza Rangel**  
Aluno de Ciência da Computação - UFES Alegre

---

Projeto pessoal desenvolvido durante o período de férias — 2026.
