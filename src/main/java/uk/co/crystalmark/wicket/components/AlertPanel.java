package uk.co.crystalmark.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author tims
 * 
 *         This is a simple implementation of the Bootstrap dismissible alert with two buttons.
 * 
 *         The content, and the button text are in models. There are two abstract methods for the button callbacks. The alert will be hidden when either of the buttons are clicked.
 * 
 *         The X at the top right of the alert will only hide the text and not call back to the java.
 * 
 * @see <a href="http://getbootstrap.com/2.3.2/javascript.html#alerts">Bootstrap Documentation</a>
 * 
 */
public abstract class AlertPanel extends Panel {
    private static final long serialVersionUID = 1L;

    private boolean visible = false;

    /**
     * @param id
     *            The wicket id
     * @param text
     *            The text to display when visible
     * @param okLabelString
     *            The Text to display on the OK button
     * @param cancelLabelString
     *            The Text to display on the cancel button
     */
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
