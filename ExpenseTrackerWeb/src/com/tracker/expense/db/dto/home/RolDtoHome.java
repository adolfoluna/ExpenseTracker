package com.tracker.expense.db.dto.home;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.RolDto;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.model.Rol;
import com.tracker.expense.db.model.RolHome;

@Stateless
@Remote(PersistenceDtoRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RolDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {
	
	@EJB private RolHome rolHome;
	
	//private static final Log log = LogFactory.getLog(RolDtoHome.class);
	
	@Override
	public String getDtoQuery(SearchDto search) {
		//int idrol, int rolnum, String ruta, int version
		return "select new com.tracker.expense.db.dto.RolDto(r.idrol,r.rolnum,r.ruta,r.version) from Rol r"+addFilters(search)+addOrderField(search);
	}

	@Override
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Rol r"+addFilters(search);
	}

	@Override
	public Object fromDtoToInstance(Object dto) {
		
		if( !(dto instanceof RolDto) )
			return null;
		
		RolDto rdto = (RolDto) dto;
		Rol r = new Rol();
		
		if( rdto.getIdrol() > 0 )
			r.setIdrol(rdto.getIdrol());
		
		r.setRolnum(rdto.getRolnum());
		r.setRuta(rdto.getRuta());
		r.setVersion(rdto.getVersion());
		return r;
	}

	@Override
	public Object getInstance(SearchDto search) {
		Rol r = rolHome.findById(Integer.valueOf(search.getSearhFieldsMap().get("idrol")));
		return r;
	}
	
	private String addFilters(SearchDto search) {
		if( search.getSearhFieldsMap() != null && search.getSearhFieldsMap().containsKey("idrol") )
			return " where r.idrol="+Integer.valueOf(search.getSearhFieldsMap().get("idrol"));
		return "";
	}
	
	private String addOrderField(SearchDto search) {
		
		String temp = "";
		
		//agregar todos los campo por los que se va a ordenar los resultados
		if( search.getOrderFields() != null && search.getOrderFields().length > 0 ) {
			
			for(String x : search.getOrderFields() ) {
				if( temp.length() <= 0 )
					temp = " order by r."+x;
				else
					temp+=",r."+x;
			}
		}
		
		return temp;
	}


}
