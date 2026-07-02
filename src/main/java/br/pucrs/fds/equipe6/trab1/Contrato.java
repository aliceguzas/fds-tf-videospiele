package br.pucrs.fds.equipe6.trab1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Contrato {

    @Id
    private int id;

    @Temporal(TemporalType.DATE)
    private Date data;

    private int periodo;

    @ManyToOne
    @JoinColumn(name = "cliente_cod")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "jogo_cod")
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_num")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Uso> usos = new ArrayList<>();


    private boolean cancelado = false; // novo atributo ! para cancelar contratos (endpoint 10)

    public Contrato(){

    }

    //construtor pro metodo da classe contratos
    public Contrato(int id, Date data, int periodo, Cliente cliente, Jogo jogo) {
    this.id = id;
    this.data = data;
    this.periodo = periodo;
    this.cliente = cliente;
    this.jogo = jogo;
    this.usos = new ArrayList<>();
    this.cancelado = false;
    }

    // novo: construtor usado pelo endpoint 6 do TF, já recebendo forma de pagamento
    public Contrato(int id, Date data, int periodo, Cliente cliente, Jogo jogo, FormaPagamento formaPagamento) {
        this.id = id;
        this.data = data;
        this.periodo = periodo;
        this.cliente = cliente;
        this.jogo = jogo;
        this.formaPagamento = formaPagamento;
        this.usos = new ArrayList<>();
        this.cancelado = false;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public void addUso(Uso uso) {
    uso.setContrato(this); 
    usos.add(uso);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public Jogo getJogo(){
        return this.jogo;
    }

    public List<Uso> getUsos(){
        return this.usos;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    // novo — getter/setter para forma de pagamento
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // Consulta a data final do contrato data + periodo
    public Date getDataFim() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        cal.add(Calendar.DAY_OF_MONTH, periodo);

        return cal.getTime();
    }

    // metodo que cancela logicamente nao fisicamente
    public void cancelar() { this.cancelado = true; }
}
