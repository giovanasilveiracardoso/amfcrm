package com.amf.crm.views.list;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ListViewTest {

//    static {
//        // Prevent Vaadin Development mode to launch browser window
//        System.setProperty("vaadin.launch-browser", "false");
//    }
//
//    @Autowired
//    private ListView listView;
//
//    @Test
//    public void formShownWhenContactSelected() {
//        Grid<Client> grid = listView.grid;
//        Client firstContact = getFirstItem(grid);
//
//        ClientForm form = listView.form;
//
//        assertFalse(form.isVisible());
//        grid.asSingleSelect().setValue(firstContact);
//        assertTrue(form.isVisible());
//        assertEquals(firstContact.getFirstName(), form.firstName.getValue());
//    }
//
//    private Client getFirstItem(Grid<Client> grid) {
//        return( (ListDataProvider<Client>) grid.getDataProvider()).getItems().iterator().next();
//    }
}