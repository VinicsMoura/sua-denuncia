package com.suadenuncia.pojo;

import java.io.Serializable;

import java.sql.Timestamp;

public class Denuncia implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String categoria;

	private Timestamp criadoEm;

	private String descricao;

	private float latitude;

	private float longitude;

	private byte[] midia;

	private String nome;

	private String status;

	private Civil civil;

	public Denuncia() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Timestamp getCriadoEm() {
		return this.criadoEm;
	}

	public void setCriadoEm(Timestamp criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public byte[] getMidia() {
		return this.midia;
	}

	public void setMidia(byte[] midia) {
		this.midia = midia;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Civil getCivil() {
		return this.civil;
	}

	public void setCivil(Civil civil) {
		this.civil = civil;
	}

}