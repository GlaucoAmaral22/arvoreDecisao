/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreDecisao;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreCondicional.ExpressaoUnaria;
import arvoreCondicional.Multiplicacao;
import arvoreCondicional.Soma;
import arvoreCondicional.Subtracao;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author glauc
 */
public class Inteligencia {

    Dataframe d = null;
    int[] target = null;
    int[][] mC = null;

    double[] saida;
    private double v;

    private Random r;

    public Inteligencia(Dataframe d) {
        this.d = d;
        this.target = d.target;
        saida = new double[target.length];
        this.r = new Random();
        System.out.println("saida lenght:" + saida.length);
    }

    public Programa montaArvoreCondicional(int nivel) {
        if (nivel == 1) {
            return new Folha(r.nextDouble());
        } else {
            int feature = r.nextInt(30);

            double num2 = r.nextDouble() * (d.getMaior(feature) - d.getMenor(feature)) + d.getMenor(feature);

            if (r.nextDouble() > 0.5) {
                return new Condicional(d, montaArvoreCondicional(nivel - 1), montaArvoreCondicional(nivel - 1), montaArvoreExpressao(nivel), OperadorRelacional.MAIOR_OU_IGUAL, montaArvoreExpressao(nivel));
            } else {
                return new Condicional(d, montaArvoreCondicional(nivel - 1), montaArvoreCondicional(nivel - 1), montaArvoreExpressao(nivel), OperadorRelacional.MENOR_OU_IGUAL, montaArvoreExpressao(nivel));
            }
        }
    }

    public void executaArvoreCondicioal(Programa raiz) { //modificar para int nLinhas
        //RAIZ É UMA CONDICIONAL
        for (int j = 0; j < saida.length; j++) {
            saida[j] = raiz.processa(j);
        }
    }

    public void vai() {
        for (int i = 0; i < saida.length; i++) {
            System.out.print(saida[i] + "        ");
        }
        System.out.println("");
    }

    public void executaArvoreExpressao(ExpressaoAritmetica raiz, int inicio, int fim) {
        //RAIZ É UMA Expressao Aritmetica
        for (int j = inicio; j < fim; j++) {
            saida[j] = raiz.processa(d, j);
        }

    }

    public double rmseArvoreCondicional() {
        double rmse = 0;

        for (int i = 0; i < target.length; i++) {
            rmse += Math.pow((target[i] - saida[i]), 2);
        }
        rmse = rmse / target.length;

        return Math.sqrt(rmse);
    }

    public double calculaRMSEArvoreExpressao(int inicio, int fim) {
        double RMSE = 0;
        for (int i = inicio; i < fim; i++) {
            RMSE += Math.pow((this.target[i] - (1 / (1 + Math.exp(-this.saida[i])))), 2);
        }
        RMSE = RMSE / this.target.length;
        return Math.sqrt(RMSE);
    }

    public double mae() {
        double mae = 0;
        for (int i = 0; i < target.length; i++) {
            mae += (target[i] - saida[i]);
        }
        return mae / target.length;
    }

    public String matrizConfusao() {
        this.mC = new int[2][2];

        int TP = 0, TN = 0, FN = 0, FP = 0;

        for (int i = 0; i < target.length; i++) {

            if (saida[i] == 1 && target[i] == 1) { //Verdadeiro Positivo. 
                TP++;
            } else if (saida[i] == 0 && target[i] == 0) { //Verdadeiro Negativo
                TN++;
            } else if (saida[i] == 0 && target[i] == 1) { //A predição indica que a pessoa nao tem o problema, mas na verdade ele possui sim. Entao é um FALSO NEGATIVO
                FN++;
            } else if (saida[i] == 1 && target[i] == 0) {
                FP++;
            }
        }
        return "---------\n"
                + "|" + TP + "(VP)|" + FP + " (FP)|\n"
                + "|" + FN + "(FN)|VN" + TN + "(VN)|\n";
    }

    //criar método para criação de folha aleatoria
    public Programa novaFolha() {
        return new Folha(r.nextDouble());
    }

    //criar método para criar arvore aleatoria de nivel entre 2 e 5
    public Programa novaMiniArvore(int min, int maxNivel) {

        int nivelDaArvore = numeroAleatorio(min, maxNivel);

        return this.montaArvoreCondicional(nivelDaArvore);

    }

    public int numeroAleatorio(int min, int max) {

        int randomNum = r.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /*
    public void alteraCondicional(Condicional cond) {

        int num = numeroAleatorio(1, 3);

        switch (num) {
            case 1: //alterar o operador
                if (cond.op == OperadorRelacional.MAIOR_OU_IGUAL) {
                    cond.setOp(OperadorRelacional.MENOR_OU_IGUAL);
                } else {
                    cond.setOp(OperadorRelacional.MAIOR_OU_IGUAL);
                }
                break;
            case 2://alterar para new random
                int feature = cond.getFeature();

                double num2 = Math.random() * (d.getMaior(feature) - d.getMenor(feature)) + d.getMenor(feature);

                cond.setNum2(num2);

                break;
            case 3:  //adicionar uma quantia a mais no lado direito

                cond.setNum2(cond.getNum2() + Math.random());

                break;
        }
    }*/
    ////FAZER GRAFICO(), OTIMIZAÇÃO(...) e IMPRESSAO DO PROGRAMA(OK)
    /*
    public Programa otimiza2(Condicional raiz, int alturaArvore, double rmse) {

        Condicional arvoreClonada = raiz.clone();

        int alturaModificar = numeroAleatorio(1, alturaArvore - 1);

        Condicional pai = arvoreClonada;

        //primeiro saber em qual nivel iremos modificar
        //estarei apontando para o Pai da altura a ser modifiado. Logo o pai SEMPRE será uma condicional
        for (int j = 0; j < alturaModificar - 1; j++) {

            if (Math.random() > Math.random()) {
                pai = (Condicional) pai.getDireito();
            } else {
                pai = (Condicional) pai.getEsquerdo();
            }

        }

        //aqui o pai ja estará na altura-1 a ser modificada.
        //agora precisamos saber se o filho da direita ou da esquerda será modificado
        if (Math.random() > Math.random()) { //esquerda

            if (pai.getEsquerdo() instanceof Folha) {
                switch (numeroAleatorio(1, 2)) {
                    case 1: //mudo a folha
                        pai.setEsquerdo(this.novaFolha());
                        break;
                    case 2: //coloco uma subarvore
                        pai.setEsquerdo(this.novaMiniArvore((alturaArvore - alturaModificar), alturaArvore + 2));
                        break;
                }
            } else {//condicional
                //podemos colocar uma nova subarvore, folha ou modificar parametros
                switch (numeroAleatorio(1, 2)) {
                    case 1: //coloco uma subarvore
                        pai.setEsquerdo(this.novaMiniArvore((alturaArvore - alturaModificar), alturaArvore + 2));
                        break;
                    case 2:
                        this.alteraCondicional((Condicional) pai.getEsquerdo());/////
                        break;
                }
            }

        } else { //direita
            if (pai.getDireito() instanceof Folha) {
                switch (numeroAleatorio(1, 2)) {
                    case 1: //mudo a folha
                        pai.setDireito(this.novaFolha());
                        break;
                    case 2: //coloco uma subarvore
                        pai.setDireito(this.novaMiniArvore((alturaArvore - alturaModificar), alturaArvore + 2));
                        break;
                }
            } else {//condicional
                //podemos colocar uma nova subarvore ou modificar parametros
                switch (numeroAleatorio(1, 2)) {
                    case 1: //coloco uma subarvore
                        pai.setDireito(this.novaMiniArvore((alturaArvore - alturaModificar), alturaArvore + 2));
                        break;
                    case 2:
                        this.alteraCondicional((Condicional) pai.getDireito());
                        break;
                }
            }
        }
        return arvoreClonada;
    }*/
    public ExpressaoAritmetica montaArvoreExpressao(int nivel) {

        if (nivel == 1) {
            if (r.nextDouble() > 0.5) {
                return new ExpressaoUnaria(r.nextDouble());//gero uma constante

            } else {
                return new ExpressaoUnaria(numeroAleatorio(1, 29));//gero uma feature
            }
        } else {
            int numeroAleatorio = this.numeroAleatorio(1, 3);

            switch (numeroAleatorio) {
                case 1:
                    return new Soma(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
                case 2:
                    return new Subtracao(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
                case 3:
                    return new Multiplicacao(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
            }
        }
        return null;

    }

    public LinkedList<double[][]> getTreinos(int kfolds, double[][] dfTrainValid) {

        //montar os grupos de treinos - linkedlist..
        LinkedList<double[][]> gruposTreinos = new LinkedList();

        int qtdLinhasCadaFold = 568 / kfolds; //113

        int qtdTotalLinhasSemOFold = (kfolds - 1) * qtdLinhasCadaFold;//452

        int qtdFeat = dfTrainValid[0].length;

        for (int k = 0; k < kfolds; k++) {//para cada fold eu crio um grupo de treino 

            int inicioExcluidos = k * qtdLinhasCadaFold;
            int fimExcluidos = ((k + 1) * qtdLinhasCadaFold) - 1;

            double[][] m = new double[qtdTotalLinhasSemOFold][qtdFeat];

            int l = 0;//para controle das linhas do novo grupo

            for (int i = 0; i < kfolds * qtdLinhasCadaFold; i++) { //percorrer por todas as linhas do TRAIN junto com o VALID

                //pegando somente os valores necessarios se o fold k
                if (i < inicioExcluidos || i > fimExcluidos) {
                    for (int j = 0; j < qtdFeat; j++) {
                        m[l][j] = dfTrainValid[i][j];
                    }
                    l++;
                }
            }
            gruposTreinos.add(m);
        }

        return gruposTreinos;
    }

    public void executaArvoreExpressaof(ExpressaoAritmetica raiz, int incioF, int fimF) {
        //RAIZ É UMA Expressao Aritmetica
        for (int j = 0; j < saida.length; j++) {
            if (j < incioF || j > fimF) {
                saida[j] = raiz.processa(d, j);
            }
        }
    }

    public double calculaRMSEArvoreExpressaof(int inicioF, int fimF) {
        double RMSE = 0;
        for (int i = 0; i < target.length; i++) {
            if (i < inicioF || i > fimF) {
                RMSE += Math.pow((this.target[i] - (1 / (1 + Math.exp(-this.saida[i])))), 2);
            }
        }
        RMSE = RMSE / this.target.length;
        return Math.sqrt(RMSE);
    }

}
