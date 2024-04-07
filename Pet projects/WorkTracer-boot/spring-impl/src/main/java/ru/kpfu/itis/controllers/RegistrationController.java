package ru.kpfu.itis.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.repositories.AccountRepository;
import ru.kpfu.itis.repositories.AddressRepository;

import java.util.UUID;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final AccountRepository accountRepository;

    private final AddressRepository addressRepository;

    @GetMapping
    public String registration(
            @RequestParam(required = false, defaultValue = "none", value = "invite") String invite
    ) {
        if (!invite.equals("none")) {

            //const urlParams = new URLSearchParams(window.location.search);
            //const inviteParam = urlParams.get('invite');
            //Todo сделать это в js
            /*
            Как только пользователь нажимает sign up (method = POST), то js отправляет ajax запрос
            в restcontroller. При удачном выполнении производим регистрацию, регистрируем сессию,
            cookies и переводим на страницу профиля. При ошибке всплывающее окно. */
        }
        return "registration";
    }

    @PostMapping
    public String toRegister(
            @RequestParam(required = true, value = "accountUUID") UUID accountUUID
    ) {
        return null;
    }

}
