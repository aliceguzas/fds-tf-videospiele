package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContratoJpaItfRepo extends CrudRepository<Contrato, Integer> {

    List<Contrato> findByJogo(Jogo jogo);
    List<Contrato> findByCliente_CPF(String cpf);
}
