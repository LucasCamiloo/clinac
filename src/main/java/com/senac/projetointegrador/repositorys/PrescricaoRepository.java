package com.senac.projetointegrador.repositorys;

import com.senac.projetointegrador.entities.Prescricao;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PrescricaoRepository extends MongoRepository<Prescricao, String> {
    List<Prescricao> findByPacienteId(String pacienteId);
}
