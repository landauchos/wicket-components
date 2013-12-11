package uk.co.crystalmark.wicket;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import uk.co.crystalmark.wicket.components.AgeLabel;

public class TestAgeLabel {
    @SuppressWarnings("unused")
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
    }

    @Test
    public void testYears() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        AgeLabel ageLabel = new AgeLabel("id", Model.of(calendar));

        CharSequence markup = ComponentRenderer.renderComponent(ageLabel);

        Assert.assertEquals("10 years", removeTags(markup.toString()));

    }

    @Test
    public void testMonths() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, -10);
        AgeLabel ageLabel = new AgeLabel("id", Model.of(calendar));

        CharSequence markup = ComponentRenderer.renderComponent(ageLabel);

        Assert.assertEquals("10 months", removeTags(markup.toString()));

    }

    private String removeTags(String markup) {
        return markup.replaceAll("(?i)<wicket:container[^>]*>", "").replaceAll("</wicket:container>", "");
    }

    @Test
    public void testMonthsAndYears() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.YEAR, -20);
        calendar.add(Calendar.MONTH, -10);
        AgeLabel ageLabel = new AgeLabel("id", Model.of(calendar));

        CharSequence markup = ComponentRenderer.renderComponent(ageLabel);

        Assert.assertEquals("20 years 10 months", removeTags(markup.toString()));

    }
}
