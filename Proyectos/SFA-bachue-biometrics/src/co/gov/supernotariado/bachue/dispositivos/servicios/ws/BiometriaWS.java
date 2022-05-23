package co.gov.supernotariado.bachue.dispositivos.servicios.ws;

import co.gov.supernotariado.bachue.dispositivos.biometrico.ejb.IHuellaBusiness;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.DuracionLogger;
import co.gov.supernotariado.bachue.dispositivos.ejb.*;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.ejb.IFirmaBusiness;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.FirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.ObtenerFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.consultarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.*;
import co.gov.supernotariado.bachue.dispositivos.usuario.ejb.ISesionBusiness;
import co.gov.supernotariado.bachue.dispositivos.usuario.ejb.IUsuarioBusiness;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.SesionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.SesionEntradaDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioEntradaDTO;
import weblogic.logging.LoggerNotAvailableException;
import weblogic.logging.LoggingHelper;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 * Servicio SOAP con endpoints para el proyecto de biometria.
 */
@Stateless
@WebService(serviceName = "BiometriaController",
            targetNamespace = "http://ws.servicios.corebachue.bachue.supernotariado.gov.co/")
public class BiometriaWS {
   /**
    * Lógica de negocio de sesiones
    */
   @EJB
   ISesionBusiness iisb_sesionBusiness;

   /**
    * Lógica de negocio de biometría de huellas
    */
   @EJB
   IHuellaBusiness iihb_huellaBusiness;

   /**
    * Lógica de negocio de registro de auditoría
    */
   @EJB
   ILogBusiness iilb_logBusiness;

   /**
    * Lógica de negocio de gesión de usuarios
    */
   @EJB
   IUsuarioBusiness iiub_usuarioBusiness;

   /**
    * Lógica de negocio de consulta de constantes
    */
   @EJB
   IConstanteBusiness iicb_constanteBusiness;

   /**
    * Contexto de mensajes e información de seguridad relativa a la petición que se atiende
    */
   @Resource
   private WebServiceContext iwsc_context;

   /**
    * Lógica de negocio de gestión de firmas
    */
   @EJB
   IFirmaBusiness iifb_firmaBusiness;

   /**
    * Logger para servidor WebLogic.
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   
  /**
   * Método que recibe la petición de verificación de sesión.
   * @param ased_sesion sesion que sera validada.
   * @return DTO con la sesion y su estado.
   */
   @WebMethod(action = "consultarSesion")
   @WebResult(name = "salidaSesion")
   public SesionDTO consultarSesion(@WebParam(name = "entradaSesion") SesionEntradaDTO ased_sesion) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio consultar sesión");
      SesionDTO lr_resultado = iisb_sesionBusiness.consultarSesion(ased_sesion.getSesion());
      ld_duracionLogger.Marca("Inicio consultar sesión");
      return lr_resultado;
   }


  /**
   * Método que recibe la petición de estadisticas.
   * @param aeed_estadisticas DTO de entrada.
   * @return DTO con los datos solicitados.
   */
   @WebMethod(action = "consultarEstadisticas")
   @WebResult(name = "salidaEstadisticas")
   public EstadisticasSalidaDTO consultarEstadisticas(@WebParam(name = "entradaEstadisticas" ) EstadisticasEntradaDTO aeed_estadisticas) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio consultar estadísticas");
      EstadisticasSalidaDTO lr_resultado = iilb_logBusiness.consultarStats(aeed_estadisticas.getTipo(), aeed_estadisticas.getIdEntidad());;
      ld_duracionLogger.Marca("Fin consultar estadísticas");
      return lr_resultado;
  }


  /**
   * Método que enrola las huellas de un usuario previamente creado.
   * @param ahd_huellas DTO con la información de las huellas.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "enrolarUsuario")
   @WebResult(name = "salidaResultado")
   public BooleanSalidaDTO enrolarUsuario(@WebParam(name = "entradaHuella") HuellaDTO[] ahd_huellas) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio enrolamiento");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);

      BooleanSalidaDTO lbsd_estado;
      for(HuellaDTO lhd_huella : ahd_huellas) {
         lhd_huella.agregarValoresAuditoria(lhsr_req);
         iihb_huellaBusiness.enrolarHuella(lhd_huella);
      }

      lbsd_estado = iihb_huellaBusiness.crearMegaTemplate(ahd_huellas[0]);
      ld_duracionLogger.Marca("Fin enrolamiento");
      return lbsd_estado;
   }

  /**
   * Método que crea un usuario en la base de datos.
   * @param aud_usuario DTO con la información del usuario.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "crearUsuario")
   @WebResult(name = "salidaResultado")
   public StringSalidaDTO crearUsuario(@WebParam(name = "entradaUsuario") UsuarioDTO aud_usuario) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio crear usuario " + aud_usuario.getIdUsuario());
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      aud_usuario.agregarValoresAuditoria(lhsr_req);
      StringSalidaDTO lr_resultado = iiub_usuarioBusiness.crearUsuario(aud_usuario);
      ld_duracionLogger.Marca("Fin crear usuario");
      return lr_resultado;
   }

  /**
   * Método que actualiza la clave en la base de datos.
   * @param aud_usuario DTO con la información del usuario.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "actualizarClave")
   @WebResult(name = "salidaResultado")
   public StringSalidaDTO actualizarClave(@WebParam(name = "entradaClave") UsuarioDTO aud_usuario) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio actualizar clave");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest ahsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      aud_usuario.agregarValoresAuditoria(ahsr_req);
      StringSalidaDTO lr_resultado = iiub_usuarioBusiness.actualizarClave(aud_usuario);
      ld_duracionLogger.Marca("Fin actualizar clave");
      return lr_resultado;
   }

  /**
   * Método que obtiene un usuario de la base de datos.
   * @param aued_usuario id del usuario.
   * @return el estado del usuario.
   */
   @WebMethod(action = "obtenerUsuario")
   @WebResult(name = "salidaResultado")
   public StringSalidaDTO obtenerUsuario(@WebParam(name = "entradaUsuario") UsuarioEntradaDTO aued_usuario) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio obtener usuario");
      StringSalidaDTO lr_resultado = iiub_usuarioBusiness.obtenerUsuario(aued_usuario.getIdUsuario());
      ld_duracionLogger.Marca("Fin obtener usuario");
      return lr_resultado;
   }

  /**
   * Método que obtiene el tipo de segundo factor de un usuario.
   * @param aued_usuario id del usuario.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "obtenerTipoSegundoFactor")
   @WebResult(name = "salidaResultado")
   public StringSalidaDTO obtenerTipoSegundoFactor(@WebParam(name = "entradaUsuario") UsuarioEntradaDTO aued_usuario) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio obtener segundo factor de usuario");
      StringSalidaDTO lr_resultado = iiub_usuarioBusiness.obtenerTipoSegundoFactor(aued_usuario.getIdUsuario());
      ld_duracionLogger.Marca("Fin obtener segundo factor de usuario");
      return lr_resultado;
   }

   /**
   * Método obtiene las constantes de la base de datos.
   * @return el resultado de la operación.
   * @throws LoggerNotAvailableException 
   */
   @WebMethod(action = "obtenerConstantes")
   @WebResult(name = "salidaResultado")
   public ConstantesSalidaDTO obtenerConstantes() throws LoggerNotAvailableException {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio obtener constantes");
      ConstantesSalidaDTO lr_resultado = iicb_constanteBusiness.consultarConstantes();
      ld_duracionLogger.Marca("Fin obtener constantes");
      return lr_resultado ;
   }

  /**
   * Método que borra la información biometrica del usuario.
   * @param bhd_usuario DTO con la información del usuario.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "borrarHuellas")
   @WebResult(name = "salidaResultado")
   public StringSalidaDTO borrarHuellas(@WebParam(name = "entradaUsuario") BorrarHuellasDTO bhd_usuario) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio borrar huellas");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      bhd_usuario.agregarValoresAuditoria(lhsr_req);
      StringSalidaDTO lr_resultado = iihb_huellaBusiness.borrarHuellas(bhd_usuario);
      ld_duracionLogger.Marca("Fin borrar huellas");
      return lr_resultado;
   }

  /**
   * Método que verifica un usuario con biometria.
   * @param avd_verificacion DTO con la información de la huella.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "verificarUsuario")
   @WebResult(name = "salidaResultado")
   public BooleanSalidaDTO verificarUsuario(@WebParam(name = "entradaVerificacion") VerificacionDTO avd_verificacion) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio verificación de huellas");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      avd_verificacion.agregarValoresAuditoria(lhsr_req);
      BooleanSalidaDTO rb_resultado = iihb_huellaBusiness.verificarHuella(avd_verificacion); 
      ld_duracionLogger.Marca("Fin verificación de huellas");
      return rb_resultado;
   }

   /**
   * Método que verifica un usuario con segunda clave.
   * @param acd_clave DTO con la información de la clave.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "verificarClave")
   @WebResult(name = "salidaResultado")
   public BooleanSalidaDTO verificarClave(@WebParam(name = "entradaClave") ClaveDTO acd_clave) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio verificar clave");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      acd_clave.agregarValoresAuditoria(lhsr_req);
      BooleanSalidaDTO lr_resultado = iiub_usuarioBusiness.verificarUsuario(acd_clave);
      ld_duracionLogger.Marca("Fin verificar clave");
      return lr_resultado;
   }

  /**
   * Método que registra un evento en la base de datos.
   * @param ald_log DTO con la información del evento a loguear.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "registrarEvento")
   @WebResult(name = "salidaResultado")
   public BooleanSalidaDTO registrarEvento(@WebParam(name = "entradaLog") LogDTO ald_log) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio registro de evento");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      ald_log.agregarValoresAuditoria(lhsr_req);
      BooleanSalidaDTO lr_resultado = iilb_logBusiness.registrarEvento(ald_log);
      ld_duracionLogger.Marca("Fin registro de evento");
      return lr_resultado;
   }
  
  /**
   * Método que guarda la firma de un usuario.
   * @param aafd_agregarfirma DTO con la información de la firma.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "agregarFirma")
   @WebResult(name = "agregarFirmaResponse")
   public AgregarFirmaSalidaDTO agregarFirma(@WebParam(name = "entradaAgregarFirma") AgregarFirmaDTO aafd_agregarfirma) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio agregar firma");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      aafd_agregarfirma.agregarValoresAuditoria(lhsr_req);
      AgregarFirmaSalidaDTO lr_resultado = iifb_firmaBusiness.crearFirma(aafd_agregarfirma);
      ld_duracionLogger.Marca("Fin agregar firma");
      return lr_resultado;
   }

  /**
   * Método que consulta la firma de un usuario.
   * @param aofd_firma DTO con la información de la firma.
   * @return el resultado de la operación.
   */
   @WebMethod(action = "obtenerFirma")
   @WebResult(name = "obtenerFirmaResponse")
   public ObtenerFirmaSalidaDTO obtenerFirma(@WebParam(name = "entradaObtenerFirma")consultarFirmaDTO aofd_firma) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio obtener firma");
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      aofd_firma.agregarValoresAuditoria(lhsr_req);
      ObtenerFirmaSalidaDTO lr_resultado = iifb_firmaBusiness.consultarFirma(aofd_firma);
      ld_duracionLogger.Marca("Fin obtener firma");
      return lr_resultado;
   }

   /**
    * Método que elimina la firma de un usuario.
    * @param aofd_firma DTO con la información de la firma.
    * @return el resultado de la operación.
    */
   @WebMethod(action = "eliminarFirma")
   @WebResult(name = "eliminarFirmaResponse")
   public FirmaSalidaDTO eliminarFirma(@WebParam(name = "entradaEliminarFirma")consultarFirmaDTO aofd_firma) {
      DuracionLogger ld_duracionLogger = new DuracionLogger("Inicio desechar firma");
      FirmaSalidaDTO lfsd_firma_salida = new FirmaSalidaDTO();
      MessageContext lmc_messageContext = iwsc_context.getMessageContext();
      HttpServletRequest lhsr_req = (HttpServletRequest)lmc_messageContext.get(MessageContext.SERVLET_REQUEST);
      aofd_firma.agregarValoresAuditoria(lhsr_req);
      
      if  (iifb_firmaBusiness.eliminarFirma(aofd_firma)){  
         lfsd_firma_salida.setResultado(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
         lfsd_firma_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
         lfsd_firma_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
      }else{
         lfsd_firma_salida.setCodigo(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lfsd_firma_salida.setResultado(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lfsd_firma_salida.setMensaje(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarMensaje());
      }
      ld_duracionLogger.Marca("Fin desechar firma");
      return lfsd_firma_salida;
   }
}
