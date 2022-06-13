package com.generation.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.ecommerce.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsuario(String usuario);
	public List<Usuario> findByOngTrue();
	
	//public Optional<Usuario> findByIdAndOngTrue(Long id);
	
	@Query (value = "select * from tb_usuarios where id = :id and is_ong = true", nativeQuery = true)
	public Optional<Usuario> BuscarOng (@Param("id") Long id);

}
