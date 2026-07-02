package br.pucrs.fds.equipe6.tf.drivers.persistence;

import br.pucrs.fds.equipe6.tf.domain.entity.FormaPagamento;
import br.pucrs.fds.equipe6.tf.domain.repository.IFormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class FormaPagamentoJpaImplRepository implements IFormaPagamentoRepository {
    private IFormaPagamentoJpaItfRepo repository;

    @Autowired
    public FormaPagamentoJpaImplRepository(IFormaPagamentoJpaItfRepo repository) {
        this.repository = repository;
    }

    @Override
    public FormaPagamento save(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @Override
    public FormaPagamento findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<FormaPagamento> findAll() {
        List<FormaPagamento> formasPagamento = new ArrayList<>();
        repository.findAll().forEach(formasPagamento::add);
        return formasPagamento;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
