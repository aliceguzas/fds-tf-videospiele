package br.pucrs.fds.equipe6.tf.domain.entity;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import br.pucrs.fds.equipe6.tf.domain.Contrato;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Uso {

    @Id
    private int numero;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    private int horarioInicio;
    private int horarioFim;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    @JsonIgnore
    private Contrato contrato;

    public Uso(){

    }
    public Uso(int numero, Date dataInicio, Date dataFim, int horarioInicio, int horarioFim) {
        this.numero = numero;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getHorarioInicio() {
        return this.horarioInicio;
    }

    public void setHorarioInicio(int horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public int getHorarioFim() {
        return this.horarioFim;
    }

    public void setHorarioFim(int horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    public long getDuracaoMinutos() {
        // converte java.sql.Date para java.util.Date antes de usar toInstant
        java.util.Date dIni = new java.util.Date(dataInicio.getTime());
        java.util.Date dFim = new java.util.Date(dataFim.getTime());

        LocalDate dataIni = dIni.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime horaIni = LocalTime.of(horarioInicio % 24, 0);

        LocalDate dataF = dFim.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime horaFim = LocalTime.of(horarioFim % 24, 0);

        LocalDateTime dataHoraIni = LocalDateTime.of(dataIni, horaIni);
        LocalDateTime dataHoraFim = LocalDateTime.of(dataF, horaFim);

        return Duration.between(dataHoraIni, dataHoraFim).toMinutes();
    }

}