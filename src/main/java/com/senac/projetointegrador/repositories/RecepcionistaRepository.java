package com.senac.projetointegrador.repositories;

import com.senac.projetointegrador.entities.Recepcionista;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface RecepcionistaRepository extends MongoRepository<Recepcionista, String> {
    Optional<Recepcionista> findByEmail(String email);
}
