/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreDecisao;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;

/**
 *
 * @author glauc
 */
public class Condicional extends Binario implements Cloneable {

    ExpressaoAritmetica exprEsq;
    ExpressaoAritmetica exprDir;

    OperadorRelacional op;
    Programa verdadeira, falsa;
    Dataframe d = null;

    public Condicional(Dataframe d, Programa verdadeira, Programa falsa, ExpressaoAritmetica exprEsq, OperadorRelacional op, ExpressaoAritmetica exprDir) {
        super(verdadeira, falsa);
        this.exprEsq = exprEsq;
        this.exprDir = exprDir;
        this.op = op;
        this.d = d;
    }

    public ExpressaoAritmetica getExprEsq() {
        return exprEsq;
    }

    public void setExprEsq(ExpressaoAritmetica exprEsq) {
        this.exprEsq = exprEsq;
    }

    public ExpressaoAritmetica getExprDir() {
        return exprDir;
    }

    public void setExprDir(ExpressaoAritmetica exprDir) {
        this.exprDir = exprDir;
    }

    public OperadorRelacional getOp() {
        return op;
    }

    public void setOp(OperadorRelacional op) {
        this.op = op;
    }

    @Override
    public double processa(int linha) {
        if (this.op == OperadorRelacional.MENOR_OU_IGUAL) {
            if (exprEsq.processa(d, linha) <= exprDir.processa(d, linha)) {
                return super.getEsquerdo().processa(linha);
            } else {
                return super.getDireito().processa(linha);
            }
        } else if (this.op == OperadorRelacional.MAIOR_OU_IGUAL) {
            if (exprEsq.processa(d, linha) >= exprDir.processa(d, linha)) {
                return super.getEsquerdo().processa(linha);
            } else {
                return super.getDireito().processa(linha);
            }
        }
        return 999999;

    }

    @Override
    public String toString() {
        String resultado
                = "if(  " + this.exprEsq.toString() + (op.toString().equals("MAIOR_OU_IGUAL") ? "  >=  " : "  <=  ") + " " + this.exprDir.toString() + "  ){\n\t"
                + super.getEsquerdo().toString().replaceAll("\n", "\n\t")
                + "\n}else{\n\t"
                + super.getDireito().toString().replaceAll("\n", "\n\t")
                + "\n}\n";
        return resultado;
    }

    @Override
    public Condicional clone() {
        return new Condicional(this.d, super.getEsquerdo(), super.getDireito(), this.exprEsq, this.op, this.exprDir);
    }

}
