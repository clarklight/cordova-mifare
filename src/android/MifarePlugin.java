
package se.frostyelk.cordova.mifare;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import com.nxp.nfclib.classic.MFClassic;
import com.nxp.nfclib.exceptions.SmartCardException;
import com.nxp.nfclib.icode.*;
import com.nxp.nfclib.ntag.*;
import com.nxp.nfclib.plus.PlusSL1;
import com.nxp.nfclib.ultralight.Ultralight;
import com.nxp.nfclib.ultralight.UltralightC;
import com.nxp.nfclib.ultralight.UltralightEV1;
import com.nxp.nfclib.utils.NxpLogUtils;
import com.nxp.nfclib.utils.Utilities;
import com.nxp.nfcliblite.Interface.NxpNfcLibLite;
import com.nxp.nfcliblite.Interface.Nxpnfcliblitecallback;
import com.nxp.nfcliblite.cards.DESFire;
import com.nxp.nfcliblite.cards.Plus;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This class represents the native implementation for the MIFARE Cordova plugin.
 */
public class MifarePlugin extends CordovaPlugin {

    private static final String LOGTAG = "MifarePlugin";
    private static final String ACTION_INIT = "init";
    private static final String ACTION_WRITE_TAG_DATA = "writeTag";
    private static final String TAG_EVENT_DETECTED = "onTagDetected";
    private static final String TAG_EVENT_ERROR = "onTagError";
    private static final String TAG_EVENT_ERROR_TYPE_SECURITY = "Security";
    private static final String TAG_EVENT_ERROR_TYPE_IOREAD = "IORead";
    private static final String TAG_EVENT_ERROR_TYPE_CARD = "Card";
    private static final String TAG_EVENT_ERROR_TYPE_UNSUPPORTED = "Unsupported";
    private static final int UNIVERSAL_NUMBER = 42;
    private static final int MAX_FAST_READ_PAGES = 50;
    private static String TAG = "MifarePLugin";

    private String password;
    private byte[] payload;
    private NTag nTag;
    private Tag tagInfo;
    private Intent initializeIntent;

    // It seems that password errors returns as IOException instead of SmartCardException?!
    private boolean checkForPasswordSentAtIOError = false;

       public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
            
            
  	showMessage("Card Detected : " + mDESFire.getCardDetails().cardName,
				'n');
        
            
            
            
              if (ACTION_INIT.equals(action)) {
            result = init(args.getJSONObject(0), callbackContext);
        } else if (ACTION_WRITE_TAG_DATA.equals(action)) {
            result = writeTag(args.getJSONObject(0), callbackContext);
        } else if (ACTION_AUTHENTICATE.equals(action)) {

     result = authenticate(args.getJSONObject(0), callbackContext);
            
 }else if (ACTION_ENCRYPT.equals(action)) {

     result = encrypt(args.getJSONObject(0), callbackContext);
            
              
              }
          return true;
            
    }


  private void init(CallbackContext callbackContext) {
        Log.d(TAG, "Enabling plugin " + getIntent());

        startNfc();
        if (!recycledIntent()) {
            parseMessage();
        }
        callbackContext.success();
    }



    private void registerDefaultTag(CallbackContext callbackContext) {
        
      
        
      addTagFilter();
      callbackContext.success();
  }

}
