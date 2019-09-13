package com.tracker.expense.web.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.tracker.expense.db.dto.home.TransaccionAttachmentRemote;
import com.tracker.expense.web.util.AddRemoveFileUtil;

@RequestScoped
@Path("/archivo")
public class UploadFileRestService {
	
	private static final Log log = LogFactory.getLog(UploadFileRestService.class);
	
	@EJB private TransaccionAttachmentRemote transaccionAttachmentRemote;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "service /archivo/test working";
	}
	
	@GET
	@Path("/eliminar/{idtransaccion}/{tipocomprobante}")
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upload(
			@PathParam("idtransaccion") int idtransaccion,
			@PathParam("tipocomprobante") String tipo,
			@Context ServletContext servletContext) {
		
		log.info("invocando servicio eliminar archivo de la transaccion "+idtransaccion+" tipo:"+tipo);
		
		//intentar poner el campo del documento en nulo
		OperationRestResult r = transaccionAttachmentRemote.removeAttachment(idtransaccion, tipo);
		
		//si la operacion no fue exitosa salir
		if( !r.isSuccess() )
			return r;
		
		if( AddRemoveFileUtil.removeFile(servletContext.getContextPath(), r.getMessage()) )
			return new OperationRestResult(true, null);
		else
			return new OperationRestResult(false, "error al intentar borrar logicamente el archivo");
		
	}

	@POST
	@Path("/subir/{idtransaccion}/{tipocomprobante}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationRestResult upload(
							@PathParam("idtransaccion") int idtransaccion,
							@PathParam("tipocomprobante") String tipo,
							MultipartFormDataInput input,
							@Context ServletContext servletContext
							) throws IOException {
		
		log.info("invocando servicio para guardar archivo de la transaccion "+idtransaccion+" tipo:"+tipo);
		
		//Get API input data
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        
        if( uploadForm.containsKey("file") == false ) 
        	return new OperationRestResult(false, "error no se encontro el valor file");
        
        List<InputPart> inputParts2 = uploadForm.get("file");
        
        if( inputParts2 == null ) 
        	return new OperationRestResult(false,"error file en null");
        
        for( InputPart inputPart : inputParts2 ) {
        		
        	try {
        		
        		String fileName = getFileName(idtransaccion,tipo,inputPart);
        		
        		// convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                
                //leer todos los bytes del stream
                byte []byteStream = inputStream.readAllBytes();
                
                //cerrar el stream
                inputStream.close();
        		
                //si no se pudo guardar archivo salir
        		if( !AddRemoveFileUtil.addFile(servletContext.getContextPath(), fileName, byteStream) )
        			return new OperationRestResult(false, "error al intentar guardar archivo "+fileName);
        		
        		 //extraer solo el nombre de archivo sin el path
                fileName = fileName.substring(fileName.lastIndexOf("/")+1);
                
                //guardar el nombre del archivo en la base de datos
                transaccionAttachmentRemote.addAttachment(idtransaccion,tipo,fileName);
                
                //regresar que la operacion fue exitosa y regresando el nombre del archivo guardado
                return new OperationRestResult(true,fileName);
                
            } catch (Exception e) {
                e.printStackTrace();
                return new OperationRestResult(false,"error al intentar guardar archivo "+e.getMessage());
            }
        }
         
        return new OperationRestResult(false, "no se encontro archivo a guardar");
        
	}
	
    private String getFileName(int idtransaccion,String tipo,InputPart inputPart) {
    	
    	Pattern p = Pattern.compile("filename=\".*\"");
    	
    	//Use this header for extra processing if required
        MultivaluedMap<String, String> header = inputPart.getHeaders();
       
        if( !header.containsKey("Content-Disposition") )
        	return null;
        
        List<String> list = header.get("Content-Disposition");
        
    	for(String aux : list ) {
    		Matcher m = p.matcher(aux);
    		
    		if(m.find()) {
    			aux = m.group();
    			aux = aux.substring(aux.indexOf("\"")+1);
    			aux = aux.substring(0,aux.indexOf("\""));
    			aux = "tr-"+idtransaccion+"-"+tipo+aux.substring(aux.lastIndexOf("."));
    			return aux;
    		}
    	}
        
    	return null;
    }
}
