package edu.grinnell.appdev.grinnelldirectory.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.grinnell.appdev.grinnelldirectory.DBScraperCaller;
import edu.grinnell.appdev.grinnelldirectory.R;
import edu.grinnell.appdev.grinnelldirectory.activities.SearchResultsActivity;
import edu.grinnell.appdev.grinnelldirectory.interfaces.APICallerInterface;
import edu.grinnell.appdev.grinnelldirectory.interfaces.NetworkAPI;
import edu.grinnell.appdev.grinnelldirectory.models.Person;
import edu.grinnell.appdev.grinnelldirectory.models.SimpleResult;

import static edu.grinnell.appdev.grinnelldirectory.R.array.facultydeptarray;
import static edu.grinnell.appdev.grinnelldirectory.R.array.studentmajorarray;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.CAMPUS_ADDRESS_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.CAMPUS_PHONE_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.CLASS_YEAR_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.CONCENTRATION_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.FAC_STAFF_OFFICE_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.FIRST_NAME_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.HIATUS_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.HOME_ADDRESS_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.LAST_NAME_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.MAJOR_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.SGA_FIELD;
import static edu.grinnell.appdev.grinnelldirectory.constants.searchConstansts.USERNAME_FIELD;


public class AdvancedSearchFragment extends Fragment implements Serializable, APICallerInterface {

    private View view;
    //binding the search parameters from layout
    @BindView(R.id.first_text) TextView firstNameText;
    @BindView(R.id.last_text) TextView lastNameText;
    @BindView(R.id.username_text) TextView usernameText;
    @BindView(R.id.phone_text) TextView phoneText;
    @BindView(R.id.campus_address_text) TextView campusAddressText;
    @BindView(R.id.home_address_text) TextView homeAddressText;
    @BindView(R.id.fac_dept_spinner) Spinner facDeptSpinner;
    @BindView(R.id.student_major_spinner) Spinner studentMajorSpinner;
    @BindView(R.id.concentration_spinner) Spinner concentrationSpinner;
    @BindView(R.id.sga_spinner) Spinner sgaSpinner;
    @BindView(R.id.hiatus_spinner) Spinner hiatusSpinner;
    @BindView(R.id.student_class_spinner) Spinner studentClassSpinner;
    @BindView(R.id.search_button) Button submitButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.advanced_search_fragment, null);
        ButterKnife.bind(this, view);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        return view;
    }

    public void customSpinner (List<String> list, Spinner spinner) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> classYears = Arrays.asList(getResources().getStringArray(R.array.classyeararray));
        List<String> facDept = Arrays.asList(getResources().getStringArray(R.array.facultydeptarray));
        List<String> studMajor = Arrays.asList(getResources().getStringArray(R.array.studentmajorarray));
        List<String> studConc = Arrays.asList(getResources().getStringArray(R.array.studentconcentrationarray));
        List<String> sgaPos = Arrays.asList(getResources().getStringArray(R.array.sgaarray));
        List<String> hiatusStat = Arrays.asList(getResources().getStringArray(R.array.hiatusarray));
        customSpinner(classYears, studentClassSpinner);
        customSpinner(facDept, facDeptSpinner);
        customSpinner(studMajor, studentMajorSpinner);
        customSpinner(studConc,concentrationSpinner);
        customSpinner(sgaPos,sgaSpinner);
        customSpinner(hiatusStat,hiatusSpinner);
    }

    void search() {

        NetworkAPI api = new DBScraperCaller(getContext(), this);

        //populating searchObject with parameters
        List<String> searchObject = new ArrayList(14);
        searchObject.add(FIRST_NAME_FIELD, firstNameText.getText().toString().trim());
        searchObject.add(LAST_NAME_FIELD, lastNameText.getText().toString());
        getSpinnerWord(searchObject, studentMajorSpinner, MAJOR_FIELD);
        getSpinnerWord(searchObject, studentClassSpinner, CLASS_YEAR_FIELD);
        getSpinnerWord(searchObject, concentrationSpinner, CONCENTRATION_FIELD);
        getSpinnerWord(searchObject, sgaSpinner, SGA_FIELD);
        searchObject.add(USERNAME_FIELD, usernameText.getText().toString());
        searchObject.add(CAMPUS_PHONE_FIELD, phoneText.getText().toString());
        getSpinnerWord(searchObject, hiatusSpinner, HIATUS_FIELD);
        searchObject.add(HOME_ADDRESS_FIELD, homeAddressText.getText().toString());
        getSpinnerWord(searchObject, facDeptSpinner, FAC_STAFF_OFFICE_FIELD);
        searchObject.add(CAMPUS_ADDRESS_FIELD, campusAddressText.getText().toString());

        api.advancedSearch(searchObject);
    }

    public void getSpinnerWord (List<String> searchObject, Spinner spin, int fieldNumber) {
        if (spin.getSelectedItemPosition() == 0)
            searchObject.add(fieldNumber, "");
        else
            searchObject.add(fieldNumber, spin.getSelectedItem().toString());
    }

    /**
     * Bundle people and move to SearchResults Activity if search successful
     *
     * @param people List of person models
     */
    @Override
    public void onSearchSuccess(List<Person> people) {
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SimpleResult.SIMPLE_KEY, new SimpleResult(people));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void authenticateUserCallSuccess(boolean success, Person person) {
        // Intentionally left blank
        // The api should never call an authentication callback after a search is requested
    }

    /**
     * Show an error message if the server returns an error
     *
     * @param failMessage error description
     */
    @BindString(R.string.server_failure)
    String serverFailure;

    @Override
    public void onServerFailure(String failMessage) {
        showAlert(serverFailure, failMessage);
    }

    /**
     * Show an error message if the network has an error
     *
     * @param failMessage error description
     */
    @BindString(R.string.networking_error)
    String networkingError;

    @Override
    public void onNetworkingError(String failMessage) {
        showAlert(networkingError, failMessage);
    }

    private void showAlert(String label, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(label + ": " + message);
        builder.show();
    }
}
