/*
 * Copyright ish group pty ltd. All rights reserved. http://www.ish.com.au No copying or use of this code is allowed without permission in writing from ish.
 */


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

import static com.codeborne.selenide.Selenide.*;

public class T27058 {

    static int currentAttempt = 0;

    private static String[] urls = new String[]{
            "https://www.sgscc.edu.au/courses/SchoolAge?debugQuery=true",
            "https://www.sgscc.edu.au/courses/Leisure/Fashion+Sewing?debugQuery=true",
            "https://www.sgscc.edu.au/courses?s=&tag=/SchoolAge&near=&km=&price=&time=&day=&debugQuery=true"
    };

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
        while (true) {
            for (String url : urls) {
                if (isFailed(url)) {
                    return;
                }
                Thread.sleep(60000);
            }
        }
    }

    private static boolean isFailed(String url) throws FileNotFoundException, UnsupportedEncodingException {
        currentAttempt++;
        open(url);
        SelenideElement courseList = $("#courses-list");
        ElementsCollection h2s = $$(courseList, "h2");

        Optional<SelenideElement> noResults = h2s
                .stream()
                .filter(p -> p.innerText().equals("No results"))
                .findFirst();

        if (noResults.isPresent()) {
            Selenide.screenshot("NoResult");
            PrintWriter report = new PrintWriter("report.txt", "UTF-8");
            report.println("Attempt count : " + currentAttempt);
            report.println("Failed url : " + url);
            report.close();
            return true;
        }
        return false;
    }
}
