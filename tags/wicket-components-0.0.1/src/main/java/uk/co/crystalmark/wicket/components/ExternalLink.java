package uk.co.crystalmark.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ExternalLink extends Panel {
    private static final long serialVersionUID = 1L;

    public ExternalLink(final String id, final IModel<String> textModel, final IModel<String> urlModel) {
        super(id);

        WebMarkupContainer link = new WebMarkupContainer("link");
        link.add(new AttributeModifier("href", urlModel));
        add(link);

        Label label = new Label("label", urlModel);
        link.add(label);

    }

}
