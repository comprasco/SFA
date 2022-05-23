package co.gov.supernotariado.bachue.dispositivos.biometrico.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.biometrico.ejb.IHuellaBusiness;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dao.IHuellaDAO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.helper.HuellaHelper;
import co.gov.supernotariado.bachue.dispositivos.common.Enrolador;
import co.gov.supernotariado.bachue.dispositivos.common.Verificador;
import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.common.util.MotorBiometrico;
import co.gov.supernotariado.bachue.dispositivos.common.util.Utils;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.helper.LogHelper;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Constante;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ISesionDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper.SesionHelper;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Sesion;
import weblogic.logging.LoggingHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Implementación de lógica de negocio de operaciones biometricas.
 */
@Stateless(name="HuellaBusiness")
@Local
public class HuellaBusiness implements IHuellaBusiness {
   /**
    * Logger de weblogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   
   /**
    * DAO de huellas
    */
   @EJB
   private IHuellaDAO iihd_huellaDao;

   /**
    * DAO de log
    */
   @EJB
   private ILogDAO iild_logDao;

   /**
    * DAO de sesión
    */
   @EJB
   private ISesionDAO iisd_sesionDao;

   /**
    * DAO de usuarios
    */
   @EJB
   private IUsuarioDAO iiud_usuarioDao;

   /**
    * DAO de constantes
    */
   @EJB
   private IConstanteDAO iicd_constanteDao;

   /**
    * límite de aceptación de coincidencia de comparación de huellas
    */
   private String is_matchingThreshold = "40";

   @Override
   public Boolean enrolarHuella(HuellaDTO ahd_huella) {
      if(!Utils.crearImagen(ahd_huella)) {
         return false;
      }
      
      return iihd_huellaDao.crearHuella(HuellaHelper.toEntity(ahd_huella, iiud_usuarioDao.consultarUsuario(ahd_huella.getIdUsuario())));
   }

   @Override
   public BooleanSalidaDTO verificarHuella(VerificacionDTO avd_verificacion) {
      ll_logger.info("Verificación de huella");
      BooleanSalidaDTO lbsd_salida = new BooleanSalidaDTO();
      lbsd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lbsd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
      try {
         ll_logger.info("Obtener constantes");
         leerConstantes();
         Verificador lv_verificador = new Verificador();
         ll_logger.info("Llamar al verificador");
         boolean lb_resultado = lv_verificador.verificar(avd_verificacion, iihd_huellaDao.obtenerHuellas(avd_verificacion.getIdUsuario()));
         ll_logger.info("Verificación terminada");
         boolean lb_transaccionOk= true;
         
         //26-01-2020 se agrega funcionalidad de actualizar la session
         ll_logger.info("Consultar sesión");
         Sesion ls_sesion= iisd_sesionDao.consultarSesion(avd_verificacion.getSesion()); 
         if(ls_sesion!=null){
            ll_logger.info("Actualizar sesión");
            lb_transaccionOk= iisd_sesionDao.actualizarSesion(SesionHelper.createSesion(avd_verificacion, lb_resultado));
         }else{
            ll_logger.info("Insertar nueva sesión");
            lb_transaccionOk= iisd_sesionDao.crearSesion(SesionHelper.createSesion(avd_verificacion, lb_resultado)); 
         }
         
         if (!lb_transaccionOk){
            ll_logger.info("Error registrando la sesión");
            throw new Exception();
         }
      
         ll_logger.info("Registrar el evento verificación de huella");
         iild_logDao.crearEvento(LogHelper.crearLogDeVerificacion(avd_verificacion, lb_resultado));
         lbsd_salida.setResultado(true);
         ll_logger.info("FIn del proceso de verificación");
         return lbsd_salida;
      } catch (Throwable lt_exception) {
         lbsd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lbsd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         lbsd_salida.setResultado(false);
         ll_logger.severe("Error en la verificación de huella (business): " + lt_exception.getMessage());
         for(StackTraceElement lest_causa : lt_exception.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
         return lbsd_salida;
      }
   }

   @Override
   public StringSalidaDTO borrarHuellas(BorrarHuellasDTO abhd_borrarHuellas) {
      StringSalidaDTO lssd_salida = new StringSalidaDTO();
      lssd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lssd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      try {
         leerConstantes();
         Enrolador le_enrolador = new Enrolador(null);
         le_enrolador.eliminarHuellas(Criptografia.encrypt(abhd_borrarHuellas.getIdUsuario()));
         iihd_huellaDao.borrarHuellas(Criptografia.encrypt(abhd_borrarHuellas.getIdUsuario()));
         iild_logDao.crearEvento(LogHelper.crearLogDeBorrado(abhd_borrarHuellas));
         lssd_salida.setResultado(String.valueOf(true));

         return lssd_salida;
      } catch (Exception le_exception) {
         lssd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lssd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         lssd_salida.setResultado(String.valueOf(false));

         return lssd_salida;
      }
   }

   @Override
   public BooleanSalidaDTO crearMegaTemplate(HuellaDTO ahd_huella) {
      BooleanSalidaDTO lbsd_salida = new BooleanSalidaDTO();
      lbsd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lbsd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      try {
         leerConstantes();
         Enrolador le_enrolador = new Enrolador(ahd_huella);
         boolean lb_enrolamiento = le_enrolador.enrolarUsuario(iihd_huellaDao.obtenerHuellas(ahd_huella.getIdUsuario()));
         iild_logDao.crearEvento(LogHelper.crearLogDeEnrolamiento(ahd_huella, lb_enrolamiento));
         lbsd_salida.setResultado(true);

         return lbsd_salida;
      } catch (Exception le_exception) {
         lbsd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lbsd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         lbsd_salida.setResultado(false);

         return lbsd_salida;
      }
   }

   /**
    * Leer constantes de biometría
    */
   private void leerConstantes() {
      List<Constante> llc_constantes = iicd_constanteDao.consultarConstantes();
      llc_constantes.forEach(e -> {
         if(e.getIdConstante().equals("MATCHING_THRESHOLD_BIOMETRIA")) {
            is_matchingThreshold = e.getCaracter();
         }
      });
      MotorBiometrico.getInstance();
      MotorBiometrico.getInstance().getCliente().setMatchingThreshold(Integer.parseInt(is_matchingThreshold));
   }
}
