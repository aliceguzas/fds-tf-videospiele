package br.pucrs.fds.equipe6.tf.usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.pucrs.fds.equipe6.tf.FormaPagamento;
import br.pucrs.fds.equipe6.tf.PIX;
import br.pucrs.fds.equipe6.tf.drivers.repository.FormaPagamentoRepository;

@Service
public class UploadFormasPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public UploadFormasPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
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

                // 📌 TIPO 1 = PIX (no seu sistema atual)
                if (tipo == 1) {

                    String chavePix = data[3];

                    forma = new PIX(num, diaVencimento, chavePix);
                }

                // 📌 extensão futura (cartão, boleto etc)
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