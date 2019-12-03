/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leitura;

import arvoreDecisao.Condicional;
import arvoreDecisao.Folha;
import arvoreDecisao.OperadorRelacional;
import arvoreDecisao.Programa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

/**
 *
 * @author glauc
 */
public class Dataframe {

    public double[][] df = null;
    
    public int[] target;

    public void ler(String endereco, int qtdlinha) {
        
        this.target = new int[qtdlinha];
        
        String arquivo = endereco;

        BufferedReader conteudoCSV = null;

        String linha = "";

        String csvSeparador = ",";

        int qtdColunas;

        try {
            conteudoCSV = new BufferedReader(new FileReader(arquivo));

            linha = conteudoCSV.readLine(); //leitura primeira linha com o nome de todas colunas

            qtdColunas = linha.split(csvSeparador).length - 2;// menos id e diagnosis

            this.df = new double[qtdlinha][qtdColunas];

            

            int i = 0;
            while ((linha = conteudoCSV.readLine()) != null) {
                //sempre o primeiro e o segundo valor sao "ignorados"

                String[] valoresLinha = linha.split(csvSeparador);//0 é id e 1 é o diagnosis

                target[i] = valoresLinha[1].equals("M") ? 1 : 0;

                for (int j = 0; j < qtdColunas; j++) {
                    this.df[i][j] = Double.parseDouble(valoresLinha[j + 2]);
                }

                i++;
            }

        } catch (Exception e) {
        }

    }

  

    public double getMenor(int caracteristica) {
        double menor = Double.POSITIVE_INFINITY;

        for (int i = 0; i < this.target.length; i++) {
            if (this.df[i][caracteristica] < menor) {
                menor = this.df[i][caracteristica];
            }
        }
        return menor;

    }

    public double getMaior(int caracteristica) {
        double maior = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < this.target.length; i++) {
            if (this.df[i][caracteristica] > maior) {
                maior = this.df[i][caracteristica];
            }
        }
        return maior;
    }

//    public void montaSaida() {
//        int[] saida = new int[284];
//
//    }

}
