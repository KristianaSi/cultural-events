package com.kristiana.culturalevents;

import com.kristiana.culturalevents.appui.PageFactory;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import com.kristiana.culturalevents.persitance.repository.RepositoryFactory;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        RepositoryFactory repositoryFactory = JsonRepositoryFactory.getInstance();
        ServiceFactory serviceFactory = ServiceFactory.getInstance(repositoryFactory);
        PageFactory pageFactory = PageFactory.getInstance(serviceFactory);
        try {
            pageFactory.createAuthView().render();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
