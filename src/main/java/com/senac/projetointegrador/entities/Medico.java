package com.senac.projetointegrador.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicos")
public class Medico extends Pessoa {
	private Integer crm;
	private String especialidade;
	
	public Medico(String nome, Integer crm, String cpf, String email, String telefone, String sexo, String cidade, String estado,
			String senha, String especialidade) {
		super(nome, cpf, email, telefone, sexo, cidade, estado, senha);
		this.crm = crm;
		this.especialidade = especialidade;
		setTipo("medico");
	}
	
	public Integer getCrm() {
		return crm;
	}
	
	public void setCrm(Integer crm) {
		this.crm = crm;
	}
	
	public String getEspecialidade() {
		return especialidade;
	}
	
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
}
