package co.gov.supernotariado.bachue.dispositivos.usuario.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dao.IHuellaDAO;
import co.gov.supernotariado.bachue.dispositivos.common.enums.RespuestaUsuarioEnum;
import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.StringSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.helper.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Constante;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Historico;
import co.gov.supernotariado.bachue.dispositivos.usuario.ejb.IUsuarioBusiness;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ISesionDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioBachueDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper.SesionHelper;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper.UsuarioHelper;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper.ValidadorHelper;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Sesion;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;
import weblogic.logging.LoggingHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Implementacion de logica de negocio de usuario.
 */
@Stateless(name="UsuarioBusiness")
@Local
public class UsuarioBusiness implements IUsuarioBusiness {
   /**
    * Logger de weblogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();

   /**
    * DAO DE Usuario
    */
  @EJB
  private IUsuarioDAO iiud_usuarioDao;


  /**
   * DAO de usuario de Bachué
   */
  @EJB
  private IUsuarioBachueDAO iiud_usuarioBachueDao;

  /**
   * DAO de históricos de huellas
   */
  @EJB
  private IHistoricoDAO iihd_historicoDao;

  /**
   * DAO de huellas
   */
  @EJB
  private IHuellaDAO iihd_huellaDao;

  /**
   * DAO de sessión
   */
  @EJB
  private ISesionDAO iisd_sesionDao;

  /**
   * DAO de registro de auditoría de negocio 
   */
  @EJB
  private ILogDAO iild_logDao;

  /**
   * DAO de constantes
   */
  @EJB
  private IConstanteDAO iicd_constanteDao;

  /**
   * Patrón de contraseña
   */
  private String is_clavePatron = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";
  /**
   * Longitud mínima de la constraseña
   */
  private String is_claveMinimo = "8";
  /**
   * Longitud máxima de contraseña
   */
  private String is_claveMaximo = "32";

  @Override
  public StringSalidaDTO crearUsuario(UsuarioDTO aud_usuario) {
    StringSalidaDTO lssd_salida = new StringSalidaDTO();
    lssd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
    lssd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

    try {
      leerConstantes();
      String ls_resultado = ValidadorHelper.validarClave(aud_usuario.getClave(), is_clavePatron, is_claveMinimo, is_claveMaximo);
      if (ls_resultado.equals("Validado exitosamente")) {
        iiud_usuarioDao.crearUsuario(UsuarioHelper.toEntity(aud_usuario));
        lssd_salida.setResultado(String.valueOf(iihd_historicoDao.crearHistorico(HistoricoHelper.userToHistorico(aud_usuario))));
      } else {
        lssd_salida.setResultado(ls_resultado);
      }
      iild_logDao.crearEvento(LogHelper.crearLogDeCrearUsuario(aud_usuario, true));
      return lssd_salida;
    } catch (Exception le_exception) {
      lssd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
      lssd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
      iild_logDao.crearEvento(LogHelper.crearLogDeCrearUsuario(aud_usuario, false));
      return lssd_salida;
    }
  }

  @Override
  public StringSalidaDTO actualizarClave(UsuarioDTO aud_usuario) {
    StringSalidaDTO lssd_salida = new StringSalidaDTO();
    lssd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
    lssd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

    try {
      leerConstantes();
      String ls_resultado = ValidadorHelper.validarClave(aud_usuario.getClave(), is_clavePatron, is_claveMinimo, is_claveMaximo);
      if(ls_resultado.equals("Validado exitosamente")) {
        Usuario lu_usuarioActual = iiud_usuarioDao.consultarUsuario(Criptografia.encrypt(aud_usuario.getIdUsuario()));
        if(lu_usuarioActual.getClaveHash().equals(Criptografia.encrypt(aud_usuario.getClave()))) {
          lssd_salida.setResultado("La clave ingresada debe ser diferente a la actual");
          return lssd_salida;
        } else {
          List<Historico> llh_historico = iihd_historicoDao.consultarUltimasCincoClaves(Criptografia.encrypt(aud_usuario.getIdUsuario()));
          boolean lb_claveUsada = false;

          for(Historico claveActual : llh_historico) {
            if(Objects.equals(Criptografia.encrypt(aud_usuario.getClave()), claveActual.getClaveHash())) {
              lb_claveUsada = true;
            }
          }

          if(lb_claveUsada) {
            lssd_salida.setResultado("La clave ingresada debe ser diferente a las últimas cinco utilizadas");
            return lssd_salida;
          } else {
            iild_logDao.crearEvento(LogHelper.crearLogDeActualizacionDeClave(aud_usuario));
            iihd_historicoDao.crearHistorico(HistoricoHelper.userToHistorico(aud_usuario));

            lssd_salida.setResultado(String.valueOf(iiud_usuarioDao.actualizarClave(UsuarioHelper.usuarioConClave(aud_usuario))));
            return lssd_salida;
          }
        }
      } else {
        lssd_salida.setResultado(ls_resultado);
        return lssd_salida;
      }
    } catch (Exception le_exception) {
      lssd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
      lssd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());

      return lssd_salida;
    }
  }

  @Override
  public StringSalidaDTO obtenerUsuario(String as_id) {
    StringSalidaDTO lssd_salida = new StringSalidaDTO();
    lssd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
    lssd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

    try {
      boolean lb_usuarioExiste = iiud_usuarioDao.consultarUsuario(Criptografia.encrypt(as_id)) != null;
      if(lb_usuarioExiste) {
        int li_cuenta = iihd_huellaDao.contarHuellas(Criptografia.encrypt(as_id));
        if(li_cuenta > 0) {
          lssd_salida.setResultado(RespuestaUsuarioEnum.USUARIO_EXISTE.toString());
          return lssd_salida;
        } else {
          lssd_salida.setCodigo(SalidasEnum.RECURSO_NO_VALIDO.consultarCodigo());
          lssd_salida.setMensaje(SalidasEnum.RECURSO_NO_VALIDO.consultarMensaje());
          lssd_salida.setResultado(RespuestaUsuarioEnum.USUARIO_NO_TIENE_HUELLAS.toString());
          return lssd_salida;
        }
      } else {
        lssd_salida.setCodigo(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarCodigo());
        lssd_salida.setMensaje(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarMensaje());
        lssd_salida.setResultado(RespuestaUsuarioEnum.USUARIO_NO_EXISTE.toString());
        return lssd_salida;
      }
    } catch (Exception le_exception) {
      lssd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
      lssd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());

      return lssd_salida;
    }
  }

   @Override
   public BooleanSalidaDTO verificarUsuario(ClaveDTO acd_clave) {
      BooleanSalidaDTO lbsd_salida = new BooleanSalidaDTO();
      lbsd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lbsd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      try {
         ll_logger.info("Consultar la contraseña actual del usuario " + acd_clave.getIdUsuario());
         Usuario lu_usuario = iiud_usuarioDao.consultarUsuario(Criptografia.encrypt(acd_clave.getIdUsuario()));
         
         if (lu_usuario == null) {
            throw new NotFoundException(acd_clave.getIdUsuario() + " no ha sido creado");
         }
         ll_logger.info("Contraseña consultada del usuario " + acd_clave.getIdUsuario());
         
         boolean lb_resultado = true;
         
         ll_logger.info("Verificar vencimiento de la contraseña del usuario " + acd_clave.getIdUsuario());
         if (lu_usuario.getFechaVencimiento().before(new Date()))
         {
            ll_logger.info("Contraseña vencida del usuario " + acd_clave.getIdUsuario());
            lbsd_salida.setCodigo("403");
            lbsd_salida.setMensaje("La contraseña del usuario está vencida");
            lbsd_salida.setResultado(false);
            lb_resultado = false;
         }
         
         lb_resultado = (
              lu_usuario.getClaveHash().equals(Criptografia.encrypt(acd_clave.getClave())) &&
                      lu_usuario.getFechaVencimiento().after(new Date()) &&
                      lu_usuario.getClaveActiva() == '1');
         
         ll_logger.info("Resultado de la verificación del usuario " + acd_clave.getIdUsuario() + " es: " + lb_resultado);
         
         //23-02-2020 se agrega funcionalidad de actualizar la session
         boolean lb_transaccionOk= true;
         Sesion ls_sesion= iisd_sesionDao.consultarSesion(acd_clave.getSesion()); 
         if(ls_sesion!=null){
    	  lb_transaccionOk= iisd_sesionDao.actualizarSesion(SesionHelper.crearSesionConClave(acd_clave, lb_resultado));
         }else{
    	  lb_transaccionOk= iisd_sesionDao.crearSesion(SesionHelper.crearSesionConClave(acd_clave, lb_resultado));
         }
         if (!lb_transaccionOk){
    	  throw new Exception();
         }
         iild_logDao.crearEvento(LogHelper.crearLogDeVerificacionConClave(acd_clave, lb_resultado));
         lbsd_salida.setResultado(lb_resultado);

         return lbsd_salida;
      } catch (Throwable le_exception) {
         ll_logger.severe("Error verificando usuario " + acd_clave.getIdUsuario());
         ll_logger.severe(acd_clave.getIdUsuario() +  " - Error: " + le_exception.getMessage());
         for(StackTraceElement stElement : le_exception.getStackTrace()) {
            ll_logger.severe(acd_clave.getIdUsuario() +  " StackTrace: " + stElement.getClassName() + " - " + stElement.getMethodName() + " - " + stElement.getLineNumber() + " : " + stElement.toString());
         }
         
         
         lbsd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lbsd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         lbsd_salida.setResultado(false);

         return lbsd_salida;
      }
   }

   @Override
   public StringSalidaDTO obtenerTipoSegundoFactor(String as_id) {
      StringSalidaDTO lssd_salida = new StringSalidaDTO();
      lssd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lssd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      try {
         lssd_salida.setResultado(iiud_usuarioBachueDao.obtenerSegundoFactor(as_id));
         return lssd_salida;
      } catch (Exception le_exception) {
         lssd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lssd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());

         return lssd_salida;
      }
   }

  /**
   * Método que lee las constantes de la complejidad de la segunda clave
   */
  private void leerConstantes() {
    List<Constante> llc_constantes = iicd_constanteDao.consultarConstantes();
    llc_constantes.forEach(e -> {
      switch (e.getIdConstante()) {
        case "CARACTERES_MINIMOS_CLAVE_SEGUNDO_FACTOR":
          is_claveMinimo = e.getCaracter();
          break;
        case "CARACTERES_MAXIMOS_CLAVE_SEGUNDO_FACTOR":
          is_claveMaximo = e.getCaracter();
          break;
        case "PATRON_CLAVE_SEGUNDO_FACTOR":
          is_clavePatron = e.getCaracter();
          break;
      }
    });
  }
}
