package com.x.ecommerce.controller;

import com.x.ecommerce.dto.AuthRequest;
import com.x.ecommerce.dto.LoginDto;
import com.x.ecommerce.model.Customer;
import com.x.ecommerce.service.CustomerService;
import com.x.ecommerce.service.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
/*
{
   "email":"hkdemircan06@gmail.com",
   "firstName":"hkdemircan06",
   "surname":"demircan",
   "password":"123456",
   "address":{
      "country":"TR",
      "city":"Ankara",
      "district":"etimesgut",
      "addressLine":"adressline",
      "postCode":"060606"
   },
   "roles":"ROLE_ADMIN"
}
 */
    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addUser(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
    }

    @PostMapping("/getToken")
    public ResponseEntity<LoginDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(authentication), HttpStatus.OK);
        }

        throw new UsernameNotFoundException("invalid user details.");
    }

    @GetMapping("/log")
    public void logging() {
        log.info("testtttttt");
    }
}
