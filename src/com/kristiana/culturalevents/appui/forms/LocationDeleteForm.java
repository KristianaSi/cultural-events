package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationDeleteForm implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public LocationDeleteForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        LocationService locationService = serviceFactory.getLocationService();

        Set<EventLocation> locationsSet = locationService.getAll();
        List<EventLocation> locations = new ArrayList<>(locationsSet);

        if (locations.isEmpty()) {
            System.out.println("Немає доступних локацій для видалення.");
            return;
        }

        printYellow("Оберіть локацію для видалення:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.printf("%d. %s - %s%n", i + 1, locations.get(i).getName(),
                locations.get(i).getDescription());
        }

        System.out.print("Введіть номер локації (0 для виходу): ");
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Невірний ввід, спробуйте ще раз.");
            return;
        }

        if (choice < 1 || choice > locations.size()) {
            System.out.println("Видалення скасовано.");
            return;
        }

        EventLocation location = locations.get(choice - 1);

        System.out.print("Ви впевнені, що хочете видалити цю локацію? (+/-): ");
        String confirmation = reader.readLine();
        if (confirmation.equalsIgnoreCase("+")) {
            locationService.delete(location.getId());
            JsonRepositoryFactory.getInstance().commit();
            System.out.println("Локацію успішно видалено!");
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Видалення локації ~~~");
        process();
    }
}
