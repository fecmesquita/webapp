package br.org.cip.CRMMock.controller.rest;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cip.CRMMock.Fabric.ChaincodeService;
import br.org.cip.CRMMock.model.Feriado;

@RestController
public class RestFeriadoController {

	private static final Logger log = LoggerFactory.getLogger(RestFeriadoController.class);
	
	@Autowired
	private ChaincodeService chaincodeService;
	
	@RequestMapping(value="/rest/feriado/consultar", method = RequestMethod.POST)
	public Feriado update(@RequestBody String idFeriado) throws RestFeriadoControllerException{
		try {
			JsonReader jsonReader = Json.createReader(new StringReader(idFeriado));
			JsonObject rec = jsonReader.readObject();
			Integer key = rec.asJsonObject().getInt("idFeriado");
			log.debug("Consulta Rest de Feriado de id: {}", idFeriado);
			return chaincodeService.getFeriado(String.valueOf(key));
		}catch (Exception e) {
			log.error("Nao foi possivel consultar o Feriado de id: {}.", idFeriado);
			log.error("StackTrace: ", e);
			throw new RestFeriadoControllerException("Nao foi possivel consultar o Feriado de id: " + idFeriado + ".");
		}
	}
	//curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"idFeriado":20180907}' http://localhost:8080/CRMMock/rest/feriado/consultar
	
	/*@RequestMapping(value="/rest/feriado/consultar/{key}", method=RequestMethod.GET)
	public @ResponseBody Feriado findByDate(@PathVariable String key) {
	    return chaincodeService.getFeriado(key);
	}*/
	//curl --header "Accept: application/json" http://localhost:8080/CRMMock/rest/feriado/consultar/20180125
	
	/*@Autowired
	private FeriadoService feriadoService;
	
	@RequestMapping(value="/rest/feriado/consultar/{id}", method=RequestMethod.GET)
	public @ResponseBody Feriado findById(@PathVariable long id) {
	    return feriadoService.getFeriado(id);
	}
	//curl --header "Accept: application/json" http://localhost:8080/CRMMock/rest/feriado/consultar/1
	
	
	//retornar true -- se existir feriado (Nacional) e ativo --  ou false caso contrário. 
	
	/*@RequestMapping(value="/rest/feriado/alterar", method=RequestMethod.PUT)
	public @ResponseBody Feriado updateFoo(@RequestBody Feriado feriado) {
	    return feriadoService.alterar(feriado);
	}
	//curl -i -X PUT -H "Content-Type: application/json" -d '{"id":"1","descricao":"Teste Curl rest","data":"06/08/2018","situacao":"Ativo(a)","tipoFeriado":"Estadual","tipoRequisicao":"Efetivar"}' http://localhost:8080/CRMMock/rest/feriado/alterar
	*/
	
}
