package com.tracker.expense.db.home;

import java.security.SecureRandom;
import java.util.Base64;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.UsuarioDto;
import com.tracker.expense.db.model.Usuario;

@Stateless
public class UsuarioHome2 {

	@PersistenceContext private EntityManager entityManager;
	
	private static final Log log = LogFactory.getLog(UsuarioHome2.class);
	
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

	
	public UsuarioDto userLogin(String user,String pwd) {
		
		try {
			
			log.info("bucando usuario:"+user+" con pwd:"+pwd+".........");
			
			//busar el usuario especificado
			Query q = entityManager.createQuery("from Usuario user where user.usuario=:user and clave=:pwd and activo>0");
			q.setParameter("user", user);
			q.setParameter("pwd", pwd);
			
			//buscar el usuario en la base de datos
			Usuario entity = (Usuario) q.getSingleResult();
			
			//indicar que se encontro el usuario
			log.info("usuario:"+user+" con pwd:"+pwd+" encontrado");
			
			//si el usuario esta desactivado, indicar y salir
			if( entity.getActivo() <= 0 ) {
				log.info("usuario:"+user+" desactivado, no es posible iniciar sesion");
				return null;
			}
			
			//generar token
			log.info("generando token de usuario:"+user+"...........");
			byte[] randomBytes = new byte[24];
			secureRandom.nextBytes(randomBytes);
			String token = base64Encoder.encodeToString(randomBytes);
			log.info("token:"+token+" usuario:"+user);
			
			//guardar token generado en la base de datos
			entity.setToken(token);
			entityManager.merge(entity);
			
			//regresar el token generado
			return new UsuarioDto(user, token, entity.getRolnum());
			
		}catch(NoResultException e) {
			log.info("usuario:"+user+" con pwd:"+pwd+" no encontrado");
			return null;
		}
	}
	
	public UsuarioDto userExternalLogin(String user,String token) {
		
		try {
			
			log.info("bucando usuario:"+user+".........");
			
			//busar el usuario especificado
			Query q = entityManager.createQuery("from Usuario user where user.usuario=:user and activo>0");
			q.setParameter("user", user);
			
			//buscar el usuario en la base de datos
			Usuario entity = (Usuario) q.getSingleResult();
			
			if( entity == null ) {
				log.info("usuario:"+user+" no encontrado");
				return null;
			}
			
			log.info("usuario:"+user+" encontrado");
			
			log.info("actualizando sesion de usuario "+user+"........");
			
			//borrar token en la base de datos
			entity.setToken(token);
			entityManager.merge(entity);
			
			return new UsuarioDto(entity.getUsuario(),token,entity.getRolnum());
		
		}catch(NoResultException e) {
			log.info("usuario:"+user+" no encontrado");
			return null;
		}
	}
	
	public boolean logout(String user) {
		
		log.info("bucando usuario:"+user+".........");
		
		try {
			//busar el usuario especificado
			Query q = entityManager.createQuery("from Usuario user where user.usuario=:user");
			q.setParameter("user", user);
			
			//buscar el usuario en la base de datos
			Usuario entity = (Usuario) q.getSingleResult();
			
			if( entity == null ) {
				log.info("usuario:"+user+" no encontrado");
				return false;
			}
			
			log.info("usuario:"+user+" encontrado");
			
			log.info("borrando sesion de usuario "+user+"........");
			
			//borrar token en la base de datos
			entity.setToken(null);
			entityManager.merge(entity);
			
			return true;
		
		}catch(NoResultException e) {
			log.info("usuario:"+user+" no encontrado");
			return false;
		}
	}
}
