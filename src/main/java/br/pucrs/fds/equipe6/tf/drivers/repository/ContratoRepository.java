package br.pucrs.fds.equipe6.trab1.repository;

import br.pucrs.fds.equipe6.trab1.Contrato;
import br.pucrs.fds.equipe6.trab1.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    List<Contrato> findByJogo(Jogo jogo);
    List<Contrato> findByCliente_CPF(String cpf);
}