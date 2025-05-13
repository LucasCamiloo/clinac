package com.senac.projetointegrador.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicos")
public class Medico extends Pessoa {
	private Integer crm;
	
	public Medico(String nome, Integer crm, String cpf, String email, String telefone, String sexo, String cidade, String estado,
			String senha) {
		super(nome, cpf, email, telefone, sexo, cidade, estado, senha);
		this.crm = crm;
	}
	
	public Integer getCrm() {
		return crm;
	}
	
	public void setCrm(Integer crm) {
		this.crm = crm;
	}
}
