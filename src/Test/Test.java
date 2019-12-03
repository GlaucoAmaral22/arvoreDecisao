/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreCondicional.ExpressaoUnaria;
import arvoreCondicional.Multiplicacao;
import arvoreCondicional.Soma;
import arvoreDecisao.Inteligencia;
import arvoreDecisao.OperadorRelacional;
import arvoreDecisao.Programa;
import java.util.Random;

/**
 *
 * @author glauc
 */
public class Test {

    public static void main(String[] args) {
        Dataframe d = new Dataframe();
        d.ler("C:\\Users\\glauc\\Desktop\\DS\\train.csv", 284);

        Inteligencia e = new Inteligencia(d);
        
        Programa raiz = e.montaArvoreCondicional(4);

        System.out.println(raiz.toString());

    }

}
