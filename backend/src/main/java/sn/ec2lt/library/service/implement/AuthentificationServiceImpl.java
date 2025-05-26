package sn.ec2lt.library.service.implement;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.ec2lt.library.dto.LoginUserDto;
import sn.ec2lt.library.dto.RegisterUserDto;
import sn.ec2lt.library.entity.Utilisateur;
import sn.ec2lt.library.repository.UtilisateurRepository;
import sn.ec2lt.library.service.AuthentificationService;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthentificationServiceImpl(UtilisateurRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Utilisateur signUp(RegisterUserDto input) {
        Utilisateur user = new Utilisateur();
        String[] parts = input.getFullName().split(" ");
        user.setNom(parts.length > 1 ? parts[1] : "");
        user.setPrenom(parts[0]);
        user.setEmail(input.getEmail());
        user.setRole(input.getRole());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Utilisateur login(LoginUserDto loginUserDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDto.getEmail(),
                            loginUserDto.getPassword()
                    )
            );
            return userRepository.findByEmail(loginUserDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Échec de l'authentification: " + e.getMessage());
        }
    }
}