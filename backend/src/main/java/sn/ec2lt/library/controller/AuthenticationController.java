package sn.ec2lt.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ec2lt.library.dto.LoginResponse;
import sn.ec2lt.library.dto.LoginUserDto;
import sn.ec2lt.library.dto.RegisterUserDto;
import sn.ec2lt.library.entity.Utilisateur;
import sn.ec2lt.library.service.AuthentificationService;
import sn.ec2lt.library.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthentificationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthentificationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Utilisateur> register(@RequestBody RegisterUserDto registerUserDto) {
        Utilisateur registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        Utilisateur authenticatedUser = authenticationService.login(loginUserDto);
        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpiration());
        loginResponse.setRole(authenticatedUser.getRole());
        loginResponse.setFullName(authenticatedUser.getNom() + " " + authenticatedUser.getPrenom());
        return ResponseEntity.ok(loginResponse);
    }
}