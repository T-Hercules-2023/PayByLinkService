package com.epam.demo.pbl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.demo.pbl.model.UserDetails;
import com.epam.demo.pbl.repository.UserDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserDetailsController {

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/users")
  public ResponseEntity<List<UserDetails>> getAllUsers() {
    try {
      List<UserDetails> userDetails = new ArrayList<UserDetails>();

      userDetailsRepository.findAll().forEach(userDetails::add);

      if (userDetails.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(userDetails, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  @GetMapping("/users/{id}")
  public ResponseEntity<UserDetails> getUserById(@PathVariable("id") long id) {
    Optional<UserDetails> userData = userDetailsRepository.findById(id);

    if (userData.isPresent()) {
      return new ResponseEntity<>(userData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  @GetMapping("/users/{mobileNumber}")
  public ResponseEntity<Long> getUserByMobileNo(@PathVariable("mobileNumber") long mobileNumber) {
    Optional<Long> _mobileNumber = userDetailsRepository.findCountByMobileNumber(mobileNumber);

    if (_mobileNumber.isPresent()) {
      return new ResponseEntity<>(_mobileNumber.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/users/login")
  public ResponseEntity<?> loginUser(@RequestBody UserDetails userDetails) {
    Optional<UserDetails> _userDetails = userDetailsRepository.findByMobileNumber(userDetails.getMobileNumber());
    if (_userDetails.isPresent()) {
      if(!passwordEncoder.matches(userDetails.getPassword(), _userDetails.get().getPassword())){
        return new ResponseEntity<>("Incorrect Password!!", HttpStatus.OK);
      }
      return new ResponseEntity<>(_userDetails.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/users/createUser")
  public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails) {
    try {
      UserDetails user = new UserDetails();
      BeanUtils.copyProperties(userDetails, user);
      user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
      user.setActive(true);
      UserDetails _userDetails = userDetailsRepository.save(user);
      return new ResponseEntity<>(_userDetails, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDetails> updateUser(@PathVariable("id") long id, @RequestBody UserDetails userDetails) {
    Optional<UserDetails> userData = userDetailsRepository.findById(id);
    if (userData.isPresent()) {
      UserDetails _userDetails = userData.get();
      BeanUtils.copyProperties(userDetails, _userDetails);
      return new ResponseEntity<>(userDetailsRepository.save(_userDetails), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
    try {
      userDetailsRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/users")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    try {
      userDetailsRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/users/user")
  public ResponseEntity<List<UserDetails>> findActiveUsers() {
    try {
      List<UserDetails> userDetails = userDetailsRepository.findByActive(true);

      if (userDetails.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(userDetails, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
