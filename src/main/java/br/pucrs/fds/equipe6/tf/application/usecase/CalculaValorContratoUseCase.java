package br.pucrs.fds.equipe6.tf.application.usecase;

import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import br.pucrs.fds.equipe6.tf.domain.repository.ContratoRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculaValorContratoUseCase {

    private final ContratoRepository contratoRepository;

    public CalculaValorContratoUseCase(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public double executar(int id) {
        Contrato c = contratoRepository.findById(id).orElse(null);
        if (c == null) return 0;

        double valorBase = c.getJogo().getCategoria().getValorMinimo();
        double valorMinuto = c.getJogo().getValorMinuto();
        double taxaParaReal = c.getJogo().getMoeda().getTaxaParaReal();

        double valorTotal = 0;
        for (Uso u : c.getUsos()) {
            long minutos = u.getDuracaoMinutos();
            double valorEmMoeda = valorBase + (valorMinuto * minutos);
            valorTotal += valorEmMoeda * taxaParaReal;
        }
        return valorTotal;
    }
}