package uk.co.crystalmark.wicket;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import uk.co.crystalmark.wicket.components.AlertPanel;
import uk.co.crystalmark.wicket.pages.DummyBootstrapPage;

public class TestAlertPanel {
    private WicketTester tester;

    boolean ok = false;
    boolean cancel = false;

    @Before
    public void setUp() {
        tester = new WicketTester();
    }

    @Test
    public void testRender() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.YEAR, -10);

        final AlertPanel panel = new AlertPanel("id", Model.of("Content Text"), Model.of("OK"), Model.of("Cancel")) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onOK(AjaxRequestTarget target) {
                ok = true;
            }

            @Override
            public void onCancel(AjaxRequestTarget target) {
                cancel = true;
            }

        };

        tester.startPage(new DummyBootstrapPage(new PageParameters()) {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTestComponent(String markupId) {
                return panel;
            }

            @Override
            protected void onAjaxClick(AjaxRequestTarget target) {
                panel.show(target);
            }
        });

        // Alert hidden
        tester.assertContainsNot("Content Text");
        Assert.assertFalse(ok);
        Assert.assertFalse(cancel);

        tester.executeAjaxEvent("ajaxlink", "click");

        // Alert shown
        tester.assertContains("Content Text");
        Assert.assertFalse(ok);
        Assert.assertFalse(cancel);

        // Alert still shown
        tester.executeAjaxEvent("ajaxlink", "click");
        tester.assertContains("Content Text");

        // Hide using OK button
        tester.executeAjaxEvent("id:container:ok", "click");
        Assert.assertTrue(ok);
        Assert.assertFalse(cancel);
        tester.assertContainsNot("Content Text");

        // Show again
        tester.executeAjaxEvent("ajaxlink", "click");
        tester.assertContains("Content Text");
        ok = false;

        // Close using Cancel button
        tester.executeAjaxEvent("id:container:cancel", "click");
        Assert.assertFalse(ok);
        Assert.assertTrue(cancel);
        tester.assertContainsNot("Content Text");

    }

}
