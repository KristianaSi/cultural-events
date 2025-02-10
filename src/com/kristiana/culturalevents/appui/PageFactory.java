package com.kristiana.culturalevents.appui;

import com.kristiana.culturalevents.appui.forms.CommentAddForm;
import com.kristiana.culturalevents.appui.forms.CommentDeleteForm;
import com.kristiana.culturalevents.appui.forms.CommentUpdateForm;
import com.kristiana.culturalevents.appui.forms.EventAddForm;
import com.kristiana.culturalevents.appui.forms.EventDeleteForm;
import com.kristiana.culturalevents.appui.forms.EventUpdateForm;
import com.kristiana.culturalevents.appui.forms.LocationAddForm;
import com.kristiana.culturalevents.appui.forms.LocationDeleteForm;
import com.kristiana.culturalevents.appui.forms.LocationUpdateForm;
import com.kristiana.culturalevents.appui.pages.AuthView;
import com.kristiana.culturalevents.appui.pages.CommentView;
import com.kristiana.culturalevents.appui.pages.EventView;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;

public class PageFactory {

    private static PageFactory instance;
    private final ServiceFactory serviceFactory;

    public PageFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public static PageFactory getInstance(ServiceFactory serviceFactory) {
        if (instance == null) {
            instance = new PageFactory(serviceFactory);
        }
        return instance;
    }

    public Renderable createAuthView() {
        return new AuthView(serviceFactory);
    }

    public Renderable createCommentView() {
        return new CommentView(serviceFactory);
    }

    public Renderable createEventView() {
        return new EventView(serviceFactory);
    }

    public Renderable createCommentAddForm() {
        return new CommentAddForm(serviceFactory);
    }

    public Renderable createCommentUpdateForm() {
        return new CommentUpdateForm(serviceFactory);
    }

    public Renderable createCommentDeleteForm() {
        return new CommentDeleteForm(serviceFactory);
    }

    public Renderable createEventAddForm() {
        return new EventAddForm(serviceFactory);
    }

    public Renderable createLocationAddForm() {
        return new LocationAddForm(serviceFactory);
    }

    public Renderable createDeleteEventForm() {
        return new EventDeleteForm(serviceFactory);
    }

    public Renderable createDeleteLocationForm() {
        return new LocationDeleteForm(serviceFactory);
    }

    public Renderable createEventUpdateForm() {
        return new EventUpdateForm(serviceFactory);
    }

    public Renderable createLocationUpdateForm() {
        return new LocationUpdateForm(serviceFactory);
    }
}
