package br.pucrs.fds.equipe6.tf.domain;

import java.util.ArrayList;
import java.util.List;

import br.pucrs.fds.equipe6.tf.Jogo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {

    @Id
    private  int num;
    private String nome;
    private double valorMinimo;

    @OneToMany(mappedBy = "categoria")
    private List<Jogo> jogos = new ArrayList<>();

    public Categoria(){

    }

    public Categoria(int num, String nome, double valorMinimo) {
        this.num = num;
        this.nome = nome;
        this.valorMinimo = valorMinimo;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorMinimo() {
        return this.valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }


}
