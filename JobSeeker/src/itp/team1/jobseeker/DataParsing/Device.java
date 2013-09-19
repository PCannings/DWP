package itp.team1.jobseeker.DataParsing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

public class Device 
{
	static public String getUniqueID(Context context)
	{
		WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifi.getConnectionInfo();
		return md5(wifiInfo.getMacAddress());
	}
	
	static public String getDescription()
	{
		String description = null;
		description = Build.BOARD + ", ";
		description += Build.BRAND + ", ";
		description += Build.CPU_ABI + ", ";
		description += Build.DEVICE + ", ";
		description += Build.DISPLAY + ", ";
		description += Build.FINGERPRINT + ", ";
		description += Build.ID + ", ";
		description += Build.MANUFACTURER + ", ";
		description += Build.MODEL + ", ";
		description += Build.PRODUCT + ", ";
		description += Build.TAGS + ", ";
		description += Build.TYPE + ", ";
		return description;
	}
		
	//**************ENCRYPTION METHODS**************
	public static String hex(byte[] array) {
		  StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < array.length; ++i) {
		    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1,3));
		  }
		  return sb.toString();
		}
	public static String md5(String message) { 
		  try { 
		    MessageDigest md = MessageDigest.getInstance("MD5"); 
		    return hex (md.digest(message.getBytes("CP1252"))); 
		  } catch (NoSuchAlgorithmException e) { } catch (UnsupportedEncodingException e) { } 
		  return null;
		}
	//**************ENCRYPTION METHODS**************
}
