package uk.co.crystalmark.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class FoldingPanel extends Panel {
    private static final long serialVersionUID = 1L;

    public FoldingPanel(final String id, final IModel<String> titleModel) {
        super(id);

        // Required to generate a new html id for the accordion
        WebMarkupContainer container = new WebMarkupContainer("accordion");
        container.setOutputMarkupId(true);
        add(container);

        WebMarkupContainer colapsable = new WebMarkupContainer("colapsable");
        container.setOutputMarkupId(true);
        container.add(colapsable);

        colapsable.add(getComponent("body"));

        ExternalLink link = new ExternalLink("openclose", "#" + colapsable.getMarkupId());
        link.add(new AttributeModifier("data-parent", "#" + container.getMarkupId()));
        container.add(link);

        link.add(new Label("title", titleModel));

    }

    public abstract Component getComponent(String wicketId);

}
