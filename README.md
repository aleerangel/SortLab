# 📊 SortLab - Visualizador de Algoritmos de Ordenação

![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![GUI](https://img.shields.io/badge/GUI-Swing-blue.svg)
![Status](https://img.shields.io/badge/Status-v1.0-brightgreen.svg)
![Project](https://img.shields.io/badge/Projeto-F%C3%A9rias-purple.svg)

## 🚀 Versão 1.0!

Este repositório contém a implementação completa do **SortLab**, um **Visualizador de Algoritmos de Ordenação** desenvolvido em Java utilizando **Swing**.

O objetivo do projeto é permitir a visualização **passo a passo** do funcionamento interno de uma ampla gama de algoritmos de ordenação, destacando operações como:

- comparações
- trocas e escritas
- movimentações de elementos flutuantes (chaves)
- estados intermediários e particionamento de dados (buckets)

Mais do que apenas implementar algoritmos, o projeto apresenta uma **arquitetura limpa, modular e extensível**, separando:

- lógica dos algoritmos (Máquinas de Estado)
- controle de execução multithreaded (Engine)
- renderização gráfica e métricas (UI)

---

# 🧠 Estrutura do Projeto

```text
SortLab/
├── src/
│   ├── algorithms/
│   │   ├── BubbleSort.java
│   │   ├── BubbleSortOtimizado.java
│   │   ├── BucketSort.java
│   │   ├── HeapSort.java
│   │   ├── InsertionSort.java
│   │   ├── MergeSort.java
│   │   ├── PivotStrategy.java
│   │   ├── QuickSort.java
│   │   ├── RadixSort.java
│   │   ├── SearchStrategy.java
│   │   ├── SelectionSort.java
│   │   └── ShellSort.java
│   ├── core/
│   │   ├── PartitionedSort.java
│   │   ├── SortingAlgorithm.java
│   │   ├── SortingEngine.java
│   │   └── StepAction.java
│   ├── ui/
│   │   ├── ControlPanel.java
│   │   ├── MainFrame.java
│   │   ├── MetricsPanel.java
│   │   └── SortingPanel.java
│   ├── util/
│   │   ├── ArrayGenerator.java
│   │   └── LinkedStack.java
│   └── Main.java
│── Makefile
└── README.md
```

---

# 🔹 Arquitetura

O projeto foi dividido em camadas para facilitar **manutenção, expansão e clareza arquitetural**.

## 📦 core

### `SortingAlgorithm` & `PartitionedSort`

Interfaces que definem o contrato da simulação. A interface base (`SortingAlgorithm`) transforma cada algoritmo em uma simulação pausável. A extensão `PartitionedSort` adiciona suporte visual para algoritmos não-comparativos que usam baldes/partições.

Principais métodos:
- `nextStep()` → executa um único passo lógico do algoritmo.
- `getActiveIndices()` → índices atualmente sofrendo comparação ou troca.
- `hasFloatingKey()` / `getFloatingKeyValue()` → suporte para elementos temporariamente fora do array principal (ex: Insertion Sort).

### `StepAction`

Enum que dita o **estado atual** da simulação frame a frame, permitindo renderização baseada em contexto (ex: pintar de vermelho ao comparar, laranja ao trocar):
- `INICIADO`, `COMPARANDO`, `TROCANDO`, `FINALIZADO`

### `SortingEngine`

O coração da execução. Roda em uma **thread separada**, controlando a taxa de atualização (vSync e delays dinâmicos) e coordenando com a **Swing Event Dispatch Thread (EDT)** para garantir renderizações suaves mesmo em velocidades "Turbo".

---

## 🧮 algorithms

Implementações concretas dos algoritmos, desenhados para não travar a thread principal.

Atualmente suportados:
- ✔ **Bubble Sort** (Padrão e Otimizado)
- ✔ **Selection Sort**
- ✔ **Insertion Sort** (Estratégias de busca: Sequencial, Binária e Ternária)
- ✔ **Shell Sort**
- ✔ **Heap Sort**
- ✔ **Merge Sort** (Implementação iterativa frame a frame)
- ✔ **Quick Sort** (Estratégias de pivô: Último, Meio, Mediana de 3)
- ✔ **Bucket Sort** (Com visualização de partições)
- ✔ **Radix Sort** (Com visualização de partições)

### Padrões de Projeto Aplicados
Uso extensivo do **Strategy Pattern** através de enums (`SearchStrategy`, `PivotStrategy`) para modificar o comportamento interno de algoritmos complexos (Insertion e Quick) de forma limpa.

---

## 🛠️ util

### `LinkedStack`
Estrutura de dados própria (Pilha Encadeada Genérica). Vital para simular a **call stack** de algoritmos naturalmente recursivos (MergeSort, QuickSort), permitindo a pausa da execução a qualquer momento.

### `ArrayGenerator`
Módulo responsável por alimentar a Engine com cenários variados: Vetores Aleatórios, Crescentes ou Decrescentes.

---
  
## 🖥️ ui

Camada de **interface gráfica** utilizando **Java Swing**.

- **`MainFrame`**: Janela principal e orquestradora.
- **`ControlPanel`**: Painel inferior contendo os seletores (algoritmo, tipo, tamanho) e controles de fluxo (Play, Pause, Reset, Velocidade).
- **`MetricsPanel`**: Painel superior que contabiliza métricas em tempo real (Comparações e Trocas/Escritas).
- **`SortingPanel`**: Responsável pela renderização de alto desempenho. Desenha os arrays como barras e suporta layout dividido para renderizar múltiplos buckets independentes simultaneamente em algoritmos como Radix e Bucket Sort.

---

# 🚀 Conceitos Aplicados

| Categoria | Aplicação no Projeto |
|------------|----------------------|
| Orientação a Objetos | Padrões de Projeto (Strategy), Polimorfismo e Interfaces |
| Estrutura de Dados | Implementação de pilhas genéricas (`LinkedStack`) | 
| Máquina de Estados | Execução assíncrona orientada a estados (`StepAction`) |
| Desrecursivação | Pilha explícita para manter contexto de Merge e Quick Sort | 
| Arquitetura em Camadas | Isolamento estrito entre Domínio, Engine e Interface |
| Computação Gráfica | Pintura customizada (`paintComponent`), proporções dinâmicas |
| Análise de Algoritmos | Coleta de métricas em tempo real |

---

## ⚙️ Como Compilar e Executar

O projeto utiliza **Makefile** para orquestração no ambiente Windows.

### ✅ 1️⃣ Compilar

Na raiz do projeto:
```bash
make compile
```

### ▶ 2️⃣ Executar

```bash
make run
```
*(Compila automaticamente caso existam alterações pendentes e executa a aplicação).*

### 🧹 3️⃣ Limpar Arquivos Compilados

```bash
make clean
```

*Nota: Para rodar em ambientes Linux/macOS, adapte os comandos do Makefile (`dir`, `mkdir`, `rmdir` para equivalentes Unix).*

---

## 📌 Status Atual

- ✔ **Versão 1.0 Finalizada!**
- ✔ Máquina de estados robusta.
- ✔ Mais de 13 variações de algoritmos e estratégias mapeadas.
- ✔ Suporte a métricas em tempo real.
- ✔ Renderização para ordenações baseadas em distribuição (Radix/Bucket).
- ✔ Controles avançados de taxa de atualização e velocidade gráfica.

---

## 👨‍💻 Autor

**Alexandre Cesar de Souza Rangel** Aluno de Ciência da Computação - UFES Alegre

---
*Projeto pessoal desenvolvido durante o período de férias — 2026.*
