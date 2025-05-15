package com.senac.projetointegrador.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "pacientes")
public class Paciente extends Pessoa {

    private Date dataDeNascimento;
    private List<String> prescricoes = new ArrayList<>();

    public Paciente(String nome, String cpf, String email, String telefone, String sexo, String cidade, String estado,
            String senha, Date dataDeNascimento) {
        super(nome, cpf, email, telefone, sexo, cidade, estado, senha);
        this.dataDeNascimento = dataDeNascimento;
        setTipo("paciente");
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public List<String> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<String> prescricoes) {
        this.prescricoes = prescricoes;
    }
}
