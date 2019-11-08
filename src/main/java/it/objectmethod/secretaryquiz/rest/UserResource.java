package it.objectmethod.secretaryquiz.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
					System.out.println("Errore durante l'ivio dell'email" + e);
				}

			} else
				response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			System.out.println(e);
			response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping("/login")
	public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		ResponseEntity<User> res = new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			User user = userService.checkUser(email, password);
			if (user != null) {
				res = new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;

	}

	@GetMapping("/get")
	public User findById(@RequestParam("id") int id) {
		User user = userRepository.findById(id);
		return user;
	}
}
