package com.example.aurora.myweb3j.util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Numeric;

/**
 * Created by yuan.
 */

public class Alice {
    public static String PRIVATE_KEY = "0x8a1d50c7a4b832bac9a18adce65974ffcb3e5f32207ec989ecc791cf65ab0391";
    public static String PUBLIC_KEY =  "0x47dda9dffb7cfbe218bcd62f0a2b7dc34f0651442ddacd386b4a0a435b04e416b13cd2beb626b81d748bf9e99244e30161e1e2644733abd4323d95ea4419375b";
//    static ECKeyPair KEY_PAIR = new ECKeyPair(Numeric.toBigInt(PRIVATE_KEY), Numeric.toBigInt(PUBLIC_KEY));
//
//    public static Credentials CREDENTIALS = Credentials.create(KEY_PAIR);
//    public static String ADDRESS = CREDENTIALS.getAddress();//0x775937da41955c32048af49b292714347034e53a

    //hello.then(function(instance) {return instance.getBalance("0x775937da41955c32048af49b292714347034e53a");}).then(function(balance) {console.log(balance);});
}
