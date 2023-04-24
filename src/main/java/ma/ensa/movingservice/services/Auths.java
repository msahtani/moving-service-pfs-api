package ma.ensa.movingservice.services;

import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class Auths {

    public static Optional<User> getUser(){
        try {
            User user = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            return Optional.of(user);
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Client> getClient(){
        try{
            return Optional.of(
                    (Client) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal()
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public static Optional<Provider> getProvider(){
        try{
            return Optional.of(
                    (Provider) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal()
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Admin> getAdmin(){
        try{
            Admin admin = (Admin) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return Optional.of(admin);
        }catch (ClassCastException ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

}
