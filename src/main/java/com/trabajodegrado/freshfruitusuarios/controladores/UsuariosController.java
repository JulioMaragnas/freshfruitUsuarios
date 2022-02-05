package com.trabajodegrado.freshfruitusuarios.controladores;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trabajodegrado.freshfruitusuarios.configuracion.ContextoSesion;
import com.trabajodegrado.freshfruitusuarios.modelos.Usuarios;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.PaginacionDTO;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.ProgresoMetasDTO;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.RespuestaPaginada;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.UsuariosDTO;
import com.trabajodegrado.freshfruitusuarios.negocio.UsuariosNegocio;
import com.trabajodegrado.freshfruitusuarios.utilidades.Constantes;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RequestMapping("/usuarios")
public class UsuariosController {
	
	 @Autowired
	 private UsuariosNegocio usuariosNegocio;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UsuariosDTO usuario) throws Exception {
		return new ResponseEntity<>(usuariosNegocio.login(usuario), HttpStatus.OK);
	}

	@GetMapping("/")
    public ResponseEntity<RespuestaPaginada<Usuarios>> obtenerLista(@PathParam("paginacion") PaginacionDTO paginacion) {
		return new ResponseEntity<>(usuariosNegocio.obtenerLista(paginacion), HttpStatus.OK);
    }
	 
	@GetMapping("/obtenerListaPendientes")
    public ResponseEntity<RespuestaPaginada<Usuarios>> obtenerListaPendientes(@PathParam("paginacion") PaginacionDTO paginacion) {
		return new ResponseEntity<>(usuariosNegocio.obtenerListaPendientes(paginacion), HttpStatus.OK);
    }
	
	@GetMapping("/obtenerListaRepartidores")
    public ResponseEntity<List<Usuarios>> obtenerListaRepartidores() {
		return new ResponseEntity<>(usuariosNegocio.obtenerListaRepartidores(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(usuariosNegocio.obtenerUsuario(id), HttpStatus.OK);
    }
    @GetMapping("/obtenerUsuarioSesion")
    public ResponseEntity<Usuarios> obtenerUsuarioSesion() {
        return new ResponseEntity<>(usuariosNegocio.obtenerUsuario(ContextoSesion.getUsuarioSesion()), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerMetasUsuarioSesion")
    public ResponseEntity<List<ProgresoMetasDTO>> obtenerMetasUsuarioSesion() {
        return new ResponseEntity<>(usuariosNegocio.obtenerMetasUsuarioSesion(), HttpStatus.OK);
    }
    
	@PostMapping("/insertarAdmin")
	public ResponseEntity<String> insertarAdmin(@RequestBody Usuarios usuario) throws Exception {
		return new ResponseEntity<>(usuariosNegocio.insertarAdmin(usuario), HttpStatus.OK);
	}
	
	@PostMapping("/insertarRepartidor")
	public ResponseEntity<String> insertarRepartidor(@RequestBody Usuarios usuario) throws Exception {
		return new ResponseEntity<>(usuariosNegocio.insertarRepartidor(usuario), HttpStatus.OK);
	}
	
	@PostMapping("/insertarCliente")
	public ResponseEntity<String> insertarCliente(@RequestBody Usuarios usuario) throws Exception {
		return new ResponseEntity<>(usuariosNegocio.insertarCliente(usuario), HttpStatus.OK);
	}
	
	@PutMapping("/marcarAprobado/{id}")
    public ResponseEntity<String> marcarAprobado(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(usuariosNegocio.cambiarEstado(id, Constantes.ESTADOS_USUARIO.ACTIVO), HttpStatus.OK);
    }
	
	@PutMapping("/marcarRechazado/{id}")
    public ResponseEntity<String> marcarRechazado(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(usuariosNegocio.cambiarEstado(id, Constantes.ESTADOS_USUARIO.RECHAZADO), HttpStatus.OK);
    }
	
	@PutMapping("/marcarInactivo/{id}")
    public ResponseEntity<String> marcarInactivo(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(usuariosNegocio.cambiarEstado(id, Constantes.ESTADOS_USUARIO.INACTIVO), HttpStatus.OK);
    }
	
	//Por el momento no se tiene actualizar
}
