package com.app.oneclicksign.authcontroller;

import com.app.oneclicksign.config.GoogleConfig;
import com.app.oneclicksign.model.UserInfo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProfileController {
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
			UserInfo user = (UserInfo) session.getAttribute("user");
			logger.info(user.getName());
			if (user == null) {
				return "redirect:/login";
			}
			
			model.addAttribute("user", user);
			return "profile";
		}
		
	
}
