package com.bzhong7.labs.dswopapp.Main;


import android.app.Application;
import android.os.AsyncTask;

import com.bzhong7.labs.dswopapp.ethWrapper.ExampleToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;

public class EthNetwork extends AsyncTask<Void, Void, String> {

    private static final Logger log = LoggerFactory.getLogger(Application.class);


    private String nodeURL = "https://ropsten.infura.io/v3/4876cedb80be4b1494f888302a420403";
    private String privateKey = "7d8f2e90995f42dc6d44beb3ac0c42a91db33e0a66c3040fb45baca9c460e3c6";
    private String erc721Address = "0xf72DE30b93d5e02e0127A49479EF0b589a121199";
    private String publicAccAddress = "0xF214f19Cbf969C6a5Fab6bB0762b94bb0d498f71";


    private Web3j web3;
    private Credentials creds;
    private ExampleToken exampleContract;
    private String contractAddress;
    private DefaultGasProvider gasProvider;
    private String accountAddress;

    private String txHash;

    private TransactionReceipt transactionReceipt;


    public EthNetwork() {
        web3 = Web3j.build(new HttpService(nodeURL));
        creds = Credentials.create(privateKey);
        contractAddress = erc721Address;
        gasProvider = new DefaultGasProvider();
        accountAddress = publicAccAddress;

        exampleContract = ExampleToken.load(contractAddress, web3, creds, gasProvider);
    }

    public void mintToken() throws Exception {
        transactionReceipt = exampleContract.mintUniqueToken(accountAddress, "EXT").send();
    }

    protected String doInBackground (Void... params) {
        try {
            mintToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTxHash() {
        txHash = transactionReceipt.getTransactionHash();
        return txHash;
    }
}