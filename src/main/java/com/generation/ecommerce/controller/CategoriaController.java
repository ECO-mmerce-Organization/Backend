package com.generation.ecommerce.controller;

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

import com.generation.ecommerce.model.Categoria;
import com.generation.ecommerce.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		List<Categoria> cat = categoriaRepository.findAll();
		if(!cat.isEmpty()) {
			return ResponseEntity.ok(categoriaRepository.findAll());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nomedacategoria/{nomecat}")
	public ResponseEntity<List<Categoria>> getByNomeCat(@PathVariable String nomecat) {
		List<Categoria> cat = categoriaRepository.findAllByNomeCatContainingIgnoreCaseOrderByNomeCat(nomecat);
		if(!cat.isEmpty()) {
			return ResponseEntity.ok(categoriaRepository.findAll());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<Categoria>> getByTipo(@PathVariable String tipo) {
		List<Categoria> cat = categoriaRepository.findAllByTipoContainingIgnoreCaseOrderByTipo(tipo);
		if(!cat.isEmpty()) {
			return ResponseEntity.ok(categoriaRepository.findAll());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/nomedacategoria/{nomeCat}/tipo/{tipo}")
	public ResponseEntity<List<Categoria>> getByNomeCatAndTipo(@PathVariable String nomeCat,
			@PathVariable String tipo) {
		List<Categoria> listCat = categoriaRepository.findAllByNomeCatContainingAndTipoContainingIgnoreCase(nomeCat, tipo);
		if (listCat.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(listCat);
	}

	@PostMapping
	public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

	}

	@PutMapping
	public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria) {
		return categoriaRepository.findById(categoria.getId()).map(resposta -> {
			return ResponseEntity.ok().body(categoriaRepository.save(categoria));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
		return categoriaRepository.findById(id)
				.map(resposta -> {
					categoriaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
