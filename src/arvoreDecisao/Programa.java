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
public interface Programa extends Cloneable{
    
    public abstract double processa(int linha);
    
    public abstract String toString();
    
    public abstract Programa clone();
    
}
