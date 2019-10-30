package com.tracker.expense.db.dto.home;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.model.Transaccion;
import com.tracker.expense.db.model.TransaccionArticulo;
import com.tracker.expense.db.model.TransaccionHome;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Remote(TransaccionDescripcionUpdateRemote.class)
public class TransaccionDescripcionUpdateHome implements TransaccionDescripcionUpdateRemote {

	@EJB private TransaccionHome transaccionHome;
	private static final Log log = LogFactory.getLog(TransaccionDescripcionUpdateHome.class);
	
	@Override
	public void updateDescription(int idtransaccion) {
		
		log.info("intentando actualizar descripcion de transaccion "+idtransaccion);
		
		try {
			
			//buscar la transaccion en la base de datos
			Transaccion t = transaccionHome.findById(idtransaccion);
			
			//tomar todos los articulos de la transaccion encontrada
			Set<TransaccionArticulo> lista = t.getTransaccionArticulos();
			
			//si la transaccion no tiene articulos entonces poner en null la descripcion
			if( lista == null || lista.size() <= 0 ) {
				t.setArticulos(null);
				t.setCategoria(null);
				transaccionHome.merge(t);
				log.info("descripcion de transaccion "+idtransaccion+" exitosamente actualizada");
				return;
			}
			
			//coleccion de articulos
			Set<String> articulos = new HashSet<String>();
			
			//coleccion de categorias de articulos
			Set<String> categorias = new HashSet<String>();
			
			//tomar todos los nombres de todos los articulos y tambien las respectivas categorias
			for( TransaccionArticulo transaccionarticulo: lista ) {
				articulos.add(transaccionarticulo.getArticulo().getNombre().trim());
				categorias.add(transaccionarticulo.getArticulo().getCategoria().getNombre().trim());
			}
			
			//unir todos los resultados encontrados con una coma 
			String res =  String.join(",", articulos);
			String res2 = String.join(",", categorias);
			
			//cortar hasta los primeros 50 caracteres si la cadena es muy larga
			if( res.length() > 50 )
				res = res.substring(0, 51);
			
			//cortar hasta los primeros 50 caracteres si la cadena es muy larga
			if( res2.length() > 50 )
				res2 = res2.substring(0, 51);
			
			//actualizar la lista articulos
			t.setArticulos(res);
			
			//actualizar la lista de categorias
			t.setCategoria(res2);
			
			//actualizar la base de datos
			transaccionHome.merge(t);
			
			log.info("descripcion de transaccion "+idtransaccion+" exitosamente actualizada");
			
		}catch(Exception ex) {
			log.error("error al intentar actualizar descripcion de transaccion "+idtransaccion+" "+ex.getMessage());
			ex.printStackTrace();
		}
			
	}
	
}
