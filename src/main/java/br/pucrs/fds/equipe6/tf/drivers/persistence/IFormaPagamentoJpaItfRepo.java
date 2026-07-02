package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.FormaPagamento;
import org.springframework.data.repository.CrudRepository;

public interface IFormaPagamentoJpaItfRepo extends CrudRepository<FormaPagamento, Integer> {
}
