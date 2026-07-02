package br.pucrs.fds.equipe6.tf.application.usecase;

import java.util.Date;


public class UsoDTO {
    private int idContrato;
    private int numero;
    private Date dataInicio;
    private Date dataFim;
    private int horarioInicio;
    private int horarioFim;

    public int getIdContrato() { return idContrato; }
    public void setIdContrato(int idContrato) { this.idContrato = idContrato; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

    public int getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(int horarioInicio) { this.horarioInicio = horarioInicio; }

    public int getHorarioFim() { return horarioFim; }
    public void setHorarioFim(int horarioFim) { this.horarioFim = horarioFim; }
}
