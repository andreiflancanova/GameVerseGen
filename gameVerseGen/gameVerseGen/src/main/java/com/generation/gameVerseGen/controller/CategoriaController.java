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

import com.generation.gameVerseGen.model.Categoria;
import com.generation.gameVerseGen.repository.CategoriaRepository;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/categoria")
public class CategoriaController {
	@Autowired
	private CategoriaRepository CategoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.ok(CategoriaRepository.findAll());
	}
	
	//Criar uma sub-rota
	@GetMapping ("/{id}")
	/*
	 	Quando usamos a anotação @PathVariable, estamos fazendo ela pegar o valor
	 	diretamente pela URL.
	*/
	
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return CategoriaRepository.findById(id)
				//Método Option: Map e Função Lambda: resposta
				//Retorna um objeto do tipo Tema e renderiza na Body
				.map(resposta -> ResponseEntity.ok(resposta))
				//Retorna um "Not Found" no código do Postman se não houver nada
				.orElse(ResponseEntity.notFound().build());
	}
	
	//Criar uma sub-rota para busca pelo nome
		@GetMapping ("/categoria/{nome}")		
		public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nome){
			return ResponseEntity.ok(CategoriaRepository.findAllByNomeContainingIgnoreCase(nome));
		}
		
		@PostMapping
		public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria){
			return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaRepository.save(categoria));
		}
		
		@PutMapping
		public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria){
			return ResponseEntity.ok(CategoriaRepository.save(categoria));
		}
		
		@DeleteMapping ("/{id}")
		public void delete(@PathVariable Long id) {
			CategoriaRepository.deleteById(id);
		}
}
