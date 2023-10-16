package com.amf.crm.views.list;

import org.springframework.context.annotation.Scope;

import com.amf.crm.data.entity.Client;
import com.amf.crm.data.service.ClientService;
import com.amf.crm.utils.UtilFormat;
import com.amf.crm.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import jakarta.annotation.security.PermitAll;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Clientes | AMF CRM")
public class ClientView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
    Grid<Client> grid = new Grid<>(Client.class, false);
    TextField filterText = new TextField();
    ClientForm form;
    ClientService service;

    public ClientView(ClientService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ClientForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveClient);
        form.addDeleteListener(this::deleteClient);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveClient(ClientForm.SaveEvent event) {
        service.saveClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void deleteClient(ClientForm.DeleteEvent event) {
        service.deleteClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("client-grid");
        grid.setSizeFull();
        grid.addColumn("name").setHeader("Nome");
        grid.addColumn(new TextRenderer<>(client -> UtilFormat.formatString(client.getCpf(), "###.###.###-##"))).setHeader("CPF");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editClient(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filtro por nome ou CPF");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addClientButton = new Button("Incluir cliente");
        addClientButton.addClickListener(click -> addClient());

        var toolbar = new HorizontalLayout(filterText, addClientButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editClient(Client client) {
        if (client == null) {
            closeEditor();
        } else {
            form.setClient(client);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setClient(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addClient() {
        grid.asSingleSelect().clear();
        editClient(new Client());
    }


    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
}