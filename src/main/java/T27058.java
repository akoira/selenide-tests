/*
 * Copyright ish group pty ltd. All rights reserved. http://www.ish.com.au No copying or use of this code is allowed without permission in writing from ish.
 */


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class T27058 {

    private static String[] urls = new String[]{

    }

    public static void main(String[] args) {
        boolean found = false;
        while (!found) {
            open("https://www.sgscc.edu.au/courses/SchoolAge?debugQuery=true");
            SelenideElement courseList = $("#courses-list");
            ElementsCollection h2s = $$(courseList, "h2");
            SelenideElement noResults = h2s.stream().filter(p -> p.innerText().equals("No results")).findFirst().get();
            System.out.println(noResults);


//            System.out.println(courses);
//            if (courses.size() < 1) {
//                Selenide.screenshot("NoResult");
//                found = true;
//            }
        }
    }
}
