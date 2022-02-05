package com.trabajodegrado.freshfruitusuarios.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trabajodegrado.freshfruitusuarios.configuracion.ContextoSesion;
import com.trabajodegrado.freshfruitusuarios.excepciones.ConflictException;
import com.trabajodegrado.freshfruitusuarios.excepciones.DatosInvalidosExcepcion;
import com.trabajodegrado.freshfruitusuarios.modelos.Metasusuario;
import com.trabajodegrado.freshfruitusuarios.modelos.Roles;
import com.trabajodegrado.freshfruitusuarios.modelos.Usuarios;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.PaginacionDTO;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.ProgresoMetasDTO;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.RespuestaPaginada;
import com.trabajodegrado.freshfruitusuarios.modelos.dto.UsuariosDTO;
import com.trabajodegrado.freshfruitusuarios.repositorio.MetasRepositorio;
import com.trabajodegrado.freshfruitusuarios.repositorio.MetasusuarioRepositorio;
import com.trabajodegrado.freshfruitusuarios.repositorio.RolesRepositorio;
import com.trabajodegrado.freshfruitusuarios.repositorio.UsuariosRepositorio;
import com.trabajodegrado.freshfruitusuarios.seguridad.ClaveEncriptacion;
import com.trabajodegrado.freshfruitusuarios.seguridad.JWTToken;
import com.trabajodegrado.freshfruitusuarios.utilidades.Constantes;

@Service
public class UsuariosNegocio {
	
	@Autowired
	private UsuariosRepositorio usuariosRepositorio;
	
	@Autowired
	private RolesRepositorio rolRepositorio;
	
	@Autowired
	private MetasRepositorio metasRepositorio;
	
	@Autowired
	private MetasusuarioRepositorio metasUsuarioRepositorio;
	
	@Autowired
	private JWTToken jwt;
	
	
	
	public RespuestaPaginada<Usuarios> obtenerLista(PaginacionDTO paginacion) {
		
		RespuestaPaginada<Usuarios> respuestaPaginada = new RespuestaPaginada<>();
		
		Page<Usuarios> consultaProductos = usuariosRepositorio.findAll(PageRequest.of(paginacion.getPaginaActual(), paginacion.getPaginacion()));
		
        respuestaPaginada.setLista(consultaProductos.getContent());
        respuestaPaginada.setTotal(consultaProductos.getTotalElements());
        respuestaPaginada.setTotalPaginas(consultaProductos.getTotalPages());
        
        return respuestaPaginada;
        
	}
	
	public RespuestaPaginada<Usuarios> obtenerListaPendientes(PaginacionDTO paginacion) {
		
		RespuestaPaginada<Usuarios> respuestaPaginada = new RespuestaPaginada<>();
		
		Page<Usuarios> consultaProductos = usuariosRepositorio.findByIdestado(Constantes.ESTADOS_USUARIO.PENDIENTE_POR_APROBAR, PageRequest.of(paginacion.getPaginaActual(), paginacion.getPaginacion()));
		
        respuestaPaginada.setLista(consultaProductos.getContent());
        respuestaPaginada.setTotal(consultaProductos.getTotalElements());
        respuestaPaginada.setTotalPaginas(consultaProductos.getTotalPages());
        
        return respuestaPaginada;
        
	}
	
	public List<Usuarios> obtenerListaRepartidores(){
		
		Optional<Roles> rolRepartidor = rolRepositorio.findByCodigo(Constantes.ROLES.REPARTIDOR);
		if(!rolRepartidor.isPresent()) {
			throw new ConflictException("Hay inconcistencias en la configuración de roles. Contáctese con el administrador.");
		}
		return usuariosRepositorio.findByIdrol(rolRepartidor.get().getId());
	}
	
	
	public Usuarios obtenerUsuario(Integer id) {
		Optional<Usuarios> usuario = usuariosRepositorio.findById(id);	
		if(usuario.isPresent()) {
			return usuario.get();
		}else {
			throw new ConflictException("Usuario no encontrado");
		}

	}
	
	public List<ProgresoMetasDTO>  obtenerMetasUsuarioSesion() {
		
		Integer idUsuarioSesion = ContextoSesion.getUsuarioSesion();

		
		List<Metasusuario> metasUsuario = metasUsuarioRepositorio.findByIdusuario(idUsuarioSesion);
		
		Date fechaActual = new Date();
		metasUsuario = metasUsuario.stream()
				.filter(x -> 
				x.isIsactivo() && 
				!x.isIsredimido() &&
				fechaActual.before(x.getMeta().getFechafin()) &&
				fechaActual.after(x.getMeta().getFechainicio())
				).toList();
		
		List<ProgresoMetasDTO> listaProceso = new ArrayList<>();
		
		
		metasUsuario.stream().forEach(x -> 
			listaProceso.add(
				ProgresoMetasDTO.builder().cantidadAlcanzada(x.getCantidad())
					.cantidadAlcanzar(x.getMeta().getCantidad())
					.idMeta(x.getIdmeta())
					.nombreMeta(x.getMeta().getNombre())
					.fechaInicio(x.getMeta().getFechainicio())
					.fechaFin(x.getMeta().getFechafin())
					.porcentajeAlcanzado( (x.getCantidad() / x.getMeta().getCantidad()) * 100 )
				.build())
				);
	
		return listaProceso;
		
	}
	
	public String login(UsuariosDTO usuario) throws Exception {
		ContextoSesion.getUsuarioSesion();
		Optional<Usuarios> usuarioExistente = usuariosRepositorio.findByNombreusuarioAndClave(usuario.getUsuario(), ClaveEncriptacion.Encriptar(usuario.getClave()));
		if(!usuarioExistente.isPresent()) {
			throw new DatosInvalidosExcepcion("El usuario o la clave son incorrectos.");
		}
		
		if(!usuarioExistente.get().getIdestado().equals(Constantes.ESTADOS_USUARIO.ACTIVO)) {
			throw new ConflictException("El usuario no se encuentra activo.");
		}
		
		return jwt.getJWTToken(usuarioExistente.get());
	}
	
	
	public String insertarCliente(Usuarios usuario) {
		 insertar( usuario, true,false,false) ;
		 return "El cliente ha sido registrado correctamente. Debe esperar la aprobación del administrador.";
	}
	
	public String insertarAdmin(Usuarios usuario) {
		 insertar( usuario, false,true,false) ;
		 return "El usuario se ha creado correctamente";
	}
	
	public String insertarRepartidor(Usuarios usuario) {
		 insertar( usuario, false,false,true) ;
		 return "El usuario se ha creado correctamente";
	}
	
	
	private String insertar(Usuarios usuario, boolean esCliente, boolean esAdmin, boolean esRepartidor) {
		
		if(!validar(usuario)) {
			throw new DatosInvalidosExcepcion("Datos incompletos.");
		}
		
		Optional<Usuarios> nombreExistente = usuariosRepositorio.findByNombreusuario(usuario.getNombreusuario());
		
		if(nombreExistente.isPresent()) {
			throw new ConflictException("El nombre de usuario ya existe.");
		}
		Optional<Usuarios> correoExistente = usuariosRepositorio.findByCorreoelectronico(usuario.getCorreoelectronico());
		
		if(correoExistente.isPresent()) {
			throw new ConflictException("El correo electrónico ya se encuentra registrado.");
		}
		
		usuario.setClave(ClaveEncriptacion.Encriptar(usuario.getClave()));
		
		if(esCliente) {
			
			Optional<Roles> rol = rolRepositorio.findByCodigo(Constantes.ROLES.CLIENTE);
			if(!rol.isPresent()) {
				throw new ConflictException("Se encontraron problemas de configuración de roles en el sistema. Por favor contáctese con el administrador.");
			}
			
			usuario.setIdrol(rol.get().getId());
			usuario.setIdestado(Constantes.ESTADOS_USUARIO.PENDIENTE_POR_APROBAR);
		}else {
			if(esAdmin) {
				Optional<Roles> rol = rolRepositorio.findByCodigo(Constantes.ROLES.ADMIN);
				if(!rol.isPresent()) {
					throw new ConflictException("Se encontraron problemas de configuración de roles en el sistema. Por favor contáctese con el administrador.");
				}
				
				usuario.setIdrol(rol.get().getId());
			}
			if(esRepartidor) {
				Optional<Roles> rol = rolRepositorio.findByCodigo(Constantes.ROLES.REPARTIDOR);
				if(!rol.isPresent()) {
					throw new ConflictException("Se encontraron problemas de configuración de roles en el sistema. Por favor contáctese con el administrador.");
				}
				
				usuario.setIdrol(rol.get().getId());
			}
			usuario.setIdestado(Constantes.ESTADOS_USUARIO.ACTIVO);
		}
		
	
		usuariosRepositorio.save(usuario);
		
		return "El usuario se ha creado correctamente";
	}
	
	public String cambiarEstado(Integer id, Integer idNuevoEstado) {
		
		Optional<Usuarios> usuarioExistente = usuariosRepositorio.findById(id);
		
		if(!usuarioExistente.isPresent()) {
			throw new ConflictException("No se encontró el usuario.");
		}
		
		if(idNuevoEstado.equals(Constantes.ESTADOS_USUARIO.ACTIVO)) {
			if(!usuarioExistente.get().getIdestado().equals(Constantes.ESTADOS_USUARIO.PENDIENTE_POR_APROBAR)) {
				throw new DatosInvalidosExcepcion("El usuario no se encuentra pendiente por aprobar.");
			}
			usuarioExistente.get().setIdestado(Constantes.ESTADOS_USUARIO.ACTIVO);
		}
		
		if(idNuevoEstado.equals(Constantes.ESTADOS_USUARIO.RECHAZADO)) {
			if(!usuarioExistente.get().getIdestado().equals(Constantes.ESTADOS_USUARIO.PENDIENTE_POR_APROBAR)) {
				throw new DatosInvalidosExcepcion("El usuario no se encuentra pendiente por aprobación.");
			}
			usuarioExistente.get().setIdestado(Constantes.ESTADOS_USUARIO.RECHAZADO);
		}
		
		if(idNuevoEstado.equals(Constantes.ESTADOS_USUARIO.INACTIVO)) {
			if(!usuarioExistente.get().getIdestado().equals(Constantes.ESTADOS_USUARIO.ACTIVO)) {
				throw new DatosInvalidosExcepcion("El usuario no se encuentra en estado activo.");
			}
			usuarioExistente.get().setIdestado(Constantes.ESTADOS_USUARIO.INACTIVO);
		}
		
		
		
		return "El estado del usuario se ha actualizado correctamente";
	}
	
	
	private boolean validar(Usuarios usuario) {
		if(
				usuario.getCelular() != null && !usuario.getCelular().isEmpty() &&
				usuario.getIdrol() != null && !usuario.getIdrol().equals(0) &&
				usuario.getNombreusuario() != null && !usuario.getNombreusuario().isEmpty() &&
				usuario.getNombre() != null && !usuario.getNombre().isEmpty() &&
				usuario.getClave() != null && !usuario.getClave().isEmpty() &&
				usuario.getCorreoelectronico() != null && !usuario.getCorreoelectronico().isEmpty() &&
				usuario.getDireccion() != null && !usuario.getDireccion().isEmpty()
				) {
			return true;
		}
		return false;
	}
	
	
	
	
}
