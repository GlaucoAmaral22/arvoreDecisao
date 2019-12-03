/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package execucao;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreDecisao.Condicional;
import arvoreDecisao.Folha;
import arvoreDecisao.Inteligencia;
import arvoreDecisao.OperadorRelacional;
import arvoreDecisao.Programa;

/**
 *
 * @author glauc
 */
public class Main {

    public static void main(String[] args) {

        Dataframe train = new Dataframe();
        train.ler("C:\\Users\\glauc\\Desktop\\DS\\train.csv", 284);

        Dataframe valid = new Dataframe();
        valid.ler("C:\\Users\\glauc\\Desktop\\DS\\valid.csv", 284);

        Inteligencia iTrain = new Inteligencia(train);
        Inteligencia iValid = new Inteligencia(valid);
        

     
        System.out.println("########## MEDIDAS DE ERRO ##########");
        System.out.println("RMSE: " + iTrain.rmseArvoreCondicional());
        System.out.println("MAE:  " + iTrain.mae());
        System.out.println(iTrain.matrizConfusao());

        int qtdITeracoes = 100000;
        int h = 4;

        double rmsemenor = 9999999;

        Programa melhorArvore = null;

        for (int j = 0; j < qtdITeracoes; j++) {

            Programa raiz = iTrain.montaArvoreCondicional(h);//monto uma arvore
            
            iTrain.executaArvoreCondicioal(raiz);//executo essa arvore
            
            if (iTrain.rmseArvoreCondicional() < rmsemenor) { //se ela tiver o rmse melhor que a antiga, salva ela

                rmsemenor = iTrain.rmseArvoreCondicional();
                System.out.println("rmse menor: " + rmsemenor);
                melhorArvore = raiz;

                iValid.executaArvoreCondicioal(melhorArvore);
                
                System.out.println(j + "   ,   " + iTrain.rmseArvoreCondicional()  + "   ,   " + iValid.rmseArvoreCondicional());
            }
        }

        iTrain.executaArvoreCondicioal(melhorArvore);
        System.out.println("RMSE da MELHOR ARVORE: " + iTrain.rmseArvoreCondicional());
        System.out.println(iTrain.matrizConfusao());
        System.out.println(melhorArvore.toString());

        ///chamar a quantidade de otimização que quero
        /*
        for (int j = 0; j < 10; j++) {

            Inteligencia n = new Inteligencia(train);

            Programa arvoreOtimizada = iTrain.otimiza2((Condicional) melhorArvore, h, iTrain.rmse());

            n.executaArvoreCondicioal(arvoreOtimizada);
            
            if (n.rmse() < iTrain.rmse()) {
                System.out.println("novo RMSE: " + n.rmse());
                melhorArvore = arvoreOtimizada;
                iTrain.executaArvoreCondicioal(melhorArvore);
            }
        }*/
        
      

    }
    
    
    
    
   
    
    

}
