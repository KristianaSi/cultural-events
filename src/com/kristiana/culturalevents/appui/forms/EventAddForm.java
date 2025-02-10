package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.dto.EventAddDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EventAddForm implements Renderable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final ServiceFactory serviceFactory;

    public EventAddForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        EventService eventService = serviceFactory.getEventService();
        LocationService locationService = serviceFactory.getLocationService();

        String name;
        do {
            System.out.println("Впишіть назву події: ");
            name = reader.readLine().trim();
            if (name.isEmpty()) {
                System.out.println("Назва не може бути порожньою.");
            }
        } while (name.isEmpty());

        String participators;
        do {
            System.out.println("Впишіть учасників події: ");
            participators = reader.readLine().trim();
            if (participators.isEmpty()) {
                System.out.println("Учасники не можуть бути порожніми.");
            }
        } while (participators.isEmpty());

        LocalDate date = null;
        while (date == null) {
            System.out.println("Вкажіть дату події у форматі yyyy-mm-dd: ");
            try {
                date = LocalDate.parse(reader.readLine().trim());
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Дата не може бути в минулому. Спробуйте ще раз.");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println(
                    "Неправильний формат дати. Введіть ще раз у форматі yyyy-mm-dd.");
            }
        }

        Set<EventLocation> allLocationsSet = locationService.getAll(loc -> true);
        List<EventLocation> allLocations = new ArrayList<>(allLocationsSet);

        EventLocation location = null;

        if (allLocations.isEmpty()) {
            System.out.println("Немає доступних локацій.");
        } else {
            System.out.println("\nДоступні локації: ");
            for (int i = 0; i < allLocations.size(); i++) {
                EventLocation loc = allLocations.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, loc.getName(), loc.getDescription());
            }

            while (true) {
                System.out.println("Виберіть номер локації (0 для повернення): ");
                try {
                    int choice = Integer.parseInt(reader.readLine().trim());
                    if (choice == 0) {
                        System.out.println("Повернення до головного меню...");
                        return;
                    }
                    if (choice > 0 && choice <= allLocations.size()) {
                        location = allLocations.get(choice - 1);
                        break;
                    } else {
                        System.out.println("Невірний номер, спробуйте ще раз.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введіть коректне число.");
                }
            }
        }

        String description;
        do {
            System.out.println("Впишіть опис події: ");
            description = reader.readLine().trim();
            if (description.isEmpty()) {
                System.out.println("Опис не може бути порожнім.");
            }
        } while (description.isEmpty());

        try {
            EventAddDto eventAddDto = new EventAddDto(
                UUID.randomUUID(),
                name,
                participators,
                location,
                date,
                description
            );

            eventService.add(eventAddDto);
            JsonRepositoryFactory.getInstance().commit();

            System.out.println("Подія успішно створена!");
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Створення нової події ~~~");
        try {
            process();
        } catch (IOException e) {
            System.err.println("Помилка при введенні даних: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
