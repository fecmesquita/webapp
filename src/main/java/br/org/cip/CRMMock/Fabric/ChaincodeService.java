package br.org.cip.CRMMock.Fabric;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.stereotype.Service;

import com.google.protobuf.ByteString;

import br.org.cip.CRMMock.model.Feriado;
import br.org.cip.CRMMock.model.UserVO;

//@Service
public class ChaincodeService {

	
	//TODO resgister user admin...
	private static HFCAClient caClient;// = getHfCaClient("http://localhost:7054", null);
	private static UserVO admin;// = getAdmin(caClient);
	private static UserVO appUser;
	//private static UserVO appUser = getUser(caClient, admin, "hfuser");
	private static HFClient client;// = getHfClient();
	private static Channel channel;// = getChannel(client);
	
	public ChaincodeService() throws Exception{
		caClient = getHfCaClient("http://localhost:7054", null);
		admin = getAdmin(caClient);
		//appUser = getUser(caClient, admin, "hfuser");
		client = getHfClient();
        client.setUserContext(admin);
        channel = getChannel(client);
    }
	
	/**
     * Initialize and get HF channel
     *
     * @param client The HFC client
     * @return Initialized channel
     * @throws InvalidArgumentException
     * @throws TransactionException
     */
    static Channel getChannel(HFClient client) {
        // initialize channel
        // peer name and endpoint in fabcar network
        try {
	        	Peer peer = client.newPeer("peer0.org1.example.com", "grpc://localhost:7051");
	        	// eventhub name and endpoint in fabcar network
	        	EventHub eventHub = client.newEventHub("eventhub01", "grpc://localhost:7053");
	        	// orderer name and endpoint in fabcar network
	        	Orderer orderer = client.newOrderer("orderer.example.com", "grpc://localhost:7050");
	        	// channel name in fabcar network
	        	Channel channel = client.newChannel("mychannel");
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();
			return channel;
		} catch (InvalidArgumentException | TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channel;
    }

    /**
     * Create new HLF client
     *
     * @return new HLF client instance. Never null.
     * @throws CryptoException
     * @throws InvalidArgumentException
     */
    static HFClient getHfClient() {
        // initialize default cryptosuite
        CryptoSuite cryptoSuite;
		try {
			cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
			// setup the client
			HFClient client = HFClient.createNewInstance();
			client.setCryptoSuite(cryptoSuite);
			return client;
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException | CryptoException
				| InvalidArgumentException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }


    /**
     * Register and enroll user with userId.
     * If UserVO object with the name already exist on fs it will be loaded and
     * registration and enrollment will be skipped.
     * @param user 
     * @param caClient 
     *
     * @param caClient  The fabric-ca client.
     * @param registrar The registrar to be used.
     * @param userId    The user id.
     * @return UserVO instance with userId, affiliation,mspId and enrollment set.
     * @throws Exception
     */
    static UserVO getUser(HFCAClient caClient, UserVO registrar, String userId) {
        try {
        		UserVO user = tryDeserialize(userId);
            if (user == null) {
                RegistrationRequest rr = new RegistrationRequest(userId, "org1");
                String enrollmentSecret = caClient.register(rr, registrar);
                Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
                user = new UserVO(userId, "org1", "Org1MSP", enrollment);
                serialize(user);
            }
            return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    		
    }
    /**
     * Enroll admin into fabric-ca using {@code admin/adminpw} credentials.
     * If UserVO object already exist serialized on fs it will be loaded and
     * new enrollment will not be executed.
     *
     * @param caClient The fabric-ca client
     * @return UserVO instance with userid, affiliation, mspId and enrollment set
     * @throws Exception
     */
    static UserVO getAdmin(HFCAClient caClient) {
        UserVO admin;
		try {
			admin = tryDeserialize("admin");
			if (admin == null) {
				Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
				admin = new UserVO("admin", "org1", "Org1MSP", adminEnrollment);
				serialize(admin);
			}
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    /**
     * Get new fabic-ca client
     *
     * @param caUrl              The fabric-ca-server endpoint url
     * @param caClientProperties The fabri-ca client properties. Can be null.
     * @return new client instance. never null.
     * @throws Exception
     */
    static HFCAClient getHfCaClient(String caUrl, Properties caClientProperties){
		try {
			CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
			HFCAClient caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
			caClient.setCryptoSuite(cryptoSuite);
			return caClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }


    // user serialization and deserialization utility functions
    // files are stored in the base directory

    /**
     * Serialize UserVO object to file
     *
     * @param user The object to be serialized
     * @throws IOException
     */
    static void serialize(UserVO user) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(
                Paths.get(user.getName() + ".jso")))) {
            oos.writeObject(user);
        }
    }

    /**
     * Deserialize UserVO object from file
     *
     * @param name The name of the user. Used to build file name ${name}.jso
     * @return
     * @throws Exception
     */
    static UserVO tryDeserialize(String name) throws Exception {
        if (Files.exists(Paths.get(name + ".jso"))) {
            return deserialize(name);
        }
        return null;
    }

    static UserVO deserialize(String name) throws Exception {
        try (ObjectInputStream decoder = new ObjectInputStream(
                Files.newInputStream(Paths.get(name + ".jso")))) {
            return (UserVO) decoder.readObject();
        }
    }
    
    
    public List<Feriado> getAllFeriados() throws InvalidArgumentException, ProposalException  {
		
		
        // create chaincode request
        QueryByChaincodeRequest qpr = client.newQueryProposalRequest();

        ChaincodeID fabcarCCId = ChaincodeID.newBuilder().setName("minerva-app").build();
        qpr.setChaincodeID(fabcarCCId);
        qpr.setFcn("queryAllFeriado");
		
        Collection<ProposalResponse> responses = channel.queryByChaincode(qpr);
         
        for (ProposalResponse response : responses) {
            if (response.isVerified() && response.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                ByteString payload = response.getProposalResponse().getResponse().getPayload();
                //System.out.println("OKKK " + payload.toStringUtf8());
                try (JsonReader jsonReader = Json.createReader(new ByteArrayInputStream(payload.toByteArray()))) {
                    // parse response
                    JsonArray arr = jsonReader.readArray();
                    //Map<String, Feriado> feriados = new HashMap<>();
                    List<Feriado> feriados = new ArrayList<Feriado>();
                    for (int i = 0; i < arr.size(); i++) {
                        JsonObject rec = arr.getJsonObject(i);
                        //System.out.println(rec);
                        generateFeriado(rec);
                        Feriado feriadoRecord = generateFeriado(rec);
                        feriados.add(feriadoRecord);
                    }
                    return feriados;
                }
            } else {
               // log.error("response failed. status: " + response.getStatus().getStatus());
            }
        }
        return null;
    }

	private Feriado generateFeriado(JsonObject rec) {
		
    		long key = Long.parseLong(rec.get("Key").toString().substring(1, rec.get("Key").toString().length()-1));
    		String tipoRequisicao = rec.get("Record").asJsonObject().getString("tipoRequisicao");
		String data = rec.get("Record").asJsonObject().get("data").toString();
		String descricao = rec.get("Record").asJsonObject().getString("descricao");
		String situacao = rec.get("Record").asJsonObject().getString("situacao");
		String tipoFeriado= rec.get("Record").asJsonObject().getString("tipoFeriado");
		Feriado feriado = new Feriado();
		feriado.setData(data);
		feriado.setDescricao(descricao);
		feriado.setId(key);
		feriado.setSituacao(situacao);
		feriado.setTipoFeriado(tipoFeriado);
		feriado.setTipoRequisicao(tipoRequisicao);
		
		return feriado;
	
	}
    
    
}
