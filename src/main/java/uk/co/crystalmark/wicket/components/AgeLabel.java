package uk.co.crystalmark.wicket.components;

import java.util.Calendar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A simple converter from a birthday to an age of years and months. E.g. "12 years 6 months" or "1 year 1 month"
 * 
 * @author tims
 *
 * TODO Internationalize years/month.
 * TODO Rounding to nearest month or year?
 * TODO Options to show only years or only months.  e.g. 42 years 1 month = "42 years" or "505 months"
 * TODO Include days, minutes, etc
 * TODO Auto refresh?
 * 
 * The MIT License
 *
 * Copyright (c) 2013 Tim Squires
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */
public class AgeLabel extends Label {
    private static final long serialVersionUID = 1L;

    /**
     * @param id
     *            The Wicket markup id
     * @param birthdayModel
     *            The model of the calendar representing the bithday.
     */
    public AgeLabel(final String id, final IModel<Calendar> birthdayModel) {
        super(id, new LoadableDetachableModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected String load() {
                Calendar birthday = Calendar.getInstance();
                if (birthdayModel != null && birthdayModel.getObject() != null) {
                    birthday.setTime(birthdayModel.getObject().getTime());
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
