package com.epilot.files;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@CrossOrigin(origins = "*")
	@GetMapping("/")
	public String healthCheck() {
		return "Up and running!";
	}
  
}
