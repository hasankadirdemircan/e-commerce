package com.x.ecommerce.controller;

import com.x.ecommerce.dto.AuthRequest;
import com.x.ecommerce.dto.LoginDto;
import com.x.ecommerce.model.Customer;
import com.x.ecommerce.service.CustomerService;
import com.x.ecommerce.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    public String addUser(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PostMapping("/getToken")
    public ResponseEntity<LoginDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(authentication), HttpStatus.OK);
        }

        throw new UsernameNotFoundException("invalid user details.");
    }
}
