package com.generation.gameVerseGen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.gameVerseGen.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> {
	public List<Produto> findAllByProdutoraContainingIgnoreCase(String produtora);
}
