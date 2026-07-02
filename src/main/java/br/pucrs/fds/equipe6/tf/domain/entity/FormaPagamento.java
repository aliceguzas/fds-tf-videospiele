package br.pucrs.fds.equipe6.tf.domain.entity;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class FormaPagamento {
    @Id
    private int num;
    private int diaVencimento;

    public FormaPagamento(){

    }

    public FormaPagamento(int num, int diaVencimento) {
        this.num = num;
        this.diaVencimento = diaVencimento;
    }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }

    public int getDiaVencimento() { return diaVencimento; }
    public void setDiaVencimento(int diaVencimento) { this.diaVencimento = diaVencimento; }
}
