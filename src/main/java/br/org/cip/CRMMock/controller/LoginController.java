package br.org.cip.CRMMock.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import br.org.cip.CRMMock.dao.ThemeDao;
import br.org.cip.CRMMock.dao.UserDao;
import br.org.cip.CRMMock.model.UserVO;
import br.org.cip.CRMMock.model.form.LoginForm;
import br.org.cip.CRMMock.model.theme.Config;
import br.org.cip.CRMMock.model.theme.ThemeType;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ThemeDao themeDao;
	
	@RequestMapping("/loginForm")
	public String loginForm(Model model) {

		LoginForm loginModel = new LoginForm();
		model.addAttribute("loginform", loginModel);
		
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		
		return "login";
	}
	
	@RequestMapping("/changeTheme")
	public String chengeTheme(@ModelAttribute("theme") Config theme, Model model) throws IOException {
		
		if(Config.getInstance().getThemeType().equals(ThemeType.CRM)) {
			Config.getInstance().setThemeType(ThemeType.WEB);
			log.trace("Tema da aplicacao alterado para webcip." );
		}
		else {
			Config.getInstance().setThemeType(ThemeType.CRM);
			log.trace("Tema da aplicacao alterado para crm." );
		}
		
		themeDao.update(Config.getInstance());
		
		return "redirect:loginForm";
	}
	
	@RequestMapping("/efetuaLogin")
	public String efetuaLogin(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session, Model model) {

		LoginForm loginModel = new LoginForm();
		model.addAttribute("loginform", loginModel);

		UserVO user = null;
		user = userDao.findByNamePassword(loginForm.getUsername(), loginForm.getPassword());
		if (user != null) {
			session.setAttribute("usuarioLogado", user);
			user.setAuthenticated(true);
			log.debug("Usuario {} efetuou login.", user.getUsername());
			return "redirect:/";
		}
		return "redirect:loginForm";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		try {
			UserVO user = (UserVO) session.getAttribute("usuarioLogado");
			user.setAuthenticated(false);
			session.invalidate();
			log.debug("Usuario {} efetuou logout.", user.getUsername());
			return "redirect:loginForm";
		} catch (Exception e) {
			log.error("Nao foi possivel realizar logout.");
			return "error";
//			throw new RuntimeException("Nao foi possivel pegar o usuario da sessao.", e);
		}
	}
}
