/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execucao;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreCondicional.Floresta;
import arvoreDecisao.Inteligencia;
import java.util.LinkedList;

/**
 *
 * @author glauc
 */
public class Main2Expr {

    public static void main(String[] args) {

        Dataframe train = new Dataframe();
        train.ler("C:\\Users\\glauc\\Desktop\\DS\\train.csv", 284);

        Dataframe valid = new Dataframe();
        valid.ler("C:\\Users\\glauc\\Desktop\\DS\\valid.csv", 284);

        Inteligencia iTrain = new Inteligencia(train);
        Inteligencia iValid = new Inteligencia(valid);

        int qtdArvores = 50;
        int h = 5;

        Floresta f = new Floresta(qtdArvores);
        /*
        for (int i = 0; i < qtdArvores; i++) {

            ExpressaoAritmetica melhorExpressao = null;

            double rmseMenor = 99999999;

            //HILLCLIMB
            for (int j = 0; j < 15000; j++) {

                ExpressaoAritmetica raiz = iTrain.montaArvoreExpressao(h);//monto a expressao

                iTrain.executaArvoreExpressao(raiz, 0, 284);//executo essa arvore da expressao

                if (iTrain.calculaRMSEArvoreExpressao(0,284) < rmseMenor) {
                    melhorExpressao = raiz; //salvo a melhor expressao encontrada
                    rmseMenor = iTrain.calculaRMSEArvoreExpressao(0,284);
                }

            }
            f.addArvore(i, melhorExpressao);
            iTrain.executaArvoreExpressao(f.getArvore(i),0,284);
            System.out.println(iTrain.calculaRMSEArvoreExpressao(0,284));
        }*/

        int qtdFolds = 20;
        int qtdLinhasFold = 568 / qtdFolds;

        Dataframe dfTrainValid = new Dataframe();
        dfTrainValid.ler("C:\\Users\\glauc\\Desktop\\DS\\train-valid.csv", 568);

        Inteligencia iCV = new Inteligencia(dfTrainValid);

        //LinkedList<double[][]> treinos = iCV.getTreinos(qtdFolds, dfTrainValid.df);
        double[] rmseFolds = new double[qtdFolds];

        for (int fold = 0; fold < qtdFolds; fold++) {

            int inicioFold = fold * qtdLinhasFold;
            int fimFold = (fold + 1) * qtdLinhasFold;

            ExpressaoAritmetica melhorExpressao = null;

            double rmseMenor = 99999999;

            for (int i = 0; i < 1000; i++) {
                ExpressaoAritmetica raiz = iCV.montaArvoreExpressao(h);//monto a expressao

                iCV.executaArvoreExpressaof(raiz, inicioFold, fimFold);//executo essa arvore da expressao

                if (iCV.calculaRMSEArvoreExpressaof(inicioFold, fimFold) < rmseMenor) {
                    melhorExpressao = raiz; //salvo a melhor expressao encontrada
                    rmseMenor = iCV.calculaRMSEArvoreExpressaof(inicioFold, fimFold);
                    System.out.println("melhor rmse para o treino do fold" + fold + ": " + rmseMenor);
                }

            }

            iCV.executaArvoreExpressao(melhorExpressao, inicioFold, fold);
            rmseFolds[fold] = iCV.calculaRMSEArvoreExpressao(inicioFold, fimFold);
            System.out.println("Fold- " + fold + " :" + rmseFolds[fold]);

        }
        double sum = 0;
        for (int i = 0; i < rmseFolds.length; i++) {
            sum += rmseFolds[i];
        }
        double media = sum / rmseFolds.length;
        System.out.println("Media: " + sum / rmseFolds.length);

        sum = 0;
        for (int i = 0; i < rmseFolds.length; i++) {
            sum += Math.pow(rmseFolds[i] - media, 2);
        }

        System.out.println("Desvio Padrão: " + Math.sqrt(sum/rmseFolds.length));

    }

}
