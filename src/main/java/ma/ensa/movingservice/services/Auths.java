package ma.ensa.movingservice.services;

import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.ProviderNotAccepted;
import ma.ensa.movingservice.exceptions.UnauthenticatedException;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;


public class Auths {

    public static @NotNull User getUser() throws UnauthenticatedException {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if(user == null)
            throw new UnauthenticatedException();

        return user;
    }

    public static @NotNull Client getClient() throws Exception{
        try{
            return (Client) getUser();
        }catch (ClassCastException e){
            throw new PermissionException();
        }

    }

    public static @NotNull Provider getProvider() throws Exception {

        Provider provider;
        try{
            provider = (Provider) getUser();
        }catch (ClassCastException e){
            throw new PermissionException();
        }

        if(provider.getAcceptedBy() == null)
            throw new ProviderNotAccepted();

        return provider;
    }

    public static @NotNull Admin getAdmin() throws Exception{
        try {
            return (Admin) getUser();
        }catch (ClassCastException e){
            throw new PermissionException();
        }
    }

}
