package com.tracker.expense.web.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracker.expense.db.dto.GraphDataDto;
import com.tracker.expense.db.dto.home.GraphDataDtoHome;

@RequestScoped
@Path("/graph")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class GraphDataRestService {

	@EJB private GraphDataDtoHome graphdataHome;
	private static final Log log = LogFactory.getLog(GraphDataRestService.class);
	
	@GET
	@Path("/transactions/month/{limit}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult getTransactionsSumByMonth(@PathParam("limit") int limit,@PathParam("year") int year) {
		try {
			List<GraphDataDto> res = graphdataHome.getTransactionsSumByMonth(limit,year);
			
			if( res == null )
				return new OperationRestResult(false, "error, no se encontro lista de proveedores");
			
			return new OperationRestResult(res.toArray(new GraphDataDto[0]));
			
		}catch(Exception ex) {
			log.error("error al intentar consultar la suma de transacciones por mes "+ex.getMessage());
			return new OperationRestResult(false, "error "+ex.getMessage());
		}
	}
}
