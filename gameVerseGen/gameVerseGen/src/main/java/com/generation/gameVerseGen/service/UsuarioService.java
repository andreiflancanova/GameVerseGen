package com.generation.gameVerseGen.service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.gameVerseGen.model.Usuario;
import com.generation.gameVerseGen.model.UsuarioLogin;
import com.generation.gameVerseGen.repository.UsuarioRepository;


@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario CadastrarUsuario(Usuario usuario){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		return usuarioRepository.save(usuario);
	}

	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(user.get().getUsuario());

		//Verificação de existência do usuário
		if(usuario.isPresent()) 
		{
			//Comparação da senha digitada com a senha encriptada salva no BD
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) 
			{
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();

				//Criação do Array de Byte
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

				String authHeader = "Basic " + new String(encodedAuth);			
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());

				return user;
			}
		}
		return null;
	}


}
