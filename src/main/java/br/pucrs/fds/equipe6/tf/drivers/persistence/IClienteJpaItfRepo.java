package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IClienteJpaItfRepo extends CrudRepository<Cliente, Long> {

    Optional<Cliente> findByCPF(String cpf);
}
