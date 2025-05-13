package com.senac.projetointegrador.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "prescricoes")
public class Prescricao {

    @Id
    private String id;

    private String pacienteId;
    private String doseDeUso;
    private String remedio;
    private String remedioAlternativo;
    private Date dataInicio;
    private Date dataFim;
    private String observacao;

    public Prescricao() {
        // Construtor vazio necessário para MongoDB
    }

    public Prescricao(String id, String pacienteId, String doseDeUso, String remedio, String remedioAlternativo, Date dataInicio,
            Date dataFim, String observacao) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.doseDeUso = doseDeUso;
        this.remedio = remedio;
        this.remedioAlternativo = remedioAlternativo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.observacao = observacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getDoseDeUso() {
        return doseDeUso;
    }

    public void setDoseDeUso(String doseDeUso) {
        this.doseDeUso = doseDeUso;
    }

    public String getRemedio() {
        return remedio;
    }

    public void setRemedio(String remedio) {
        this.remedio = remedio;
    }

    public String getRemedioAlternativo() {
        return remedioAlternativo;
    }

    public void setRemedioAlternativo(String remedioAlternativo) {
        this.remedioAlternativo = remedioAlternativo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
