package it.objectmethod.secretaryquiz.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.objectmethod.secretaryquiz.domain.User;
import it.objectmethod.secretaryquiz.repository.UserRepository;
import it.objectmethod.secretaryquiz.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserResource {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@CrossOrigin
	@PostMapping("/save")
	public ResponseEntity<Object> addUpdateUser(@RequestBody User user) {
		ResponseEntity<Object> response = new ResponseEntity<Object>(user, HttpStatus.OK);
		try {
			boolean error = userService.checkSearchByUniqueEmail(user);
			if (error == false) {
				userRepository.save(user);
				try {
					userService.sendEmailToUser(user);
				} catch (Exception e) {
					System.out.println("Errore durante l'ivio dell'email"+e);
				}

			} else
				response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			System.out.println(e);
			response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
