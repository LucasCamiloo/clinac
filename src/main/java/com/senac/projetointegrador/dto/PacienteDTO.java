package com.senac.projetointegrador.dto;

import java.util.Date;

public class PacienteDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String sexo;
    private String cidade;
    private String estado;
    private String senha;
    private Date dataDeNascimento;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getSenha() {
        return senha;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
}
