package uk.co.crystalmark.wicket.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class BootstrapTimePickerPanel extends Panel implements IHeaderContributor {
    private static final long serialVersionUID = 1L;

    public BootstrapTimePickerPanel(final String id, final IModel<Integer> hourModel, final IModel<Integer> minuteModel) {
        super(id, hourModel);

        final IModel<String> timeModel = new IModel<String>() {
            private static final long serialVersionUID = 1L;

            private String time;

            @Override
            public void detach() {
                time = null;
            }

            @Override
            public String getObject() {
                if (time == null) {
                    setTime();
                }
                return time;
            }

            private void setTime() {
                time = String.format("%02d", hourModel.getObject()) + ":" + String.format("%02d", minuteModel.getObject());
            }

            @Override
            public void setObject(final String time) {
                String[] hourmin = time.split(":");
                hourModel.setObject(Integer.parseInt(hourmin[0]));
                minuteModel.setObject(Integer.parseInt(hourmin[1]));
            }
        };

        final WebMarkupContainer timepicker = new WebMarkupContainer("timepicker");
        add(timepicker);

        timepicker.add(new TextField<String>("time", timeModel));

        timepicker.add(new Behavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void renderHead(final Component component, final IHeaderResponse response) {
                response.render(OnDomReadyHeaderItem.forScript("    $('#" + timepicker.getMarkupId() + "').datetimepicker({\n" + "      pickDate: false\n" + "    });"));
            }

        });
    }

    @Override
    public void renderHead(final IHeaderResponse response) {

        response.render(JavaScriptHeaderItem.forReference(Bootstrap.responsive()));

        response.render(CssHeaderItem.forReference(new CssResourceReference(BootstrapTimePickerPanel.class, "css/bootstrap-timepicker.css")));
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(BootstrapTimePickerPanel.class, "js/bootstrap-timepicker.js")));
    }
}
