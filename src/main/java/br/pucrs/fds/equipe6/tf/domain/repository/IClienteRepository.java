package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Cliente save(Cliente cliente);
    Cliente findById(Long id);
    Optional<Cliente> findByCPF(String cpf);
    List<Cliente> findAll();
    void deleteById(Long id);
}