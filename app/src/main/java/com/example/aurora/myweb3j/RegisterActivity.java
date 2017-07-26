package com.example.aurora.myweb3j;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.aurora.myweb3j.contract.ManageOrder;
import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jConstants;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {
    static final String ERROR = "Error";
    ManageOrder contract = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // create new private/public key pair
        final ECKeyPair[] keyPair = {null};
        // Code here executes on main thread after user presses button
        try {
            keyPair[0] = Keys.createEcKeyPair();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(ERROR,"No Such Algorithm Exception");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            Log.e(ERROR,"No Such Provider Exception");
        }

        BigInteger publicKey = keyPair[0].getPublicKey();
        Alice.PUBLIC_KEY = Numeric.toHexStringWithPrefix(publicKey);

        BigInteger privateKey = keyPair[0].getPrivateKey();
        Alice.PRIVATE_KEY = Numeric.toHexStringWithPrefix(privateKey);
        saveFile(Alice.PUBLIC_KEY, "public_key.pem");
        saveFile(Alice.PRIVATE_KEY, "private_key.pem");
    }

    public void onRegister_ok(View view) {

        EditText edit_username = (EditText) findViewById(R.id.edit_username);
        String username = edit_username.getText().toString();
        EditText edit_userphone = (EditText) findViewById(R.id.edit_userphone);
        String userphone = edit_userphone.getText().toString();


        final Intent intent_main2 = new Intent(getApplicationContext(), MainActivity.class);
        final Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
        if((fileExistance("public_key.pem"))&&(fileExistance("private_key.pem"))){
            try {
                ManageOrder manageOrder = loadContract();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Uint256 result = null;
            try {
                result = contract.getBalance(new Address(Alice.ADDRESS)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Balance from the address: " +result.getValue());
            startActivity(intent_main2);
        }else{
            startActivity(intent_register);
        }






    }

    /**
     * Save data to new file
     **/
    public void saveFile(String message, String filename) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(message.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ManageOrder loadContract() throws Exception {
        System.out.println("// Deploy contract");

        contract = ManageOrder
                .load(Web3jConstants.CONTRACT_ADDRESS, LoginActivity.web3j, Alice.CREDENTIALS, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(BigInteger.valueOf(2)));

        String contractAddress = contract.getContractAddress();
        System.out.println("Contract address: " + contractAddress);
        //System.out.println("Contract address balance (initial): " + Web3jUtils.getBalanceWei(MainActivity.web3j, contractAddress));
        return contract;
    }
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}

