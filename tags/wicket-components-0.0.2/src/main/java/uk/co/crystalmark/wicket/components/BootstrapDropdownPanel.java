package uk.co.crystalmark.wicket.components;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ILabelProvider;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.util.ListModel;

public abstract class BootstrapDropdownPanel<T> extends Panel implements ILabelProvider<T> {
    private static final long serialVersionUID = 1L;

    private IModel<T> model;

    private IModel<List<T>> availableList;

    private IChoiceRenderer<T> renderer;

    public BootstrapDropdownPanel(final String id, final IModel<T> model, final IModel<List<T>> availableList) {
        super(id, model);
        this.model = model;
        this.availableList = availableList;
    }

    public BootstrapDropdownPanel(final String id, final IModel<T> model, final List<T> availableList) {
        this(id, model, new ListModel<T>(availableList));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<String> labelModel = new LoadableDetachableModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected String load() {
                if (model.getObject() == null) {
                    return "Please select";
                }
                if (renderer == null) {
                    return model.getObject().toString();
                } else {
                    return renderer.getDisplayValue(model.getObject()) + "";
                }
            }
        };
        add(new Label("triggerLabel", labelModel));

        add(new ListView<T>("options", availableList) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<T> item) {
                AjaxLink<Void> link = new AjaxLink<Void>("link") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        model.setObject(item.getModelObject());
                        onChange(target, item.getModelObject());
                    }
                };
                item.add(link);

                IModel<String> labelModel = new LoadableDetachableModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected String load() {
                        if (item.getModelObject() == null) {
                            return null;
                        }
                        if (renderer == null) {
                            return item.getModelObject().toString();
                        } else {
                            return renderer.getDisplayValue(item.getModelObject()) + "";
                        }
                    }
                };

                link.add(new Label("label", labelModel));
            }

        });
    }

    public BootstrapDropdownPanel<T> setRenderer(final IChoiceRenderer<T> renderer) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public IModel<T> getLabel() {
        return model;
    }

    public abstract void onChange(AjaxRequestTarget target, T changedTo);
}
