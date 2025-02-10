package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.dto.LocationAddDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class LocationAddForm implements Renderable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final ServiceFactory serviceFactory;

    public LocationAddForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        LocationService locationService = serviceFactory.getLocationService();

        String name;
        do {
            System.out.println("Впишіть назву локації: ");
            name = reader.readLine().trim();
            if (name.isEmpty()) {
                System.out.println("Назва не може бути порожньою.");
            }
        } while (name.isEmpty());

        String description;
        do {
            System.out.println("Впишіть опис локації: ");
            description = reader.readLine().trim();
            if (description.isEmpty()) {
                System.out.println("Опис не може бути порожнім.");
            }
        } while (description.isEmpty());

        try {
            LocationAddDto locationAddDto = new LocationAddDto(
                UUID.randomUUID(),
                name,
                description
            );

            locationService.add(locationAddDto);
            JsonRepositoryFactory.getInstance().commit();

            System.out.println("Локація успішно створена!");
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Створення нової локації ~~~");
        try {
            process();
        } catch (IOException e) {
            System.err.println("Помилка при введенні даних: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
