package ITMO.coursework.controller;


import ITMO.coursework.service.document.AppService;
import lombok.AllArgsConstructor;
// import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


@RestController
//@Controller - возвращает шаблон страниц
@RequestMapping("app/")
@AllArgsConstructor
public class AppController {

  //  private final AppService appService;

    @GetMapping("/general")
    public String general() {
        return "Главная страница";
    }

    @GetMapping("/cabinet")
    //@PreAuthorize("hasAnyAuthority('ROLE_WORKER')")
    public String cabinet() {
        return "Кабинет слушателя";
    }

    @GetMapping("/trainer")
    //@PreAuthorize("hasAnyAuthority('ROLE_WORKER')")
    public String trainer() {
        return "Тренажер с тестами Ростехнадзора";
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_WORKER')")
    @GetMapping("/lesson")
    public String lessons() {
        return "Выбираем урок";
    }

    @GetMapping("/result")
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String result() {
        return "Протоколы слушателя";
    }



}
