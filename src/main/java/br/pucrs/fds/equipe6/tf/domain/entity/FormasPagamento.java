package br.pucrs.fds.equipe6.tf.domain.entity;

import java.util.ArrayList;
import java.util.List;


public class FormasPagamento {
    private List<FormaPagamento> formasPagamento;

    public FormasPagamento() {
        formasPagamento = new ArrayList<>();
    }

    public List<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void addFormaPagamento(FormaPagamento f) {
        formasPagamento.add(f);
    }

    public FormaPagamento buscaPorNum(int num) {
        return formasPagamento.stream()
                .filter(f -> f.getNum() == num)
                .findFirst()
                .orElse(null);
    }
}
