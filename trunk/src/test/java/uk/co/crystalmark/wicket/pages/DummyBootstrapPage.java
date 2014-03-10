package uk.co.crystalmark.wicket.pages;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class DummyBootstrapPage extends WebPage {
    private static final long serialVersionUID = 1L;

    public static final String TEST_WICKET_ID = "id";

    private AjaxLink<Void> ajaxLink;

    public DummyBootstrapPage(final PageParameters parameters) {
        super(parameters);

        add(getTestComponent(TEST_WICKET_ID));

        ajaxLink = new AjaxLink<Void>("ajaxlink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                onAjaxClick(target);
            }
        };
        add(getAjaxLink());

    }

    protected void onAjaxClick(AjaxRequestTarget target) {
        // override if neccessary
    }

    public abstract Component getTestComponent(String markupId);

    public AjaxLink<Void> getAjaxLink() {
        return ajaxLink;
    }
}
