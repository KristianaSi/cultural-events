package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.dto.LocationUpdateDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationUpdateForm implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public LocationUpdateForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        LocationService locationService = serviceFactory.getLocationService();

        Set<EventLocation> locationsSet = locationService.getAll();
        List<EventLocation> locations = new ArrayList<>(locationsSet);

        if (locations.isEmpty()) {
            System.out.println("Немає доступних локацій для оновлення.");
            return;
        }

        printYellow("Оберіть локацію для оновлення:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.printf("%d. %s - %s%n", i + 1, locations.get(i).getName(),
                locations.get(i).getDescription());
        }

        System.out.print("Введіть номер локації (0 для виходу): ");
        int choice = Integer.parseInt(reader.readLine());
        if (choice < 1 || choice > locations.size()) {
            System.out.println("Відміна оновлення.");
            return;
        }

        EventLocation location = locations.get(choice - 1);

        System.out.println("Введіть нову назву локації (залиште порожнім для відміни): ");
        String newName = reader.readLine();
        if (newName.isEmpty()) {
            newName = location.getName();
        }

        System.out.println("Введіть новий опис локації (залиште порожнім для відміни): ");
        String newDescription = reader.readLine();
        if (newDescription.isEmpty()) {
            newDescription = location.getDescription();
        }

        LocationUpdateDto updateDto = new LocationUpdateDto(
            location.getId(),
            newName,
            newDescription
        );

        locationService.update(updateDto);
        System.out.println("Локацію успішно оновлено!");
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Редагування локації ~~~");
        process();
    }
}
