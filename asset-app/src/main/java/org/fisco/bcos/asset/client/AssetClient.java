package org.fisco.bcos.asset.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fisco.bcos.asset.contract.Asset;
import org.fisco.bcos.asset.contract.Test2.AccountEventResponse;
import org.fisco.bcos.asset.contract.Test2.FinancingEventResponse;
import org.fisco.bcos.asset.contract.Test2.RegisterEventResponse;
import org.fisco.bcos.asset.contract.Test2;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class AssetClient {

	static Logger logger = LoggerFactory.getLogger(AssetClient.class);

	private Web3j web3j;

	private Credentials credentials;
	
	private static String adminKey = "123";

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}
	
	public void setAdminKey(String key) {
		adminKey = key;
	}
	
	public static String getAdminKey() {
		return adminKey;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("address", address);
		final Resource contractResource = new ClassPathResource("contract.properties");
		FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
		prop.store(fileOutputStream, "contract address");
	}

	public String loadAssetAddr() throws Exception {
		// load Asset contact address from contract.properties
		Properties prop = new Properties();
		final Resource contractResource = new ClassPathResource("contract.properties");
		prop.load(contractResource.getInputStream());

		String contractAddress = prop.getProperty("address");
		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw new Exception(" load Asset contract address failed, please deploy it first. ");
		}
		logger.info(" load Asset address from contract.properties, address is {}", contractAddress);
		return contractAddress;
	}

	public void init(String privKey) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		Web3j web3j = Web3j.build(channelEthereumService, 1);
		Credentials credentials = Credentials.create(privKey);
		setCredentials(credentials);
		setWeb3j(web3j);
		logger.debug(" web3j is " + web3j + " ,credentials is " + credentials);
	}

	private static BigInteger gasPrice = new BigInteger("30000000");
	private static BigInteger gasLimit = new BigInteger("30000000");
	
	public void deployAssetAndRecordAddr() {
		try {
			Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
			System.out.println(" deploy Asset success, contract address is " + asset.getContractAddress());
			recordAssetAddr(asset.getContractAddress());
		} catch (Exception e) {
			System.out.println(" deploy Asset contract failed, error message is  " + e.getMessage());
		}
	}
	
	public void register(String addr, String name, BigInteger amount, BigInteger status) {
		try {
			String contractAddress = loadAssetAddr();
			Test2 asset = Test2.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.register(addr, name, amount).send();
			System.out.println(receipt);
			List<RegisterEventResponse> response = asset.getRegisterEvents(receipt);
			if (!response.isEmpty()) {
				System.out.println("register successful:" + response.get(0).name);
			} else {
				System.out.println("register error.\n");
			}
		} catch (Exception e) {
			System.out.println("register error" + e.getMessage());
		}
	}

	public void receivable_account(String addr, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Test2 asset = Test2.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.receivable_account(addr, amount, "0").send();
			List<AccountEventResponse> response = asset.getAccountEvents(receipt);
			if (!response.isEmpty()) {
				System.out.printf("completing transaction! %s give %s a receipt, amount is %s\n", response.get(0)._from, response.get(0)._to, response.get(0).amount.toString());
			} else {
				System.out.println("transaction error.\n");
			}
		} catch (Exception e) {
			System.out.println("error message:" + e.getMessage());
		}
	}
	
	public void transfer(String assetAccount, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Test2 asset = Test2.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			asset.transfer(assetAccount, amount, "0").send();
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("error message:" + e.getMessage());
		}
	}
	
	public void financing(BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Test2 asset = Test2.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.financing(amount).send();
			System.out.println("financing error.\n");
			List<FinancingEventResponse> response = asset.getFinancingEvents(receipt);
			if (!response.isEmpty()) {
				System.out.printf("financing %s amount: %s\n", response.get(0)._from, response.get(0).amount.toString());
			}
			else {
				System.out.println("financing error.\n");
			}
		} catch (Exception e) {
			System.out.println("error message:" + e.getMessage());
		}
	}
	
	public void account_settlement() {
		try {
			String contractAddress = loadAssetAddr();
			Test2 asset = Test2.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			asset.account_settlement().send();
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("error message:" + e.getMessage());
		}
	}
}
