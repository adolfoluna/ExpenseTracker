package com.tracker.expense.db.dto.home;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.CuentaDto;
import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.db.home.MonedaBaseHome;
import com.tracker.expense.db.home.MonedaBaseInterceptor;
import com.tracker.expense.db.home.TipocambioHome2;
import com.tracker.expense.db.model.Cuenta;
import com.tracker.expense.db.model.CuentaHome;
import com.tracker.expense.db.model.Moneda;
import com.tracker.expense.db.model.Tipocambio;
import com.tracker.expense.web.rest.service.OperationRestResult;

@Stateless
@Remote(PersistenceDtoRemote.class)
@Interceptors(MonedaBaseInterceptor.class)
public class CuentaDtoHome extends ParentPersistenceHome implements PersistenceDtoRemote {
	
	@PersistenceContext private EntityManager entityManager;
	
	@EJB private TipocambioHome2 tipocambioHome;
	@EJB private CuentaHome cuentaHome;
	
	private static final Log log = LogFactory.getLog(CuentaDtoHome.class);
	
	//asigna todos los valores del dto al objeto entidad y regresa el objeto entidad
	public Object fromDtoToInstance(Object dto) {
		
		//validar que el argumento sea de tipo CategoriaDto
		if( !(dto instanceof CuentaDto) )
			return null;
		
		CuentaDto cdto = (CuentaDto) dto;
		
		Cuenta instance = new Cuenta();
		
		if( cdto.getIdcuenta() > 0 )
			instance.setIdcuenta(cdto.getIdcuenta());
		else
			instance.setIdcuenta(null);
		
		if( cdto.getIdmoneda() > 0 ) {
			Moneda m = new Moneda();
			m.setIdmoneda(cdto.getIdmoneda());
			instance.setMoneda(m);
		}
		
		instance.setNombre(cdto.getNombre());
		instance.setSaldo(cdto.getSaldo());
		instance.setTipo(cdto.getTipo());
		instance.setVersion(cdto.getVersion());
		
		return instance;
	}
	
	//encontrar la instancia con el id del registro
	public Object getInstance(SearchDto search) {
		HashMap<String, String> temp = search.getSearhFieldsMap();
		Cuenta c = cuentaHome.findById(Integer.valueOf(temp.get("idcuenta")));
		return c;
	}

	//hacer query, agregando busquedas y ordenamientos
	public String getDtoQuery(SearchDto search) {
		
		String temp = addFilters(search);
		
		//agregar todos los campo por los que se va a ordenar los resultados
		if( search.getOrderFields() != null && search.getOrderFields().length > 0 ) {
			
			for(String x : search.getOrderFields() ) {
				if( temp.length() <= 0 )
					temp = " order by c."+x;
				else
					temp+=",c."+x;
			}
		}
		
		return "select new com.tracker.expense.db.dto.CuentaDto(c.idcuenta,c.nombre,c.saldo,c.moneda.idmoneda,c.moneda.nombre,c.moneda.monedaBase,c.tipo,c.version) from Cuenta c"+temp;
	}
	
	//hacer el query que cuenta el numero de resultados
	public String getCountDtoQuery(SearchDto search) {
		return "select count(*) from Cuenta"+addFilters(search);
	}
	
	private String addFilters(SearchDto search) {
		
		HashMap<String, String> aux = search.getSearhFieldsMap();
		
		if( aux != null && aux.containsKey("idcuenta") ) 
			return " where c.idcuenta="+Integer.valueOf(aux.get("idcuenta"));
		
		return "";
	}
	
	public OperationRestResult listDto(SearchDto search) {
		
		//intentar extraer la lista de resultados
		OperationRestResult res = super.listDto(search);
		
		//si no se pudieron extraer los resultados entonces salir
		if( res == null || !res.isSuccess() || !(res.getData() instanceof Object[]) )
			return res;
		
		//convertir la moneda de cada cuenta en moneda base
		convertirMonedas( (Object []) res.getData());
		
		//regresar resultado
		return res;
	
	}

	private void convertirMonedas(Object[] lista) {
		
		if( lista == null || lista.length <= 0 )
			return;
		
		double total = 0;
		
		for( Object temp : lista ) {
			
			CuentaDto c = (CuentaDto) temp;
			
			if( c.getIdmoneda() == MonedaBaseHome.getMonedaBase() ) {
				c.setSaldoMonedaBase(c.getSaldo());
				continue;
			}
			
			log.info("buscando conversion de moneda "+c.getIdmoneda()+"-"+c.getMoneda()+" a "+MonedaBaseHome.getMonedaBase()+"-"+MonedaBaseHome.getMonedaBaseNombre());
			
			Tipocambio t = tipocambioHome.getTipocambio(c.getIdmoneda(), MonedaBaseHome.getMonedaBase());
				
			if( t == null ) 
				log.info("no se encontro conversion de moneda de "+c.getIdmoneda()+"-"+c.getMoneda()+" a "+MonedaBaseHome.getMonedaBase()+"-"+MonedaBaseHome.getMonedaBaseNombre());
			else {
				log.info("conversion de moneda encontrada tipo de cambio:"+t.getTipocambio()+" origen:"+t.getMonedaByIdmonedaOrigen().getIdmoneda()+" a "+t.getMonedaByIdmonedaDestino().getIdmoneda());
				total =  ((double) c.getSaldo() * t.getTipocambio());
				c.setSaldoMonedaBase((long)total);
			}

		}
	}

}
