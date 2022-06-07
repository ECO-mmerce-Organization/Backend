package com.generation.ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotBlank(message = "É obrigatório o preenchimento e não pode conter apenas espaço em branco!")
	@Size(min = 5, max = 255, message = "O atributo deve conter no mínimo 5 e no máximo 255 caracteres!")
	private String nome;
	
	@NotNull(message = "É obrigatório o preenchimento do atributo usuario")
	@Email(message = "O atributo usuário deve ser um e-mail!")
	private String usuario;
	
	@NotNull(message = "É obrigatório o preenchimento do atributo senha")
	@Size(min = 8, message = "O atributo senha deve conter no mínino 8 caracteres!")
	private String senha;
	
	private String foto;
	
	@Column(name="is_ong")
	@NotNull(message = "É obrigatório o preenchimento do atributo ong")
	private boolean ong;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Produto> produto;
	
	
	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public boolean isOng() {
		return ong;
	}

	public void setOng(boolean ong) {
		this.ong = ong;
	}
	
	

}
