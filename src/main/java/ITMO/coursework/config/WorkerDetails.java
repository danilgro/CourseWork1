package ITMO.coursework.config;

import ITMO.coursework.model.db.entity.people.Worker;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class WorkerDetails {// implements UserDetails {
    private Worker worker;
    public WorkerDetails (Worker worker){
        this.worker = worker;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.stream(worker.getRoles().split(", "))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }  //роли хранятся в String, а возвращать должны в коллекции
//
//    @Override
//    public String getPassword() {
//        return worker.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return worker.getName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;  // указывает истёк ли срок действия учетки пользователя (true = действительна)
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;  // указывает заблокирован ли пользователь (true = разблочен)
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // указывает истёк ли срок действия пароля пользователя (true = действителен)
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // указывает включен ли пользователь ( true = включен)
//    }

}

