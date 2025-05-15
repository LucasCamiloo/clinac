package com.senac.projetointegrador.repositorys;

import com.senac.projetointegrador.entities.Consulta;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ConsultaRepository extends MongoRepository<Consulta, String> {
    List<Consulta> findByIdPaciente(String idPaciente);
}
