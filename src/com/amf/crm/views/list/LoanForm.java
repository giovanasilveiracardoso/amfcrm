package com.amf.crm.views.list;

import java.util.List;

import com.amf.crm.data.entity.Client;
import com.amf.crm.data.entity.Loan;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class LoanForm extends FormLayout {
	private static final long serialVersionUID = 1L;

	NumberField amount = new NumberField("Valor do Empréstimo");
	NumberField interestRate = new NumberField("Taxa de Juros");
	IntegerField installment = new IntegerField("Quantidade de Parcelas");
	ComboBox<Client> client = new ComboBox<>("Clientes");
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");

	Binder<Loan> binder = new BeanValidationBinder<>(Loan.class);

	public LoanForm(List<Client> clients) {
		addClassName("loan-form");
		binder.bindInstanceFields(this);
		
		Div realPrefix = new Div();
		realPrefix.setText("R$");
		amount.setPrefixComponent(realPrefix);

		Div percentSuffix = new Div();
		percentSuffix.setText("%");
		interestRate.setSuffixComponent(percentSuffix);
		
		client.setItems(clients);
		client.setItemLabelGenerator(Client::getName);

		add(client, amount, interestRate, installment, createButtonsLayout());
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

	public void setLoan(Loan loan) {
		binder.setBean(loan);
	}

	public static abstract class LoanFormEvent extends ComponentEvent<LoanForm> {
		private static final long serialVersionUID = 1L;
		private Loan loan;

		protected LoanFormEvent(LoanForm source, Loan loan) {
			super(source, false);
			this.loan = loan;
		}

		public Loan getLoan() {
			return loan;
		}
	}

	public static class SaveEvent extends LoanFormEvent {
		private static final long serialVersionUID = 1L;

		SaveEvent(LoanForm source, Loan loan) {
			super(source, loan);
		}
	}

	public static class DeleteEvent extends LoanFormEvent {
		private static final long serialVersionUID = 1L;

		DeleteEvent(LoanForm source, Loan loan) {
			super(source, loan);
		}

	}

	public static class CloseEvent extends LoanFormEvent {
		private static final long serialVersionUID = 1L;

		CloseEvent(LoanForm source) {
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
