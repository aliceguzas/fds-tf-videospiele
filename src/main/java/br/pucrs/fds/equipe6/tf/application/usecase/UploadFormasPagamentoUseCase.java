package br.pucrs.fds.equipe6.tf.application.usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import br.pucrs.fds.equipe6.tf.domain.entity.PIX;
import br.pucrs.fds.equipe6.tf.domain.entity.FormaPagamento;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.pucrs.fds.equipe6.tf.domain.repository.FormaPagamentoRepository;

@Service
public class UploadFormasPagamentoUseCase {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public UploadFormasPagamentoUseCase(FormaPagamentoRepository formaPagamentoRepository) {
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

                int num = Integer.parseInt(data[0]);
                int diaVencimento = Integer.parseInt(data[1]);
                int tipo = Integer.parseInt(data[2]);

                if (formaPagamentoRepository.existsById(num)) {
                    continue;
                }

                FormaPagamento forma = null;

                // PIX
                if (tipo == 1) {

                    String chavePix = data[3];

                    forma = new PIX(num, diaVencimento, chavePix);
                }

                // cartão
                else {
                    continue; // ignora tipos desconhecidos
                }

                formaPagamentoRepository.save(forma);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}