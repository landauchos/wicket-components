package uk.co.crystalmark.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;

public abstract class BootstrapPagingNavigator extends Panel {

    private static final long serialVersionUID = 1L;

    public static final String NAVIGATION_ID = "navigation";

    private static final int VIEW_SIZE = 5;

    private PagingNavigation pagingNavigation;
    private final DataView<?> dataView;
    private WebMarkupContainer pagingLinksContainer;

    public BootstrapPagingNavigator(final String id, final DataView<?> dataView) {
        super(id);
        this.dataView = dataView;

        addContainerWithPagingLinks();
    }

    @Override
    public boolean isVisible() {
        return dataView != null && dataView.getItemCount() > 0;
    }

    private void addContainerWithPagingLinks() {

        pagingLinksContainer = new WebMarkupContainer("pagingLinksContainer") {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible() {
                return dataView.getPageCount() > 0;
            }
        };

        pagingNavigation = newNavigation(dataView);
        pagingLinksContainer.add(pagingNavigation);

        // Add additional page links
        pagingLinksContainer.add(newPagingNavigationLink("first", dataView, 0));
        pagingLinksContainer.add(newPagingNavigationIncrementLink("prev", dataView, -1));
        pagingLinksContainer.add(newPagingNavigationIncrementLink("next", dataView, 1));
        pagingLinksContainer.add(newPagingNavigationLink("last", dataView, -1));

        add(pagingLinksContainer);
    }

    protected PagingNavigation newNavigation(final IPageable pageable) {
        return new AjaxPagingNavigation(NAVIGATION_ID, pageable) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final LoopItem loopItem) {
                // Get the index of page this link shall point to
                final long pageIndex = getStartIndex() + loopItem.getIndex();

                // Add a page link pointing to the page
                final AjaxPagingNavigationLink link = new AjaxPagingNavigationLink("pageLink", pageable, pageIndex) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        super.onClick(target);
                        BootstrapPagingNavigator.this.onClick(target);
                    }

                };
                link.setAutoEnable(false);
                loopItem.add(link);

                // Add a page number label to the list which is enclosed by the link
                String label = "";
                if (labelProvider != null) {
                    label = labelProvider.getPageLabel(pageIndex);
                } else {
                    label = String.valueOf(pageIndex + 1);
                }
                link.add(new Label("pageNumber", label));
                long index = pageable.getCurrentPage();
                if (index == pageIndex) {
                    loopItem.add(new AttributeAppender("class", "active"));
                }
            }

            @Override
            public int getViewSize() {
                return VIEW_SIZE;
            }

        };
    }

    protected AbstractLink newPagingNavigationIncrementLink(final String id, final IPageable pageable, final int increment) {
        return new AjaxPagingNavigationIncrementLink(id, pageable, increment) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                super.onClick(target);
                BootstrapPagingNavigator.this.onClick(target);
            }

        }.setAutoEnable(false);
    }

    protected AbstractLink newPagingNavigationLink(final String id, final IPageable pageable, final int pageNumber) {
        return new AjaxPagingNavigationIncrementLink(id, pageable, pageNumber) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                super.onClick(target);
                BootstrapPagingNavigator.this.onClick(target);
            }

        }.setAutoEnable(false);
    }

    public final PagingNavigation getPagingNavigation() {
        return pagingNavigation;
    }

    public abstract void onClick(AjaxRequestTarget target);
}