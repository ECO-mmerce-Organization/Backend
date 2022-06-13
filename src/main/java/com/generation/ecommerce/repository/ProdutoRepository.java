package com.generation.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.ecommerce.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> {
	
	public List <Produto> findAllByNomeProdContainingIgnoreCaseOrderByNomeProd (@Param("nomeProd") String nomeProd);

	public List <Produto> findAllByPrecoBetween (@Param("inicio") BigDecimal inicio, @Param("fim") BigDecimal fim); 
	
	
}
