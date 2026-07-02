package br.pucrs.fds.equipe6.tf.domain.entity;
import jakarta.persistence.Entity;


@Entity
public class PIX extends FormaPagamento {

    private String chave;

    public PIX(){
        super(0,0);
    }

    public PIX(int cod, int diaVencimento, String chave) {
        super(cod, diaVencimento);
        this.chave = chave;
    }

    public String getChave() {
        return this.chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

}
