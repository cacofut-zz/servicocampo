package br.com.servicocampo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.servicocampo.model.Usuario;
import br.com.servicocampo.web.service.UsuarioService;

@Controller
@RequestMapping(value = "/admin/usuario")
public class UsuarioController {

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService ) {
		this.usuarioService = usuarioService;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(@RequestParam(value = "id", required = false) Long id, Model model) {
		Usuario usuario = null;
		if (id == null) {
			usuario = new Usuario();
		} else {
			usuario = usuarioService.findOne(id);
		}
		model.addAttribute("usuario", usuario);
		return "admin/usuario/form";
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionar(@Valid Usuario usuario, Errors erros) {

		if (erros.hasErrors()) {
			return "admin/usuario/form";
		}
		usuarioService.save(usuario);
		return "redirect:/admin/usuario/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Usuario> usuarios = usuarioService.list();
		model.addAttribute("usuarios", usuarios);
		return "/admin/usuario/list";
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id) {
		usuarioService.delete(id);
		return "redirect:/admin/usuario/list";
	}
}
