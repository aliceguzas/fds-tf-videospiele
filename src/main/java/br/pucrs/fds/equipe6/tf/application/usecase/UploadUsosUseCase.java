package br.pucrs.fds.equipe6.tf.application.usecase;


import br.pucrs.fds.equipe6.tf.domain.entity.Contrato;
import br.pucrs.fds.equipe6.tf.domain.entity.Uso;
import br.pucrs.fds.equipe6.tf.domain.repository.UsoRepository;
import br.pucrs.fds.equipe6.tf.domain.repository.ContratoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@Service
public class UploadUsosUseCase {

    private final UsoRepository usoRepository;
    private final ContratoRepository contratoRepository;

    public UploadUsosUseCase(UsoRepository usoRepository,
                             ContratoRepository contratoRepository) {
        this.usoRepository = usoRepository;
        this.contratoRepository = contratoRepository;
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

                int numero = Integer.parseInt(data[0]);
                java.util.Date dataInicio = sdf.parse(data[1]);
                java.util.Date dataFim = sdf.parse(data[2]);
                int horarioInicio = Integer.parseInt(data[3]);
                int horarioFim = Integer.parseInt(data[4]);
                int contratoId = Integer.parseInt(data[5]);

                // evita duplicados
                if (usoRepository.existsById(numero)) {
                    continue;
                }

                Contrato contrato = contratoRepository.findById(contratoId)
                        .orElse(null);

                if (contrato == null) {
                    continue;
                }

                Uso uso = new Uso(
                        numero,
                        dataInicio,
                        dataFim,
                        horarioInicio,
                        horarioFim
                );

                uso.setContrato(contrato);
                contrato.addUso(uso);

                usoRepository.save(uso);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}