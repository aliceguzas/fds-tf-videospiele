package br.pucrs.fds.equipe6.tf.application.usecase;


import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import br.pucrs.fds.equipe6.tf.domain.repository.ContratoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaCobrancaClienteUseCase {

    private final ContratoRepository contratoRepository;

    public ConsultaCobrancaClienteUseCase(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public double executar(String cpf) {
        double valorTotal = 0;
        List<Contrato> contratosCliente = contratoRepository.findByCliente_CPF(cpf);

        for (Contrato c : contratosCliente) {
            double valorBase = c.getJogo().getCategoria().getValorMinimo();
            double valorMinuto = c.getJogo().getValorMinuto();
            double taxaParaReal = c.getJogo().getMoeda().getTaxaParaReal();

            for (Uso u : c.getUsos()) {
                long minutos = u.getDuracaoMinutos();
                double valorEmMoeda = valorBase + (minutos * valorMinuto);
                double valorEmReal = valorEmMoeda * taxaParaReal;

                valorTotal += valorEmReal > 500 ? valorEmReal * 0.97 : valorEmReal;
            }
        }
        return valorTotal;
    }
}