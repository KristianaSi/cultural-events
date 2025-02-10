package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.dto.EventUpdateDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventUpdateForm implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public EventUpdateForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        EventService eventService = serviceFactory.getEventService();
        LocationService locationService = serviceFactory.getLocationService();

        Set<CustomEvent> eventsSet = eventService.getAll();
        List<CustomEvent> events = new ArrayList<>(eventsSet);

        if (events.isEmpty()) {
            System.out.println("У вас ще немає колекцій.");
        } else {
            printYellow("Доступні події для редагування:");
            for (int i = 0; i < events.size(); i++) {
                CustomEvent event = events.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, event.getName(),
                    event.getDescription());
            }

            System.out.print("Введіть номер локації (0 для виходу): ");
            int choice = Integer.parseInt(reader.readLine());
            if (choice < 1 || choice > events.size()) {
                System.out.println("Відміна оновлення.");
                return;
            }

            CustomEvent event = events.get(choice - 1);

            System.out.println("Введіть нову назву події (залиште порожнім для відміни): ");
            String newName = reader.readLine();
            if (newName.isEmpty()) {
                newName = event.getName();
            }

            System.out.println("Введіть нових учасників події (залиште порожнім для відміни): ");
            String newParticipators = reader.readLine();
            if (newParticipators.isEmpty()) {
                newParticipators = event.getParticipators();
            }

            System.out.println(
                "Введіть нову дату події (формат yyyy-mm-dd, залиште порожнім для відміни): ");
            LocalDate newDate = event.getDate();
            String dateInput = reader.readLine();
            if (!dateInput.isEmpty()) {
                try {
                    newDate = LocalDate.parse(dateInput);
                } catch (DateTimeParseException e) {
                    System.out.println("Некоректний формат дати. Дату не оновлено.");
                }
            } else {
                System.out.println("Дату не оновлено.");
            }

            System.out.println("Доступні локації:");
            List<EventLocation> locations = new ArrayList<>(locationService.getAll());
            for (int i = 0; i < locations.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, locations.get(i).getName());
            }

            System.out.print("Виберіть нову локацію (0 для відміни): ");
            EventLocation newLocation = event.getEventLocation();
            try {
                int locationChoice = Integer.parseInt(reader.readLine());
                if (locationChoice >= 1 && locationChoice <= locations.size()) {
                    newLocation = locations.get(locationChoice - 1);
                } else {
                    System.out.println("Некоректний вибір. Локація залишиться незмінною.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некоректний ввід. Локація залишиться незмінною.");
            }

            System.out.println("Введіть новий опис локації (залиште порожнім для відміни): ");
            String newDescription = reader.readLine();
            if (newDescription.isEmpty()) {
                newDescription = event.getDescription();
            }

            EventUpdateDto updateDto = new EventUpdateDto(
                event.getId(),
                newName,
                newParticipators,
                newLocation,
                newDate,
                newDescription
            );

            eventService.update(updateDto);
            System.out.println("Подію успішно оновлено!");
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Редагування події ~~~");
        process();
    }
}
