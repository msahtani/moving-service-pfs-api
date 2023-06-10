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

        Object user = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if(user == null)
            throw new UnauthenticatedException();

        return (User) user;
    }

    public static @NotNull Client getClient(){

        if(!(getUser() instanceof Client client)){
            throw new PermissionException();
        }

        return client;

    }

    public static @NotNull Provider getProvider(boolean uncheck) {

        if(!(getUser() instanceof Provider provider)){
            throw new PermissionException();
        }

        if(!uncheck && provider.getAcceptedBy() == null)
            throw new ProviderNotAccepted();

        return provider;
    }

    public static @NotNull Provider getProvider(){
        return getProvider(false);
    }

    public static @NotNull Admin getAdmin(){

        if(!(getUser() instanceof Admin admin)){
            throw new PermissionException();
        }

        return admin;
    }

}
