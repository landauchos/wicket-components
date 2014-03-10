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
 *         This is a simple implementation of the Bootstrap
 *         dismissible alert with two buttons.
 * 
 *         The content, and the button text are in models. There are
 *         two abstract methods for the button callbacks. The alert
 *         will be hidden when either of the buttons are clicked.
 * 
 *         The X at the top right of the alert will only hide the text
 *         and not call back to the java.
 * 
 * @see <a
 *      href="http://getbootstrap.com/2.3.2/javascript.html#alerts">Bootstrap
 *      Documentation</a>
 * 
 * 
 *      The MIT License
 * 
 *      Copyright (c) 2013 Tim Squires
 * 
 *      Permission is hereby granted, free of charge, to any person
 *      obtaining a copy of this software and associated documentation
 *      files (the "Software"), to deal in the Software without
 *      restriction, including without limitation the rights to use,
 *      copy, modify, merge, publish, distribute, sublicense, and/or
 *      sell copies of the Software, and to permit persons to whom the
 *      Software is furnished to do so, subject to the following
 *      conditions:
 * 
 *      The above copyright notice and this permission notice shall be
 *      included in all copies or substantial portions of the
 *      Software.
 * 
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *      KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *      WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *      PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *      COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *      OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *      SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
