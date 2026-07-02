package br.pucrs.fds.equipe6.trab1.repository;

import br.pucrs.fds.equipe6.trab1.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCPF(String cpf);
}