package com.senac.projetointegrador.repositorys;

import com.senac.projetointegrador.entities.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
    Optional<Paciente> findByEmail(String email);
}
