package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Jogo;
import br.pucrs.fds.equipe6.tf.domain.repository.IContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class ContratoJpaImplRepository implements IContratoRepository {
    private IContratoJpaItfRepo repository;

    @Autowired
    public ContratoJpaImplRepository(IContratoJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public Contrato save(Contrato contrato) {
        return repository.save(contrato);
    }

    @Override
    public Contrato findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Contrato> findAll() {
        List<Contrato> contratos = new ArrayList<>();
        repository.findAll().forEach(contratos::add);
        return contratos;
    }

    @Override
    public List<Contrato> findByJogo(Jogo jogo) {
        return repository.findByJogo(jogo);
    }

    @Override
    public List<Contrato> findByClienteCPF(String cpf) {
        return repository.findByCliente_CPF(cpf);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}
