package com.senac.projetointegrador.repositories;

import com.senac.projetointegrador.entities.Medico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MedicoRepository extends MongoRepository<Medico, String> {
    Optional<Medico> findByCrm(Integer crm);
}
