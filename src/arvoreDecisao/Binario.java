/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreDecisao;

/**
 *
 * @author glauc
 */
public abstract class Binario implements Programa{
    
    Programa esquerdo = null;
    Programa direito = null;
    
    public Binario(Programa esq, Programa dir){
        
        this.esquerdo = esq;
        this.direito = dir;
        
    }

    public void setEsquerdo(Programa esquerdo) {
        this.esquerdo = esquerdo;
    }

    public void setDireito(Programa direito) {
        this.direito = direito;
    }

    public Programa getEsquerdo() {
        return esquerdo;
    }

    public Programa getDireito() {
        return direito;
    }
    
    public abstract Binario clone();
}
