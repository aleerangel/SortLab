# 📊 Visualizador de Algoritmos de Ordenação - Java

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-blue.svg)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow.svg)
![Project](https://img.shields.io/badge/Projeto-F%C3%A9rias-purple.svg)

## 🚀 Em Desenvolvimento

Este projeto está atualmente em fase ativa de desenvolvimento.

---

Este repositório contém a implementação de um **Visualizador de Algoritmos de Ordenação** desenvolvido em Java utilizando **Swing**.

O objetivo do projeto é permitir a visualização **passo a passo** do funcionamento interno de algoritmos clássicos de ordenação, destacando operações como:

- comparações
- trocas
- movimentações de elementos
- estados intermediários do algoritmo

Mais do que apenas implementar algoritmos, o projeto foca na construção de uma **arquitetura organizada e extensível**, separando claramente:

- lógica dos algoritmos
- controle de execução
- renderização gráfica

Cada algoritmo funciona como uma **máquina de estados pausável**, permitindo controle total da execução e da animação.

---

# 🧠 Estrutura do Projeto

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
│   │   ├── SortingEngine.java
│   │   └── StepAction.java
│   ├── ui/
│   │   ├── MainFrame.java
│   │   └── SortingPanel.java
│   ├── util/
│   │   └── ArrayGenerator.java
│   │   └── LinkedStack.java
│   └── Main.java
│── Makefile
└── README.md
```

---

# 🔹 Arquitetura

O projeto foi dividido em camadas para facilitar **manutenção, expansão e clareza arquitetural**.

## 📦 core

### `SortingAlgorithm`

Interface que define o contrato que todos os algoritmos devem seguir.

Principais métodos:

- `nextStep()` → executa um único passo do algoritmo
- `isFinished()` → indica se a ordenação terminou
- `getArray()` → retorna o vetor atual
- `getActiveIndices()` → índices atualmente envolvidos na operação
- `getCurrentAction()` → estado atual da execução
- métricas como comparações e trocas

Essa abordagem transforma os algoritmos em **simulações controladas passo a passo**.

---

### `StepAction`

Enum que representa o **estado atual do algoritmo** durante a execução.

Exemplos de estados:

- `INICIADO`
- `COMPARANDO`
- `TROCANDO`
- `FINALIZADO`


Esse estado é utilizado pela interface gráfica para **destacar visualmente as operações**.

---

### `SortingEngine`

Responsável por controlar **o motor de execução da simulação**.

Funções principais:

- Executar o algoritmo em **thread separada**
- Controlar **velocidade da simulação**
- Sincronizar **atualizações da interface**
- Garantir execução **suave e pausável**

O motor utiliza:

- `Thread`
- controle de delay
- sincronização com a **Swing Event Dispatch Thread**

---

## 🧮 algorithms
Implementações concretas dos algoritmos de ordenação.

Atualmente:

- ✔ Bubble Sort
- ✔ Selection Sort
- ✔ Insertion Sort 
- ✔ Merge Sort 

Cada algoritmo executa **uma única ação por chamada de `nextStep()`**, permitindo controle total da animação.

### Destaques

**Insertion Sort**

- visualização didática do elemento **"chave" flutuando** durante inserção.

**Merge Sort**

- implementação **iterativa**
- utiliza **pilha explícita** (`LinkedStack`) para simular a árvore de recursão frame a frame.

---

## 🛠️ util

### `LinkedStack`

Implementação própria de uma **pilha encadeada genérica**.

Utilizada principalmente para:

- simular a **call stack de algoritmos recursivos**
- permitir execução passo a passo sem usar recursão real.

---

### `ArrayGenerator`

Responsável por gerar vetores iniciais para teste:

- aleatórios
- ordenados crescentes
- ordenados decrescentes

Permite visualizar **diferentes comportamentos dos algoritmos**.

---
  
## 🖥️ ui
Camada responsável pela **interface gráfica** utilizando **Java Swing**.

### `MainFrame`

Janela principal da aplicação.

Contém:

- seletor de algoritmo
- seletor de tipo de vetor
- controle de tamanho do vetor
- controle de velocidade
- botões de execução (Play / Pause / Reset)
- métricas de execução

---

### `SortingPanel`

Responsável pela **renderização do vetor**.

Cada elemento do array é desenhado como uma **barra vertical**, onde:

- a altura representa o valor
- cores indicam operações em andamento

O painel consulta continuamente:
- getArray()
- getActiveIndices()
- getCurrentAction()
para desenhar o estado atual da execução.

---


# 🚀 Conceitos Aplicados

| Categoria | Aplicação no Projeto |
|------------|----------------------|
| Programação Orientada a Objetos | Encapsulamento, interfaces e separação de responsabilidades |
| Estrutura de Dados | Implementação de pilha encadeada (`LinkedStack`) | 
| Máquina de Estados | Controle de execução via `StepAction` |
| Simulação de Recursão | Uso de pilha explícita para manter contexto de execução | 
| Arquitetura em Camadas | Separação entre lógica, engine e UI |
| Concorrência | Execução do algoritmo em thread separada |
| Visualização de Algoritmos | Renderização gráfica passo a passo |
| Controle de Simulação | Execução via `nextStep()` |


---

# 🎯 Objetivo do Projeto

Este projeto foi desenvolvido durante o período de férias com o objetivo de:

- Aprofundar o entendimento de **algoritmos de ordenação**
- Estudar **visualização de algoritmos**
- Explorar arquitetura limpa em aplicações Java
- Evoluir progressivamente para uma ferramenta mais robusta
- Desenvolver um projeto sólido para **portfólio**

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
