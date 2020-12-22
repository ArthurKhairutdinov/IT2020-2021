package ru.itis.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.dto.UserForm;
import ru.itis.repositories.UsersRepository;
import ru.itis.models.User;

public class SignUpServiceImpl implements SignUpService{
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .age(form.getAge())
                .firstName(form.getFirst_name())
                .lastName(form.getLast_name())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .build();
        usersRepository.save(user);
    }
}
