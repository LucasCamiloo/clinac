package com.senac.projetointegrador.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "consultas")
public class Consulta {

    @Id
    private String id;
    private String tipoDeServico;
    private String profissional;
    private Date dataConsulta;
    private String preConsulta;
    private String status;
    private String idMedico;
    private String idPaciente;
    private String nomePaciente; 

    // Construtor vazio necessário para MongoDB
    public Consulta() {
    }

    public Consulta(String id, String tipoDeServico, String profissional, Date dataConsulta, String preConsulta,
            String status, String idMedico, String idPaciente, String nomePaciente) {
        this.id = id;
        this.tipoDeServico = tipoDeServico;
        this.profissional = profissional;
        this.dataConsulta = dataConsulta;
        this.preConsulta = preConsulta;
        this.status = status;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDeServico() {
        return tipoDeServico;
    }

    public void setTipoDeServico(String tipoDeServico) {
        this.tipoDeServico = tipoDeServico;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getPreConsulta() {
        return preConsulta;
    }

    public void setPreConsulta(String preConsulta) {
        this.preConsulta = preConsulta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }
}
