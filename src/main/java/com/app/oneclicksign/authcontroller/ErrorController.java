package com.app.oneclicksign.authcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/error")
	public String showErrorPage(Model model) {
		
		return "error"; // Name of  HTML template
	}
}
