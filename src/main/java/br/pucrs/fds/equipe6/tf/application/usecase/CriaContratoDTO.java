package br.pucrs.fds.equipe6.tf.application.usecase;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CriaContratoDTO {
    private int id;
    private int periodo;
    private String cpf;
    private int codigoJogo;
    private int num;

    @NotNull(message = "Falta a data")
    private Date data;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getCodigoJogo() { return codigoJogo; }
    public void setCodigoJogo(int codigoJogo) { this.codigoJogo = codigoJogo; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
}
