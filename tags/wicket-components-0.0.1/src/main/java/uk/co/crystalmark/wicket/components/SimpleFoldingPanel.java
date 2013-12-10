package uk.co.crystalmark.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class SimpleFoldingPanel extends Panel {
    private static final long serialVersionUID = 1L;

    public SimpleFoldingPanel(final String id, final IModel<String> titleModel) {
        super(id);

        WebMarkupContainer colapsable = new WebMarkupContainer("colapsable");
        colapsable.setOutputMarkupId(true);
        add(colapsable);

        colapsable.add(getComponent("body"));

        Button link = new Button("openclose");
        link.add(new AttributeModifier("data-target", "#" + colapsable.getMarkupId()));
        add(link);

        link.add(new Label("title", titleModel));

    }

    public abstract Component getComponent(String wicketId);

}
