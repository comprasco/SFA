package co.gov.supernotariado.bachue.dispositivos.common.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Clase se implementación de filtros
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

   /**
    * Método que implementa el filtro que se aplica a la respuesta de las peticiones
    * @param acrc_request petición de entrada
    * @param arc_response respuesta de la petición
    * @throws IOException
    */
   @Override
   public void filter(ContainerRequestContext acrc_request,
                     ContainerResponseContext arc_response) throws IOException {
      arc_response.getHeaders().add("Access-Control-Allow-Origin", "*");
      arc_response.getHeaders().add("Access-Control-Allow-Headers",
            "origin, content-type, accept, authorization");
      arc_response.getHeaders().add("Access-Control-Allow-Credentials", "true");
      arc_response.getHeaders().add("Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS, HEAD");
   }
}