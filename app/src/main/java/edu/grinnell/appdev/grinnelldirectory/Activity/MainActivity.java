package edu.grinnell.appdev.grinnelldirectory.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.grinnell.appdev.grinnelldirectory.R;
import edu.grinnell.appdev.grinnelldirectory.User;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new User("test1stu", "selfserv1");

        APICaller apiCaller = new APICaller(user);
        List<String> test_list_1 = new ArrayList();
        test_list_1.add(0, "Nicholas");
        test_list_1.add(1, "Roberson");
        test_list_1.add(2, "");
        test_list_1.add(3, "");

        List<String> test_list_2 = new ArrayList();
        test_list_2.add(0, "test1stu");

        List<String> test_list_3 = new ArrayList();
        test_list_3.add(0, "Nicholas");
        test_list_3.add(1, "Roberson");
        test_list_3.add(2, "");
        test_list_3.add(3, "");
        test_list_3.add(4, "");
        test_list_3.add(5, "");
        test_list_3.add(6, "");
        test_list_3.add(7, "");
        test_list_3.add(8, "");
        test_list_3.add(9, "");
        test_list_3.add(10, "");
        test_list_3.add(11, "");
        test_list_3.add(12, "");
        test_list_3.add(13, "");
        test_list_3.add(14, "");

        apiCaller.simpleSearch(user, test_list_1);
        apiCaller.advancedSearch(user, test_list_3);
        // apiCaller.authenticateUser(user, test_list_2);
    }
}
