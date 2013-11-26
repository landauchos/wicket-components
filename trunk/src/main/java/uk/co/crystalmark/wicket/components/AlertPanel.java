package uk.co.crystalmark.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class AlertPanel extends Panel {
    private static final long serialVersionUID = 1L;

    private boolean visible = false;

    public AlertPanel(final String id, final IModel<String> text, final IModel<String> okLabelString, final IModel<String> cancelLabelString) {
        super(id);

        setOutputMarkupId(true);

        final WebMarkupContainer container = new WebMarkupContainer("container") {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible() {
                return visible;
            }

        };

        container.setOutputMarkupId(true);
        add(container);

        container.add(new MultiLineLabel("text", text));

        AjaxLink<Void> okLink = new AjaxLink<Void>("ok") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                onOK(target);
            }
        };
        container.add(okLink);
        okLink.setOutputMarkupId(true);
        okLink.add(new Label("okLabel", okLabelString));

        AjaxLink<Void> cancelLink = new AjaxLink<Void>("cancel") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                visible = false;
                target.add(AlertPanel.this);
                onCancel(target);
            }
        };
        container.add(cancelLink);
        cancelLink.setOutputMarkupId(true);
        cancelLink.add(new Label("cancelLabel", cancelLabelString));

        cancelLink.add(new AttributeAppender("data-dismiss", container.getMarkupId()));
    }

    public void show(final AjaxRequestTarget target) {
        visible = true;
        target.add(this);
    }

    public abstract void onOK(AjaxRequestTarget target);

    public abstract void onCancel(AjaxRequestTarget target);

}
