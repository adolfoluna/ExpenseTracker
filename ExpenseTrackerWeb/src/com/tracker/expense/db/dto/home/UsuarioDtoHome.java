package com.tracker.expense.db.dto.home;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.dto.UsuarioDto;

import com.tracker.expense.db.model.Usuario;
import com.tracker.expense.db.model.UsuarioHome;

@Stateless
@Remote(PersistenceDtoRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsuarioDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {
	
	@EJB private UsuarioHome usuarioHome;
	private static final Log log = LogFactory.getLog(UsuarioDtoHome.class);
	
	@Override
	public String getDtoQuery(SearchDto search) {
		//int idusuario, String usuario, String clave, int activo, String token, int rolnum,int version
		return "select new com.tracker.expense.db.dto.UsuarioDto(u.idusuario,u.usuario,u.clave,u.activo,u.token,u.rolnum,u.version) from Usuario u"+addFilters(search);
	}

	@Override
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Usuario u"+addFilters(search);
	}

	@Override
	public Object fromDtoToInstance(Object dto) {
		
		if( !(dto instanceof UsuarioDto) )
			return null;
		
		UsuarioDto udto = (UsuarioDto) dto;
		Usuario u = null;
		
		if(udto.getIdusuario() > 0 ) {
			u = usuarioHome.findById(udto.getIdusuario());
			if( u == null ) {
				log.info("error usuario "+udto.getIdusuario()+" no encontrado");
				return null;
			}
		} else
			u = new Usuario();

		//actualizar el campo de activo
		if( udto.isActivo()) u.setActivo(1);
		else u.setActivo(0);
		
		//modificar clave solo si esta especificada en el request
		if( udto.getClave() != null && udto.getClave().trim().length() > 0 )
			u.setClave(udto.getClave());
				
		u.setRolnum(udto.getRolnum());
		
		//si el token esta en blanco o en nulo asignar null a la instancia
		if( udto.getToken() == null || udto.getToken().trim().length() <= 0)
			u.setToken(null);
		else 
			//token especificado, actualizar instancia con el token especificado		
			u.setToken(udto.getToken());
		
		u.setUsuario(udto.getUsuario());
		u.setVersion(udto.getVersion());
	
		return u;
	}

	@Override
	public Object getInstance(SearchDto search) {
		Usuario u = usuarioHome.findById(Integer.valueOf(search.getSearhFieldsMap().get("idusuario")));
		return u;
	}
	
	private String addFilters(SearchDto search) {
		if( search.getSearhFieldsMap() != null && search.getSearhFieldsMap().containsKey("idusuario") )
			return " where u.idusuario="+Integer.valueOf(search.getSearhFieldsMap().get("idusuario"));
		return "";
	}


}
