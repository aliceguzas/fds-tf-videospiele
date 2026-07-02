package br.pucrs.fds.equipe6.trab1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContratoRespostaDTO {

    private int id;
    private int periodo;
    private String cpf;
    private String nomeCliente;
    private int codigoJogo;
    private String nomeJogo;
    private String categoria;
    private Date data;
    private boolean cancelado;
    private List<UsoSimples> usos;

    public ContratoRespostaDTO(Contrato c) {
        this.id = c.getId();
        this.periodo = c.getPeriodo();
        this.data = c.getData();
        this.cpf = c.getCliente().getCPF();
        this.nomeCliente = c.getCliente().getNome();
        this.cancelado = c.isCancelado();
        this.codigoJogo = c.getJogo().getCod();
        this.nomeJogo = c.getJogo().getNome();
        this.categoria = c.getJogo().getCategoria().getNome();
        this.usos = c.getUsos().stream()
                .map(u -> new UsoSimples(
                        u.getNumero(),
                        u.getDataInicio(),
                        u.getDataFim(),
                        u.getHorarioInicio(),
                        u.getHorarioFim(),
                        u.getDuracaoMinutos()))
                .collect(Collectors.toList());
    }

    // classe interna para evitar referência circular com Contrato
    public static class UsoSimples {
        public int numero;
        public Date dataInicio;
        public Date dataFim;
        public int horarioInicio;
        public int horarioFim;
        public long duracaoMinutos;

        public UsoSimples(int numero, Date dataInicio, Date dataFim,
                          int horarioInicio, int horarioFim, long duracaoMinutos) {
            this.numero = numero;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.horarioInicio = horarioInicio;
            this.horarioFim = horarioFim;
            this.duracaoMinutos = duracaoMinutos;
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public int getCodigoJogo() { return codigoJogo; }
    public void setCodigoJogo(int codigoJogo) { this.codigoJogo = codigoJogo; }
    public String getNomeJogo() { return nomeJogo; }
    public void setNomeJogo(String nomeJogo) { this.nomeJogo = nomeJogo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public List<UsoSimples> getUsos() { return usos; }
    public void setUsos(List<UsoSimples> usos) { this.usos = usos; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public boolean isCancelado() { return cancelado; }
    public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }
}