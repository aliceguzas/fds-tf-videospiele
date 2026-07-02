package br.pucrs.fds.equipe6.tf.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.pucrs.fds.equipe6.tf.domain.entity.Moeda;
import br.pucrs.fds.equipe6.tf.domain.entity.Situacao;
import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import jakarta.persistence.*;

@Entity
public class Jogo {

    @Id
    private int cod;
    private String nome;
    private int ano;
    private double valorMinuto;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    private boolean situacaoManual = false;

    @ManyToOne
    @JoinColumn(name = "categoria_num")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "moeda_cod")
    private Moeda moeda;

    public Jogo() {}

    public Jogo(int cod, String nome, int ano, double valorMinuto, Categoria categoria, Moeda moeda) {
        this.cod = cod;
        this.nome = nome;
        this.ano = ano;
        this.valorMinuto = valorMinuto;
        this.situacao = Situacao.DISPONIVEL;
        this.categoria = categoria;
        this.moeda = moeda;
    }

    public void setSituacao(Situacao situacao) { this.situacao = situacao; }
    public Situacao getSituacao() { return this.situacao; }
    public Moeda getMoeda() { return moeda; }
    public void setMoeda(Moeda moeda) { this.moeda = moeda; }
    public boolean isSituacaoManual() { return situacaoManual; }
    public void setSituacaoManual(boolean situacaoManual) { this.situacaoManual = situacaoManual; }
    public int getCod() { return this.cod; }
    public void setCod(int cod) { this.cod = cod; }
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getAno() { return this.ano; }
    public void setAno(int ano) { this.ano = ano; }
    public double getValorMinuto() { return this.valorMinuto; }
    public void setValorMinuto(double valorMinuto) { this.valorMinuto = valorMinuto; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    //  recebe List<Contrato> diretamente
    // usado pelo AtualizarSituacaoJogosService (JPA)
    public boolean estaContratado(List<Contrato> contratosDoJogo) {
        Date hoje = new Date();
        return contratosDoJogo.stream()
                .anyMatch(c -> !c.isCancelado() && c.getDataFim().after(hoje));
    }

    //  recebe List<Contrato> diretamente
    public boolean estaObsoleto(List<Contrato> contratosDoJogo) {
        Calendar limite2anos = Calendar.getInstance();
        limite2anos.add(Calendar.YEAR, -2);
        Date dataLimite = limite2anos.getTime();

        // Caso 1: nunca teve contrato e foi cadastrado há mais de 3 anos
        if (contratosDoJogo.isEmpty()) {
            Calendar limite3anos = Calendar.getInstance();
            limite3anos.add(Calendar.YEAR, -3);
            Calendar cadastro = Calendar.getInstance();
            cadastro.set(this.getAno(), Calendar.JANUARY, 1, 0, 0, 0);
            cadastro.set(Calendar.MILLISECOND, 0);
            return cadastro.getTime().before(limite3anos.getTime());
        }

        // Caso 2: último uso expirou há mais de 2 anos
        Date ultimaDataFim = contratosDoJogo.stream()
                .flatMap(c -> c.getUsos().stream())
                .map(Uso::getDataFim)
                .filter(Objects::nonNull)
                .max(Date::compareTo)
                .orElse(null);

        return ultimaDataFim != null && ultimaDataFim.before(dataLimite);
    }

    //  recebe List<Contrato> diretamente
    public boolean estaRemovido(List<Contrato> contratosDoJogo) {
        if (!estaObsoleto(contratosDoJogo)) return false;

        if (contratosDoJogo.isEmpty()) {
            Calendar limite4anos = Calendar.getInstance();
            limite4anos.add(Calendar.YEAR, -4);
            Calendar cadastro = Calendar.getInstance();
            cadastro.set(this.getAno(), Calendar.JANUARY, 1, 0, 0, 0);
            cadastro.set(Calendar.MILLISECOND, 0);
            return cadastro.getTime().before(limite4anos.getTime());
        }

        Calendar limite3anos = Calendar.getInstance();
        limite3anos.add(Calendar.YEAR, -3);
        Date dataLimite = limite3anos.getTime();

        Date ultimaDataFim = contratosDoJogo.stream()
                .flatMap(c -> c.getUsos().stream())
                .map(Uso::getDataFim)
                .filter(Objects::nonNull)
                .max(Date::compareTo)
                .orElse(null);

        return ultimaDataFim != null && ultimaDataFim.before(dataLimite);
    }
}
