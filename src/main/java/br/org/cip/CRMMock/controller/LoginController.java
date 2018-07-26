package br.org.cip.CRMMock.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import br.org.cip.CRMMock.dao.ThemeDao;
import br.org.cip.CRMMock.dao.UserDao;
import br.org.cip.CRMMock.dao.UserDaoImpl;
import br.org.cip.CRMMock.model.User;
import br.org.cip.CRMMock.model.form.LoginForm;
import br.org.cip.CRMMock.model.theme.Config;
import br.org.cip.CRMMock.model.theme.ThemeType;

@Controller
public class LoginController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ThemeDao themeDao;
	
	
	@RequestMapping("/loginForm")
	public String loginForm(Model model) {

		LoginForm loginModel = new LoginForm();
		model.addAttribute("loginform", loginModel);
		
//		model.addAttribute("theme", Theme.theme.equals(ThemeType.CRM));
		model.addAttribute("theme", Config.getInstance().getThemeType().getLabel());
		
		return "login";
	}
	
	@RequestMapping("/changeTheme")
	public String chengeTheme(@ModelAttribute("theme") Config theme, Model model) throws IOException {
		
		if(Config.getInstance().getThemeType().equals(ThemeType.CRM)) Config.getInstance().setThemeType(ThemeType.WEB);
		else Config.getInstance().setThemeType(ThemeType.CRM);
		
		themeDao.update(Config.getInstance());
		
//		if(Theme.getThemeType().equals(ThemeType.CRM)) Theme.setThemeType(ThemeType.WEB);
//		else Theme.setThemeType(ThemeType.CRM);

		
//		if(Theme.theme.equals(ThemeType.CRM)) Theme.theme = ThemeType.WEB;
//		else Theme.theme = ThemeType.CRM;

		return "redirect:loginForm";
	}

	
	@RequestMapping("/efetuaLogin")
	public String efetuaLogin(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session, Model model) {

		LoginForm loginModel = new LoginForm();
		model.addAttribute("loginform", loginModel);

		User user = null;
		user = userDao.findByNamePassword(loginForm.getUsername(), loginForm.getPassword());
		if (user != null) {
			// usuario existe, guardaremos ele na session
			session.setAttribute("usuarioLogado", user);
			user.setAuthenticated(true);
			//System.out.println("USUARIO DA SESSAO: " + session.getAttribute("usuarioLogado"));
			return "redirect:/";
		}
		// ele errou a senha, voltou para o formulario
		return "redirect:loginForm";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		try {
			User user = (User) session.getAttribute("usuarioLogado");
			user.setAuthenticated(false);
			session.invalidate();
			return "redirect:loginForm";
		} catch (Exception e) {
			return "error";
//			throw new RuntimeException("Nao foi possivel pegar o usuario da sessao.", e);
		}
	}
}
