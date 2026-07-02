package br.pucrs.fds.equipe6.tf.usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.pucrs.fds.equipe6.tf.Moeda;
import br.pucrs.fds.equipe6.tf.drivers.repository.MoedaRepository;

@Service
public class UploadMoedasService {
    private final MoedaRepository moedaRepository;

    public UploadMoedasService(MoedaRepository moedaRepository) {
        this.moedaRepository = moedaRepository;
    }

    public boolean executar(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) { first = false; continue; }

                String[] data = line.split(";");
                int cod       = Integer.parseInt(data[0].trim());
                String nome   = data[1].trim();
                String simbolo = data[2].trim();

                // CSVnao tem taxaParaReal
                // admin deve atualizar depois ou usar taxa padrao 1.0
                double taxaParaReal = data.length > 3
                        ? Double.parseDouble(data[3].trim())
                        : 1.0;

                if (moedaRepository.existsById(cod)) continue;

                moedaRepository.save(new Moeda(cod, nome, simbolo, taxaParaReal));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
