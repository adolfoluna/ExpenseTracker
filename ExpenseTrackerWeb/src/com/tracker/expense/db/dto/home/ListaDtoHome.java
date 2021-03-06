package com.tracker.expense.db.dto.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.ListaDto;

@Stateless
public class ListaDtoHome {

	private static final Log log = LogFactory.getLog(ListaDtoHome.class);
	@PersistenceContext private EntityManager entityManager;
	
	
	public ListaDto[] getProveedoresOrdenadosPorNombre() {
		log.info("obteniendo lista de proveedores........");
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.ListaDto(p.idproveedor,p.nombre) from Proveedor p order by p.nombre");
		@SuppressWarnings("unchecked")
		List<ListaDto> lista = q.getResultList();
		if(lista == null)
			return null;
		return lista.toArray(new ListaDto[0]);
	}
	
	public ListaDto[] getArticulosOrdenadosPorNombre() {
		log.info("obteniendo lista de articulos ordenado por nombre........");
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.ListaDto(a.idarticulo,a.categoria.idcategoria,concat(a.categoria.nombre,'-',a.nombre)) from Articulo a order by a.categoria.nombre,a.nombre");
		@SuppressWarnings("unchecked")
		List<ListaDto> lista = q.getResultList();
		if( lista == null )
			return null;
		return lista.toArray(new ListaDto[0]);
	}
	
	public ListaDto[] getCuentasOrdenadosPorNombre() {
		log.info("obteniendo lista de cuentas ordenadas por nombre........");
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.ListaDto(a.idcuenta,a.idcuenta,a.nombre) from Cuenta a order by a.nombre");
		@SuppressWarnings("unchecked")
		List<ListaDto> lista = q.getResultList();
		if( lista == null )
			return null;
		return lista.toArray(new ListaDto[0]);
	}
	
	public ListaDto[] getCategoriasOrdenadosPorNombre() {
		log.info("obteniendo lista de categorias ordenadas por nombre........");
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.ListaDto(a.idcategoria,a.idcategoria,a.nombre) from Categoria a order by a.nombre");
		@SuppressWarnings("unchecked")
		List<ListaDto> lista = q.getResultList();
		if( lista == null )
			return null;
		return lista.toArray(new ListaDto[0]);
	}
	
	public ListaDto[] getGruposPorNombre() {
		log.info("obteniendo lista de categorias ordenadas por nombre........");
		Query q = entityManager.createQuery("select new com.tracker.expense.db.dto.ListaDto(a.idgrupo,a.idgrupo,a.nombre) from Grupo a order by a.nombre");
		@SuppressWarnings("unchecked")
		List<ListaDto> lista = q.getResultList();
		if( lista == null )
			return null;
		return lista.toArray(new ListaDto[0]);
	}
}
