package com.generation.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.ecommerce.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	public List<Categoria> findAllByNomeCatContainingIgnoreCaseOrderByNomeCat(@Param("nomeCat") String nomeCat);

	public List<Categoria> findAllByTipoContainingIgnoreCaseOrderByTipo(@Param("tipo") String tipo);

	public List<Categoria> findAllByNomeCatContainingAndTipoContainingIgnoreCase
		(@Param("nomeCat") String nomeCat,@Param("tipo") String tipo);

}
