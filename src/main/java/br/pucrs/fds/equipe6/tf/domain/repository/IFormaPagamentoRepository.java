package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.FormaPagamento;
import java.util.List;

public interface IFormaPagamentoRepository {
    FormaPagamento save(FormaPagamento formaPagamento);
    FormaPagamento findById(Integer id);
    List<FormaPagamento> findAll();
    void deleteById(Integer id);
}