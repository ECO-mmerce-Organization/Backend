package com.generation.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

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
import com.generation.ecommerce.repository.CategoriaRepository;
import com.generation.ecommerce.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nomedoproduto/{nomeProd}") //New test
	public ResponseEntity<List<Produto>> getByNomeProd(@PathVariable String nomeProd) {
		List<Produto> listProd = produtoRepository.findAllByNomeProdContainingIgnoreCaseOrderByNomeProd(nomeProd);
		if(listProd.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listProd);
	}
	
	@GetMapping("/valorinicial/{inicio}/valorfinal/{fim}")
	public ResponseEntity <List<Produto>> getByPrecoBetween(@PathVariable BigDecimal inicio, @PathVariable BigDecimal fim){
		List<Produto> listProd = produtoRepository.findAllByPrecoBetween(inicio, fim);
		if(listProd.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listProd);
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto) {
	 boolean prod = categoriaRepository.existsById(produto.getCategoria().getId());
	 if(prod != true)
		 return ResponseEntity.notFound().build();
	 return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {
			if(produtoRepository.existsById(produto.getId())) {
				if(categoriaRepository.existsById(produto.getCategoria().getId())) 
					return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
					
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> {
			produtoRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

	
}
