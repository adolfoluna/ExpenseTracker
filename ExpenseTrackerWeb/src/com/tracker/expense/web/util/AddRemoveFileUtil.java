package com.tracker.expense.web.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddRemoveFileUtil {
	
	private static final Log log = LogFactory.getLog(AddRemoveFileUtil.class);
	
	private static String getFilesPath(String contextPath) {

		//sacar el url de la aplicacion
		String propertyName = contextPath + "-DocRoot";
		
		//eliminar diagonal si es que empieza con diagonal
		if( propertyName.startsWith("/") )
			propertyName = propertyName.substring(1);
		
		//si la propiedad no existe marcar error y salir
		if( System.getProperty(propertyName) == null ) {
			log.error( "error no se encontro propiedad del sistema "+propertyName);
			return null;
		}
		
		//regresar el valor de la propiedad del sistema
		return System.getProperty(propertyName);
		
	}

	public static boolean removeFile(String contextPath,String fileName) {
		
		//determinar laa ruta en donde se encuentran todos los archivos de las transacciones
		contextPath = getFilesPath(contextPath);
		
		//en caso de no haber podido determinar la ruta de los archivos salir
		if( contextPath == null ) return false;
		
		//nombre de archivo seria ruta mas el nombre de archivo
		fileName = contextPath + "/" + fileName;
		
		//crear el objeto archivo
		File file = new File(fileName);
		
		//verificar que el archivo exista
		if( !file.exists() ) {
			log.error("error archivo "+fileName+" no existe");
			return false;
		}
		
		//si se pudo borrar el archivo indicarlo y salir 
		if( file.delete() ) {
			log.info("archivo "+fileName+" exitosamente borrado");
			return true;
		}
		
		//indicar error que no se pudo eliminar archivo y regresar falso
		log.error("error archivo "+fileName+" no se pudo eliminar");
		return false;
	}
	
	public static boolean addFile(String contextPath,String fileName, byte []data ) {
		
		//determinar laa ruta en donde se encuentran todos los archivos de las transacciones
		contextPath = getFilesPath(contextPath);
				
		//en caso de no haber podido determinar la ruta de los archivos salir
		if( contextPath == null ) 
			return false;
		
		//nombre de archivo seria ruta mas el nombre de archivo
		fileName = contextPath + "/" + fileName;
		
		try {
			
			log.info("intentando guardar archivo "+fileName+".................");
			
			//crear objeto archivo
			File file = new File(fileName);
	        
			//si no existe el archivo crearlo
	        if (!file.exists()) 
	            file.createNewFile();
	        
	        //intentar crear un stream de datos
	        FileOutputStream fop = new FileOutputStream(file);
	        
	        //escribir los datos al archivo
	        fop.write(data);
	        fop.flush();
	        
	        //cerrar archivo
	        fop.close();
	        
	        //indicar que si se pudo grabar el archivo
	        log.info("archivo "+fileName+" exitosamente guardado");
	        
	        return true;
	        
		}catch(Exception ex) {
			ex.printStackTrace();
			log.error("error al intentar guardar archivo "+fileName+" "+ex.getMessage());
			return false;
		}
			
	}
}
