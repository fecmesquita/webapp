package br.org.cip.CRMMock.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.org.cip.CRMMock.Fabric.ChaincodeService;
import br.org.cip.CRMMock.model.Feriado;
import br.org.cip.CRMMock.model.UserVO;
import br.org.cip.CRMMock.model.form.FeriadoForm;
import br.org.cip.CRMMock.model.form.LoginForm;
import br.org.cip.CRMMock.model.theme.Config;
import br.org.cip.CRMMock.model.theme.ThemeType;
import br.org.cip.CRMMock.service.FeriadoService;

@Controller
public class FeriadoController {

	@Autowired
	private FeriadoService feriadoService;
	
	@Autowired
	private ChaincodeService chaincodeService;
	
	@RequestMapping(value = "/feriado/incluir")
	public String incluirFeriado(@ModelAttribute("feriado") Feriado feriado, HttpSession session, Model model) throws IOException {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());

//		System.out.println("Feriado: " + feriado);
//		System.out.println("Feriado is null?" + feriado==null);
		
		FeriadoForm feriadoForm = new FeriadoForm();
//		feriadoForm.setSituacao(true);
		model.addAttribute("feriadoform", feriadoForm );

		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		model.addAttribute("submitButton", "Criar");
		
		model.addAttribute("action", "salvar");

		return "feriadoForm";
	}
	
	@RequestMapping(value = "/feriado/alterar/{feriadoId}", method=RequestMethod.GET)
	public String alterarFeriadoId(@PathVariable String feriadoId, HttpSession session, Model model) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		
//	    System.out.println(feriadoId);
//	    System.out.println("test");
	    FeriadoForm feriadoForm = new FeriadoForm();
	    feriadoForm.setId(Long.parseLong(feriadoId));
		model.addAttribute("feriadoform", feriadoForm );

		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		model.addAttribute("submitButton", "Atualizar");
		
		model.addAttribute("action", "alterar");
		
		//model.addAttribute("feriado", feriadoService.getFeriado(Integer.parseInt(feriadoId)));
		
		model.addAttribute("feriado", chaincodeService.getFeriado(feriadoId));

		return "feriadoForm";
	}
	
	@RequestMapping(value = "/feriado/salvar", method=RequestMethod.POST)
	public String salvarFeriado(@ModelAttribute("feriadoForm") FeriadoForm feriadoForm, HttpSession session, Model model, RedirectAttributes attributes) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		
		System.out.println("Situacao: "+feriadoForm.getSituacao());
		System.out.println("TipoFeriado: "+feriadoForm.getTipoFeriado());
		System.out.println("TipoRequisicao: "+feriadoForm.getTipoRequisicao());
		
		//System.out.println( Theme.theme.equals(ThemeType.CRM));
		
		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		//long id = feriadoService.incluir("Efetivar", feriadoForm.getData(), feriadoForm.getSituacao(), feriadoForm.getTipoferiado(), feriadoForm.getDescricao());
		
		long id = chaincodeService.recordFeriado(feriadoForm.getData(), feriadoForm.getDescricao(), feriadoForm.getSituacao(), feriadoForm.getTipoFeriado(), "Efetivar");
		
		model.addAttribute("message", "Feriado criado!");
		attributes.addFlashAttribute("message", "Feriado criado!");
		
		return "redirect:/feriado/consultar/" + id;
	}
	
	@RequestMapping(value = "/feriado/alterar", method=RequestMethod.POST)
	public String alterarFeriadoForm(@ModelAttribute("feriadoForm") FeriadoForm feriadoForm, HttpSession session, Model model, RedirectAttributes attributes) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		
		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		//long id = feriadoService.alterar(feriadoForm.getId(), "Efetivar", feriadoForm.getData(), feriadoForm.getSituacao(), feriadoForm.getTipoFeriado(), feriadoForm.getDescricao());

		long id = chaincodeService.changeFeriado(feriadoForm.getData(), feriadoForm.getDescricao(), feriadoForm.getSituacao(), feriadoForm.getTipoFeriado(), "Efetivar");
		
		attributes.addFlashAttribute("message", "Feriado alterado com sucesso!");

		return "redirect:/feriado/consultar/" + id;
	}
	
	
	
	@RequestMapping(value = "/feriado/consultar/{feriadoId}", method=RequestMethod.GET)
	public String feriadoDetails(@PathVariable String feriadoId, HttpSession session, Model model,@ModelAttribute("message") String message) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		model.addAttribute("message", message);
		
		//Feriado feriado = feriadoService.getFeriado(Integer.parseInt(feriadoId));
		
		Feriado feriado = chaincodeService.getFeriado(feriadoId);
		
		model.addAttribute("feriado", feriado);

		return "feriadoDetails";
	}
	
	@RequestMapping(value = "/feriado/ativar/{feriadoId}", method=RequestMethod.GET)
	public String ativarFeriado(@PathVariable String feriadoId, HttpSession session, Model model) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		//feriadoService.ativar(Long.parseLong(feriadoId), "Efetivar");
		
		chaincodeService.changeFeriadoSituacao(feriadoId, "ativar");
		
		//attributes.addFlashAttribute("message", "Feriado excluido com sucesso.");

		return "redirect:/";
	}
	
	@RequestMapping(value = "/feriado/inativar/{feriadoId}", method=RequestMethod.GET)
	public String inativarFeriado(@PathVariable String feriadoId, HttpSession session, Model model) {
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		UserVO user = (UserVO) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new UserVO();
		}
		model.addAttribute("user", user);
		
		//feriadoService.inativar(Long.parseLong(feriadoId), "Efetivar");
		
		chaincodeService.changeFeriadoSituacao(feriadoId, "inativar");
		
		//attributes.addFlashAttribute("message", "Feriado excluido com sucesso.");

		return "redirect:/";
	}
	
	/*@RequestMapping(value = "/feriado/excluir/{feriadoId}", method=RequestMethod.GET)
	public String DeleteFeriado(@PathVariable String feriadoId, HttpSession session, Model model, RedirectAttributes attributes) {
		model.addAttribute("theme", Theme.theme.equals(ThemeType.CRM));
		User user = (User) session.getAttribute("usuarioLogado");
		if (user == null) {
			user = new User();
		}
		model.addAttribute("user", user);
		
		feriadoService.delete(Long.parseLong(feriadoId));
		
		
		attributes.addFlashAttribute("message", "Feriado excluido com sucesso.");

		return "redirect:/";
	}*/
	

}
