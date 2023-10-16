package com.amf.crm.views.list;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.context.annotation.Scope;

import com.amf.crm.data.entity.Loan;
import com.amf.crm.data.service.ClientService;
import com.amf.crm.data.service.LoanService;
import com.amf.crm.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import jakarta.annotation.security.PermitAll;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "loan", layout = MainLayout.class)
@PageTitle("Empréstimos | AMF CRM")
public class LoanView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
    Grid<Loan> grid = new Grid<>(Loan.class, false);
    LoanForm form;
    LoanService loanService;
    ClientService clientService;

    public LoanView(LoanService service, ClientService clientService) {
        this.loanService = service;
        this.clientService = clientService;
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
        form = new LoanForm(clientService.findAll(null));
        form.setWidth("25em");
        form.addSaveListener(this::saveLoan);
        form.addDeleteListener(this::deleteLoan);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveLoan(LoanForm.SaveEvent event) {
        loanService.saveLoan(event.getLoan());
        updateList();
        closeEditor();
    }

    private void deleteLoan(LoanForm.DeleteEvent event) {
        loanService.deleteLoan(event.getLoan());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
    	Locale locale = UI.getCurrent().getLocale();

        grid.addClassNames("loan-grid");
        grid.setSizeFull();
        grid.addColumn("client").setHeader("Cliente");
        grid.addColumn(new NumberRenderer<>(loan -> loan.getTotalAmount(), NumberFormat.getCurrencyInstance(locale))).setHeader("Valor Total");
        grid.addColumn(new NumberRenderer<>(loan -> loan.getInstallmentAmount(), NumberFormat.getCurrencyInstance(locale))).setHeader("Valor da Parcela");
        grid.addColumn(new NumberRenderer<>(loan -> loan.getAmount(), NumberFormat.getCurrencyInstance(locale))).setHeader("Valor do Empréstimo");
        NumberFormat nf = NumberFormat.getPercentInstance(locale);
        nf.setMinimumFractionDigits(2);
        grid.addColumn(new NumberRenderer<>(loan -> loan.getInterestRate() / 100, nf)).setHeader("Taxa de Juros");
        grid.addColumn("installment").setHeader("Quantidade de Parcelas");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editLoan(event.getValue()));
    }
    
    private Component getToolbar() {
        Button addLoanButton = new Button("Incluir empréstimo");
        addLoanButton.addClickListener(click -> addLoan());

        var toolbar = new HorizontalLayout(addLoanButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editLoan(Loan loan) {
        if (loan == null) {
            closeEditor();
        } else {
            form.setLoan(loan);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setLoan(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addLoan() {
        grid.asSingleSelect().clear();
        editLoan(new Loan());
    }

    private void updateList() {
        grid.setItems(loanService.findAll());
    }
}