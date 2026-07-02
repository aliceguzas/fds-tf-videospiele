package br.pucrs.fds.equipe6.tf.domain.entity;
import java.util.Date;

import br.pucrs.fds.equipe6.tf.FormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class CartaoCredito extends FormaPagamento {

    private String numero;

    @Temporal(TemporalType.DATE)
    private Date validade;

    public CartaoCredito(){
        super(0,0);
    }

    public CartaoCredito(int cod, int diaVencimento, String numero, Date validade) {
        super(cod, diaVencimento);
        this.numero = numero;
        this.validade = validade;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getValidade() {
        return this.validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }


}
