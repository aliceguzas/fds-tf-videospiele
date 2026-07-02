package br.pucrs.fds.equipe6.tf.usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.pucrs.fds.equipe6.tf.Cliente;
import br.pucrs.fds.equipe6.tf.Contrato;
import br.pucrs.fds.equipe6.tf.FormaPagamento;
import br.pucrs.fds.equipe6.tf.Jogo;
import br.pucrs.fds.equipe6.tf.drivers.repository.ClienteRepository;
import br.pucrs.fds.equipe6.tf.drivers.repository.ContratoRepository;
import br.pucrs.fds.equipe6.tf.drivers.repository.FormaPagamentoRepository;
import br.pucrs.fds.equipe6.tf.drivers.repository.JogoRepository;

@Service
public class UploadContratosService {

    private final ContratoRepository contratoRepository;
    private final ClienteRepository clienteRepository;
    private final JogoRepository jogoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;

    public UploadContratosService(ContratoRepository contratoRepository,
                                 ClienteRepository clienteRepository,
                                 JogoRepository jogoRepository,
                                 FormaPagamentoRepository formaPagamentoRepository) {
        this.contratoRepository = contratoRepository;
        this.clienteRepository = clienteRepository;
        this.jogoRepository = jogoRepository;
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public boolean executar(MultipartFile file) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean first = true;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while ((line = reader.readLine()) != null) {

                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(";");

                int id = Integer.parseInt(data[0]);
                java.util.Date dataContrato = sdf.parse(data[1]);
                int periodo = Integer.parseInt(data[2]);

                int codCliente = Integer.parseInt(data[3]);
                int codJogo = Integer.parseInt(data[4]);
                int numFormaPagamento = Integer.parseInt(data[5]);

                // 🚨 evita duplicados
                if (contratoRepository.existsById(id)) {
                    continue;
                }

                Cliente cliente = clienteRepository.findById((long) codCliente)
                        .orElse(null);

                Jogo jogo = jogoRepository.findById(codJogo)
                        .orElse(null);

                FormaPagamento formaPagamento = formaPagamentoRepository.findById(numFormaPagamento)
                        .orElse(null);

                if (cliente == null || jogo == null || formaPagamento == null) {
                    continue; // ou return false se quiser mais rígido
                }

                Contrato contrato = new Contrato(
                        id,
                        dataContrato,
                        periodo,
                        cliente,
                        jogo,
                        formaPagamento
                );

                contratoRepository.save(contrato);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}