package com.suadenuncia.pojo;

import java.io.Serializable;


import java.sql.Timestamp;
import java.util.List;

public class Civil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp criadoEm;

	private String email;

	private String nome;

	private String senha;

	private List<Denuncia> denuncias;

	public Civil() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCriadoEm() {
		return this.criadoEm;
	}

	public void setCriadoEm(Timestamp criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Denuncia> getDenuncias() {
		return this.denuncias;
	}

	public void setDenuncias(List<Denuncia> denuncias) {
		this.denuncias = denuncias;
	}

	public Denuncia addDenuncia(Denuncia denuncia) {
		getDenuncias().add(denuncia);
		denuncia.setCivil(this);

		return denuncia;
	}

	public Denuncia removeDenuncia(Denuncia denuncia) {
		getDenuncias().remove(denuncia);
		denuncia.setCivil(null);

		return denuncia;
	}
}