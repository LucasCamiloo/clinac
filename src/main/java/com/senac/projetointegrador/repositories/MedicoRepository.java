package com.senac.projetointegrador.repositories;

import com.senac.projetointegrador.entities.Medico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface MedicoRepository extends MongoRepository<Medico, String> {
    Optional<Medico> findByCrm(Integer crm);
    List<Medico> findAll();
    Optional<Medico> findByNome(String nome);
}
