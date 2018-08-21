package br.org.cip.CRMMock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.org.cip.CRMMock.Fabric.ChaincodeService;
import br.org.cip.CRMMock.model.Feriado;
import br.org.cip.CRMMock.service.FeriadoService;

@RestController
public class RestFeriadoController {

	@Autowired
	private ChaincodeService chaincodeService;
	
	@RequestMapping(value="/rest/feriado/consultar/{key}", method=RequestMethod.GET)
	public @ResponseBody Feriado findByDate(@PathVariable String key) {
	    return chaincodeService.getFeriado(key);
	}
	
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
