package uk.co.crystalmark.wicket.components;

import java.util.Calendar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class AgeLabel extends Label {
    private static final long serialVersionUID = 1L;

    public AgeLabel(final String id, final IModel<Calendar> model) {
        super(id, new LoadableDetachableModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected String load() {
                Calendar birthday = Calendar.getInstance();
                if (model != null && model.getObject() != null) {
                    birthday.setTime(model.getObject().getTime());
                }

                Calendar today = Calendar.getInstance();
                int months = 0;
                while (today.after(birthday)) {
                    birthday.add(Calendar.MONTH, 1);
                    months++;
                }
                months--;

                StringBuffer buffer = new StringBuffer();
                if (months > 11) {
                    if (months == 12) {
                        buffer.append("1 year");
                    } else {
                        buffer.append(String.format("%d years", (months / 12)));
                    }
                }

                int m = (months % 12);
                if (m == 1) {
                    buffer.append(" 1 month");
                } else if (m > 1) {
                    buffer.append(String.format(" %d months", (months % 12)));
                }

                return buffer.toString().trim();
            }
        });
    }

}
