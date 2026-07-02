package br.pucrs.fds.equipe6.tf.domain.repository;

import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import java.util.List;

public interface IContratoRepository {
    Contrato save(Contrato contrato);
    Contrato findById(Integer id);
    List<Contrato> findAll();
    List<Contrato> findByJogo(Jogo jogo);
    List<Contrato> findByClienteCPF(String cpf);
    void deleteById(Integer id);
}