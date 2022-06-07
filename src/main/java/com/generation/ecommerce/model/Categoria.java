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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="nome_categoria")
	@NotBlank(message = "É obrigatório o preenchimento e não pode conter apenas espaço em branco!")
	@Size(min = 5, max = 50, message = "O atributo deve conter no mínimo 5 e no máximo 50 caracteres!")
	private String nomeCat;

	@NotBlank(message = "É obrigatório o preenchimento e não pode conter apenas espaço em branco!")
	@Size(min = 5, max = 100, message = "O atributo deve conter no mínimo 5 e no máximo 100 caracteres!")
	private String tipo;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria")
	private List<Produto> produto;
	
	
	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCat() {
		return nomeCat;
	}

	public void setNomeCat(String nomeCat) {
		this.nomeCat = nomeCat;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
