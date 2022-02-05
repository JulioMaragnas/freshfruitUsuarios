package com.trabajodegrado.freshfruitusuarios.seguridad;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.trabajodegrado.freshfruitusuarios.modelos.Usuarios;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTToken {

	private String secret = "#freshFruit2022";
	

	
	public String getJWTToken(Usuarios usuario) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROL_ADMIN");		
				
		String token = Jwts
				.builder()
				.setId("freshFruit")
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.claim("idusuario", usuario.getId())
				.claim("rol", usuario.getRoles().getCodigo())
				.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
				.signWith(SignatureAlgorithm.HS512,
						secret.getBytes()).compact();

		return "Bearer " + token;
	}
	
	
	public String generarClaveTemoral() {
	    
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String token = bytes.toString();

		return token;
	}
	
	
}
