package br.pucrs.fds.equipe6.tf.usecase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.pucrs.fds.equipe6.tf.Categoria;
import br.pucrs.fds.equipe6.tf.drivers.repository.CategoriaRepository;

@Service
public class UploadCategoriasService {

    private final CategoriaRepository categoriaRepository;

    public UploadCategoriasService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public boolean executar(MultipartFile file) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {

                // pula header
                if (first) {
                    first = false;
                    continue;
                }

                String[] data = line.split(";");

                int num = Integer.parseInt(data[0]);
                String nome = data[1];
                double valorMinimo = Double.parseDouble(data[2]);

                // 🚨 regra do trabalho: evitar duplicados
                if (categoriaRepository.existsById(num)) {
                    continue;
                }

                Categoria categoria = new Categoria(num, nome, valorMinimo);

                categoriaRepository.save(categoria);
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}