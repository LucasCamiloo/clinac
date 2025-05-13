package com.senac.projetointegrador.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recepcionistas")
public class Recepcionista extends Pessoa {

	public Recepcionista(String nome, String cpf, String email, String telefone, String sexo, String cidade,
						 String estado, String senha) {
		super(nome, cpf, email, telefone, sexo, cidade, estado, senha);
	}
	
}
