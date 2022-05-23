package co.gov.supernotariado.bachue.dispositivos.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Clase con funciones criptográficas
 */
public class Criptografia {

   /**
    * Clave privada
    */
   private static byte[] cb_keyArray = { 124, 114, 73, 16, 9, 117, 8, 74, 105, 72, 27, 111, 86, 107, 67, 120 };

   /**
    * Constructor de la clase
    */
   private Criptografia() {}

   /**
    * Cifra el una cadena
    * @param as_strToEncrypt cadena a cifrar
    * @return cadena cifrada en Base64
    */
   public static String encrypt(String as_strToEncrypt) {
      try {
         Cipher lc_cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
         IvParameterSpec li_ivSpec = new IvParameterSpec(cb_keyArray);
         Key lk_secretKey = new SecretKeySpec(cb_keyArray, "AES");

         lc_cipher.init(Cipher.ENCRYPT_MODE, lk_secretKey, li_ivSpec);

         return Base64.encodeBase64String(lc_cipher.doFinal(as_strToEncrypt.getBytes()));
      } catch (Exception le_exception) {
         System.err.println("[Exception]:"+le_exception.getMessage());
      }
      return null;
   }

   /**
    * Descifra una cadena en Base64
    * @param as_encryptedMessage cadena en base64 
    * @return cadena descifrada
    */
   public static String decrypt(String as_encryptedMessage) {
      try {
         Cipher lc_cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
         IvParameterSpec li_ivSpec = new IvParameterSpec(cb_keyArray);
         Key lk_secretKey = new SecretKeySpec(cb_keyArray, "AES");

         lc_cipher.init(Cipher.DECRYPT_MODE, lk_secretKey, li_ivSpec);
         byte[] decodedMessage = Base64.decodeBase64(as_encryptedMessage);
         return new String(lc_cipher.doFinal(decodedMessage));
      } catch (Exception le_exception) {
         System.err.println("[Exception]:" + le_exception.getMessage());
      }
      return null;
   }
}
