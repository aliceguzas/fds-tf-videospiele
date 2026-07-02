package br.pucrs.fds.equipe6.tf.domain.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Moeda {

    @Id
    private int cod;
    private String nome;
    private String simbolo;
    private double taxaParaReal;

    public Moeda(){

    }

    public Moeda(int cod, String nome, String simbolo, double taxaParaReal) {
        this.cod = cod;
        this.nome = nome;
        this.simbolo = simbolo;
        this.taxaParaReal = taxaParaReal;
    }

    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSimbolo() { return simbolo; }
    public void setSimbolo(String simbolo) { this.simbolo = simbolo; }

    public double getTaxaParaReal() { return taxaParaReal; }
    public void setTaxaParaReal(double taxaParaReal) { this.taxaParaReal = taxaParaReal; }
}
