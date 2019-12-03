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
public class Folha implements Programa, Cloneable {

    public double v;

    public Folha(double v1) {
        this.v = v1;
    }

    @Override
    public double processa(int limiar) {
        //return this.v;
        if (this.v > 0.6) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return Double.toString(this.v);
    }

    @Override
    public Folha clone() {
        return new Folha(this.v);
    }

}
