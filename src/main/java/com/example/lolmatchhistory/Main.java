package com.example.lolmatchhistory;

import junit.framework.JUnit4TestAdapter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UnitTests.class
})

public class Main {

    public static void main(String[] args) {
//        junit.textui.TestRunner.run(suite());
        System.out.println("In main");
        Menu menu = new Menu();
        menu.mainMenu();
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter(Main.class);
    }
}
