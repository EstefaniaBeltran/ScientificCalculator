

````md
# Calculadora Científica - ANTLR

Este proyecto consiste en una calculadora científica realizada en Java utilizando ANTLR.

## Requisitos

- Java
- ANTLR 4

## Estructura del proyecto

```text
ScientificCalculator/
│
├── Main.java
├── PlotWindow.java
├── ScientificCalc.g4
├── ScientificCalcLexer.java
├── ScientificCalcParser.java
├── ScientificCalcVisitor.java
├── ScientificEvalVisitor.java
├── ejemplos.txt
├── Calculadora Científica - ANTLR.pdf
└── README.md

## Ejecución

Primero se generan los archivos de ANTLR con:

```bash
antlr4 -no-listener -visitor ScientificCalc.g4
````

Después se compila el proyecto y se ejecuta la clase principal.

```bash
javac *.java
java Main
```

## Archivos principales

* `ScientificCalc.g4`: contiene la gramática.
* `Main.java`: programa principal.
* `PlotWindow.java`: permite mostrar las gráficas.
* `ejemplos.txt`: contiene ejemplos para probar la calculadora.

