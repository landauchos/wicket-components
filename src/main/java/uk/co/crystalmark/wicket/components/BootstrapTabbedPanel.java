package uk.co.crystalmark.wicket.components;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class BootstrapTabbedPanel<T extends ITab> extends Panel {
    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(BootstrapTabbedPanel.class);

    private final IModel<List<T>> tabs;

    public BootstrapTabbedPanel(final String id, final IModel<List<T>> tabs) {
        super(id);

        this.tabs = tabs;
    }

    @Override
    public void onBeforeRender() {
        super.onBeforeRender();
        if (!hasBeenRendered()) {

            final IModel<T> selectedTabModel = new LoadableDetachableModel<T>() {
                private static final long serialVersionUID = 1L;

                @Override
                protected T load() {
                    StringValue parameter = getPage().getPageParameters().get("tab");
                    if (parameter != null && !parameter.isEmpty()) {
                        for (T tab : tabs.getObject()) {
                            if (tab.getTitle().getObject().equalsIgnoreCase(parameter.toString())) {
                                return tab;
                            }
                        }

                    }
                    return tabs.getObject().get(0);
                }
            };

            setDefaultModel(new PropertyModel<String>(this, "selectedTitle"));

            final WebMarkupContainer contentPanel = new WebMarkupContainer("container");
            contentPanel.setOutputMarkupId(true);
            add(contentPanel);

            contentPanel.add(selectedTabModel.getObject().getPanel("content"));

            add(contentPanel);

            final WebMarkupContainer tabsContainer = new WebMarkupContainer("tabsContainer");
            tabsContainer.setOutputMarkupId(true);
            add(tabsContainer);

            tabsContainer.add(new ListView<T>("tabs", tabs) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<T> item) {

                    final String title = item.getModelObject().getTitle().getObject();

                    item.add(new AttributeAppender("class", new LoadableDetachableModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        protected String load() {
                            return item.getModelObject().equals(selectedTabModel.getObject()) ? "active" : "";
                        }
                    }));

                    log.debug("Displaying tab for " + title);

                    PageParameters parameters = new PageParameters(getPage().getPageParameters());
                    parameters.set("tab", title);

                    Link<Void> link = new BookmarkablePageLink<Void>("link", getPage().getClass(), parameters);
                    item.add(link);

                    Label label = new Label("title", item.getModelObject().getTitle());
                    link.add(label);

                }

            });

        }
    }
}
