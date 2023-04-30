package ma.ensa.movingservice.services;

import ma.ensa.movingservice.exceptions.ProviderNotAccepted;
import ma.ensa.movingservice.exceptions.UnauthenticatedException;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auths {

    public static User getUser() throws UnauthenticatedException {
        try{
            return  (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        }catch (ClassCastException ex){
            throw new UnauthenticatedException();
        }

    }

    public static Client getClient() throws UnauthenticatedException{
        return (Client) getUser();
    }

    public static Provider getProvider() throws ProviderNotAccepted, UnauthenticatedException {

        Provider provider = (Provider) getUser();

        if(provider.getAcceptedBy() == null)
            throw new ProviderNotAccepted();

        return provider;

    }

    public static Admin getAdmin() throws UnauthenticatedException{
        return (Admin) getUser();
    }

}
