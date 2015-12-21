/*
 * Copyright ish group pty ltd. All rights reserved. http://www.ish.com.au No copying or use of this code is allowed without permission in writing from ish.
 */


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.*;

public class T27058 {

    private static int currentAttempt = 0;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");

   private static String[] urls = new String[]{
            "https://www.sgscc.edu.au/courses/SchoolAge?debugQuery=true",
            "https://www.sgscc.edu.au/courses/Leisure/Fashion+Sewing?debugQuery=true",
            "https://www.sgscc.edu.au/courses?s=&tag=/SchoolAge&near=&km=&price=&time=&day=&debugQuery=true"
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            for (String url : urls) {
                checkUrl(url);
                Thread.sleep(60000);
            }
        }
    }

    private static boolean checkUrl(String url) throws IOException {
        currentAttempt++;
        open(url);
        SelenideElement courseList = $("#courses-list");
        ElementsCollection h2s = $$(courseList, "h2");

        Optional<SelenideElement> noResults = h2s
                .stream()
                .filter(p -> p.innerText().equals("No results"))
                .findFirst();

        if (noResults.isPresent()) {
            Date date = new Date();
            Selenide.screenshot("NoResult " + sdf.format(date));
            PrintWriter report = new PrintWriter(new BufferedWriter(new FileWriter("report.txt", true)));
            report.println("Attempt count : " + currentAttempt);
            report.println("Failed url : " + url + " . Time : " + sdf.format(date));
            report.close();
            return true;
        }
        return false;
    }
}
