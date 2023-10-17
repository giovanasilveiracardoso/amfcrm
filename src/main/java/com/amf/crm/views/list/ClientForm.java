package com.amf.crm.views.list;

import com.amf.crm.data.entity.Client;
import com.amf.crm.utils.ValidateCPF;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.shared.Registration;

public class ClientForm extends FormLayout {
	private static final long serialVersionUID = 1L;

	TextField name = new TextField("Nome");
	TextField cpf = new TextField("CPF");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");

	Binder<Client> binder = new BeanValidationBinder<>(Client.class);

	public ClientForm() {
		addClassName("client-form");
		binder.bindInstanceFields(this);

		cpf.setPlaceholder("99999999999");
		cpf.setErrorMessage("CPF Inválido");
		cpf.setMaxLength(11);
		cpf.setAllowedCharPattern("[0-9]");
		binder.forField(cpf)
	      .withValidator(value -> {
	    	  cpf.removeClassName("warn");
	         return ValidateCPF.isCPF(value);
	      }, "CPF Inválido", ErrorLevel.ERROR)
	      .bind(Client::getCpf, Client::setCpf);
		add(name, cpf, createButtonsLayout());
	}

	private Component createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		save.addClickShortcut(Key.ENTER);
		close.addClickShortcut(Key.ESCAPE);

		save.addClickListener(event -> validateAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
		close.addClickListener(event -> fireEvent(new CloseEvent(this)));

		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
		return new HorizontalLayout(save, delete, close);
	}

	private void validateAndSave() {
		if (binder.isValid()) {
			fireEvent(new SaveEvent(this, binder.getBean()));
		}
	}

	public void setClient(Client client) {
		binder.setBean(client);
	}

	public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
		private static final long serialVersionUID = 1L;
		private Client client;

		protected ClientFormEvent(ClientForm source, Client client) {
			super(source, false);
			this.client = client;
		}

		public Client getClient() {
			return client;
		}
	}

	public static class SaveEvent extends ClientFormEvent {
		private static final long serialVersionUID = 1L;

		SaveEvent(ClientForm source, Client client) {
			super(source, client);
		}
	}

	public static class DeleteEvent extends ClientFormEvent {
		private static final long serialVersionUID = 1L;

		DeleteEvent(ClientForm source, Client client) {
			super(source, client);
		}

	}

	public static class CloseEvent extends ClientFormEvent {
		private static final long serialVersionUID = 1L;

		CloseEvent(ClientForm source) {
			super(source, null);
		}
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return addListener(DeleteEvent.class, listener);
	}

	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
		return addListener(SaveEvent.class, listener);
	}

	public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
		return addListener(CloseEvent.class, listener);
	}

}
