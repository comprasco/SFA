package co.gov.supernotariado.bachue.dispositivos.padfirmas.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.ejb.IFirmaBusiness;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dao.IFirmaDAO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.ObtenerFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.consultarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.helper.FirmaHelper;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.model.Firma;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.helper.LogHelper;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ISesionDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioDAO;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementación de las operaciones de gestión de firma
 */
@Stateless(name="FirmaBusiness")
@Local
public class FirmaBusiness implements IFirmaBusiness {
   
   /**
    * DAO de firmas
    */
   @EJB
   private IFirmaDAO iihd_FirmasDAO;
   
   /**
    * DAO de sesión
    */
   @EJB
   private ISesionDAO iisd_sesionDao;
   
   /**
    * Dao del registro de auditoría de negocios
    */
   @EJB
   private ILogDAO iild_logDao;
   
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
   
   @Override
   public AgregarFirmaSalidaDTO crearFirma(AgregarFirmaDTO aafd_firma) {
      AgregarFirmaSalidaDTO  lafd_firmaSalida = new AgregarFirmaSalidaDTO();
      consultarFirmaDTO lofd_obtenerFirma= new consultarFirmaDTO();
      try{
         //idFirma = idtramite + numerodocumento
         lofd_obtenerFirma.setIdfirma(aafd_firma.getIdTramite()+aafd_firma.getNumeroDocCiudadano());
         lofd_obtenerFirma.setIdUsuario(aafd_firma.getIdUsuario());
         lofd_obtenerFirma.setIp(aafd_firma.getIp());
         lofd_obtenerFirma.setTime(aafd_firma.getTime());
			
         if(consultarFirma(lofd_obtenerFirma).getResultado().equals(SalidasEnum.RECURSO_EXITOSO.consultarCodigo()))
         {
            eliminarFirma(lofd_obtenerFirma); 
         }
         
         if (iihd_FirmasDAO.crearFirma(FirmaHelper.toEntity(aafd_firma)))
         {
            lafd_firmaSalida.setIdFirma(aafd_firma.getIdTramite()+aafd_firma.getNumeroDocCiudadano());
            lafd_firmaSalida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
            lafd_firmaSalida.setResultado(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
            lafd_firmaSalida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
         }else{
            lafd_firmaSalida.setCodigo(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
            lafd_firmaSalida.setResultado(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
            lafd_firmaSalida.setMensaje(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarMensaje());
            lafd_firmaSalida.setIdFirma("");
         }
         return lafd_firmaSalida;
         
      }catch(Exception le_exception){
         lafd_firmaSalida.setCodigo(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lafd_firmaSalida.setResultado(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lafd_firmaSalida.setMensaje(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarMensaje());
         return lafd_firmaSalida;
      }
   }
	
   @Override
   public ObtenerFirmaSalidaDTO consultarFirma(consultarFirmaDTO aofd_obtenerFirma)
   {
      ObtenerFirmaSalidaDTO lofsd_firmaSalida = new ObtenerFirmaSalidaDTO();
      try{
         List<Firma> llf_firma=iihd_FirmasDAO.listarFirma(aofd_obtenerFirma.getIdfirma());
         lofsd_firmaSalida.setIdFirma(llf_firma.iterator().next().getIdFirma()); 
         if(llf_firma.iterator().next().getImage()!=null){
            lofsd_firmaSalida.setFirma(new String(Base64.encodeBase64(llf_firma.iterator().next().getImage())));	
            lofsd_firmaSalida.setResultado(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
            lofsd_firmaSalida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
            lofsd_firmaSalida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
         }else{
            lofsd_firmaSalida.setFirma("");
            lofsd_firmaSalida.setResultado(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarCodigo());
            lofsd_firmaSalida.setCodigo(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarCodigo());
            lofsd_firmaSalida.setMensaje(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarMensaje());
         }
      }catch (NoSuchElementException lnsee_exception){
         lofsd_firmaSalida.setCodigo(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarCodigo());
         lofsd_firmaSalida.setResultado(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarCodigo());
         lofsd_firmaSalida.setMensaje(SalidasEnum.FIRMA_NO_ENCONTRADO.consultarMensaje());
         lofsd_firmaSalida.setFirma("");
      }catch (Exception le_exception){
         lofsd_firmaSalida.setCodigo(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lofsd_firmaSalida.setResultado(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lofsd_firmaSalida.setMensaje(SalidasEnum.FIRMA_EXCEPCION_NO_CONTROLADA.consultarMensaje());
         lofsd_firmaSalida.setFirma("");
      }
      return lofsd_firmaSalida;
   }
	
   @Override
   public Boolean eliminarFirma (consultarFirmaDTO aofd_firma) {
      boolean lb_resultado=true;
      try {
         lb_resultado=iihd_FirmasDAO.eliminarFirma(aofd_firma.getIdfirma());
         iild_logDao.crearEvento(LogHelper.crearLogDeBorradoFirma(aofd_firma,lb_resultado));
      } catch (Exception le_exception) {
         lb_resultado=false;
      }
      return lb_resultado;
   }
   
}
