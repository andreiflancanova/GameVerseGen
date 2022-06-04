package com.generation.gameVerseGen.controller;

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

import com.generation.gameVerseGen.model.Produto;
import com.generation.gameVerseGen.repository.ProdutoRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produto")
public class ProdutoController {
	@Autowired
	private ProdutoRepository ProdutoRepository;
	//Funções do CRUD
	@GetMapping
	/*
	 	Colocando o @GetMapping, estou fazendo o programa executar o método getProduto
	 	quando eu clico em GET no Postman.
	 */
	public ResponseEntity<List<Produto>> getProduto(){
		return ResponseEntity.ok(ProdutoRepository.findAll());
	}

	//Sub-rota do Get: Pegar pelo Id
	@GetMapping ("/{id}")
	/*
		 	O @PathVariable, pega o valor diretamente pela URL com o GET do Postman.
	 */

	public ResponseEntity<Produto> getProdutoById(@PathVariable Long id){
		return ProdutoRepository.findById(id)
				//Método Option: Map e Função Lambda: resposta
				//Retorna um objeto do tipo Produto
				.map(resposta -> ResponseEntity.ok(resposta))
				//Retorna um "Not Found" no código do Postman se não houver nada
				.orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/produtora/{produtora}")

	//Aqui em cima, usamos o "/produtora" para que não corra risco de confundir a API

	public ResponseEntity<List<Produto>> getProdutoByProdutora(@PathVariable String produtora){
		return ResponseEntity.ok(ProdutoRepository.findAllByProdutoraContainingIgnoreCase(produtora));
	}

	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		/*
		 	Quando se fala de Post, temos, obrigatoriamente, que enviar
		 	informações para o meu banco de dados.A annotation @RequestBody
		 	pega a Body (o que vem no corpo da requisição)
		 */
		return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoRepository.save(produto));
	}

	@PutMapping
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto){
		/*
		 	Quando se fala de Post, temos, obrigatoriamente, que enviar
		 	informações para o meu banco de dados.A annotation @RequestBody
		 	pega a Body (o que vem no corpo da requisição)
		 */
		return ResponseEntity.status(HttpStatus.OK).body(ProdutoRepository.save(produto));
	}

	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable long id) {
		ProdutoRepository.deleteById(id);
	}

}
