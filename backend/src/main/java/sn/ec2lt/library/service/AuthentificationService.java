package sn.ec2lt.library.service;

import sn.ec2lt.library.dto.LoginUserDto;
import sn.ec2lt.library.dto.RegisterUserDto;
import sn.ec2lt.library.entity.Utilisateur;
public interface AuthentificationService {
    Utilisateur signUp(RegisterUserDto registerUserDto);
    Utilisateur login(LoginUserDto loginUserDto);
}
