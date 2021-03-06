package com.generation.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.ecommerce.model.Produto;
import com.generation.ecommerce.model.Usuario;
import com.generation.ecommerce.repository.CategoriaRepository;
import com.generation.ecommerce.repository.ProdutoRepository;
import com.generation.ecommerce.repository.UsuarioRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping ("/all")
	public ResponseEntity<List<Produto>> getAll() {
		List<Produto> prod = produtoRepository.findAll();
		if(!prod.isEmpty()) {
			return ResponseEntity.ok(produtoRepository.findAll());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nomedoproduto/{nomeProd}") // New test
	public ResponseEntity<List<Produto>> getByNomeProd(@PathVariable String nomeProd) {
		List<Produto> listProd = produtoRepository.findAllByNomeProdContainingIgnoreCaseOrderByNomeProd(nomeProd);
		if (listProd.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listProd);
	}

	@GetMapping("/valorinicial/{inicio}/valorfinal/{fim}")
	public ResponseEntity<List<Produto>> getByPrecoBetween(@PathVariable BigDecimal inicio,
			@PathVariable BigDecimal fim) {
		List<Produto> listProd = produtoRepository.findAllByPrecoBetween(inicio, fim);
		if (listProd.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listProd);
	}

	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto) {
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			Optional<Usuario> checarUser = usuarioRepository.findByIdAndOngTrue(produto.getUsuario().getId());
			if (checarUser.isPresent())
				return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			Optional<Usuario> checarUser = usuarioRepository.findByIdAndOngTrue(produto.getUsuario().getId());
			if (checarUser.isPresent())
				return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
		}
		return ResponseEntity.badRequest().build();
	}

	/*
	 * Optional<Usuario> checarUser =
	 * usuarioRepository.BuscarOng(produto.getUsuario().getId()); if
	 * (produtoRepository.existsById(produto.getId()) && checarUser.isPresent()) {
	 * if (categoriaRepository.existsById(produto.getCategoria().getId())) return
	 * ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
	 * return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); } return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		Optional<Produto> prod = produtoRepository.findById(id);
		if (prod.isPresent()) {
			return usuarioRepository.findById(prod.get().getUsuario().getId())
					.map(resposta -> {
						produtoRepository.deleteById(prod.get().getId());
						return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
					})
					.orElse(ResponseEntity.notFound().build());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
