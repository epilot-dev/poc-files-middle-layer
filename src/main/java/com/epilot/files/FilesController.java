package com.epilot.files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilesController {

	@CrossOrigin(origins = "*")
	@GetMapping("/files/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "name") String name) {
		if (name == null || name.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		ClassPathResource file = new ClassPathResource("static/receipt.pdf");

		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", file.getFilename());
				
		try {
			byte[] bytes = file.getInputStream().readAllBytes();
			return ResponseEntity.ok().headers(headers).body(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}
  
}
