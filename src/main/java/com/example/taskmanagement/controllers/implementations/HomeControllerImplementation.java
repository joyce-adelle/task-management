package com.example.taskmanagement.controllers.implementations;

import com.example.taskmanagement.controllers.AuthController;
import com.example.taskmanagement.dtos.requests.LoginRequest;
import com.example.taskmanagement.dtos.requests.SignUpRequest;
import com.example.taskmanagement.dtos.responses.ApiResponse;
import com.example.taskmanagement.dtos.responses.LoginResponse;
import com.example.taskmanagement.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

//@Validated
@RestController

//@RequiredArgsConstructor
public class HomeControllerImplementation  {


    @RequestMapping("/")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("testwebsocket.html");
        return modelAndView;
    }

}
