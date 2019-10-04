package com.tracker.expense.db.home;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.RolDto;
import com.tracker.expense.db.dto.UsuarioDto;

@Stateless
public class RolUsuarioHome {

	@PersistenceContext private EntityManager entityManager;
	private static final Log log = LogFactory.getLog(RolUsuarioHome.class);
	
	private HashMap<String,UsuarioDto> usuarios = new HashMap<String,UsuarioDto>();
	private HashMap<String,RolDto> rolesRutas = new HashMap<String,RolDto>();
	
	public int getRolUsuario(String username,String token) {
		
		//buscar en el mapa si esta el usuario
		if( usuarios.containsKey(token) ) {
			
			UsuarioDto user = usuarios.get(token);
			
			long temp = new Date().getTime();
			
			if( user.getExpirationTime() > temp )
				return user.getRolnum();
			else
				usuarios.remove(token);
		}
		
		//int idusuario, String clave, boolean admin, boolean activo, String token, int rolnum,int version
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.UsuarioDto(u.idusuario,u.clave,u.activo,u.token,u.rolnum,u.version) from Usuario u where u.usuario=:usuario and u.token=:token");
		q.setParameter("token", token);
		q.setParameter("usuario", username);
		
		try {
			//buscar instancia
			UsuarioDto user = (UsuarioDto) q.getSingleResult();
			
			//no se encontro salir
			if( user == null )
				return 0;
			
			//agregar a mapa la instancia encontrada
			usuarios.put(token, user);
			
			//regresar a que rol pertenece el usuario
			return user.getRolnum();
			
		}catch(NoResultException e) {
			return 0;
		}
		
	}
	
	public boolean isRoleAllowed(int roleNumber,String route) {
		
		if( rolesRutas.containsKey(roleNumber+"-"+route) ) {
			
			RolDto r = rolesRutas.get(roleNumber+"-"+route);
			
			if( !r.isExpired() ) {
				log.info("rol:"+roleNumber+" ruta:"+route+" permitido:"+r.isPermitido());
				return r.isPermitido();
			}
			
			rolesRutas.remove(roleNumber+"-"+route);
		}
		
		//RolDto(int idrol, int rolnum, String ruta, int version) 
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.RolDto(r.idrol,r.rolnum,r.ruta,r.version) from Rol r where r.rolnum=:rolnum and r.ruta=:ruta");
		q.setParameter("rolnum", roleNumber);
		q.setParameter("ruta", route);
		
		@SuppressWarnings("unchecked")
		List<RolDto> lista = q.getResultList();
		
		for( RolDto rol : lista ) {
			rolesRutas.put(roleNumber+"-"+route, rol);
		}
		
		if( rolesRutas.containsKey(roleNumber+"-"+route) ) {
			RolDto r = rolesRutas.get(roleNumber+"-"+route);
			log.info("rol:"+roleNumber+" ruta:"+route+" permitido:"+r.isPermitido());
			return r.isPermitido();
			
		}
		
		RolDto r = new RolDto(0,roleNumber,route,0);
		r.setPermitido(false);
		rolesRutas.put(roleNumber+"-"+route, r);
		log.info("rol:"+roleNumber+" ruta:"+route+" permitido:"+r.isPermitido());
		
		return false;
	}
	
}
