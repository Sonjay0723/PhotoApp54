package com.example.photoapp54;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.view.autofill.AutofillValue;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class SearchPage extends AppCompatActivity {
    private TextInputLayout personTag;
    private TextInputLayout locationTag;
    private RadioGroup andOr;
    private RadioButton radAnd;
    private RadioButton radOr;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        andOr = findViewById(R.id.grp2Tag);
        personTag = findViewById(R.id.txtPersonTag);
        locationTag = findViewById(R.id.txtLocationTag);
        radAnd = findViewById(R.id.btnAnd);
        radOr = findViewById(R.id.btnOr);
    }

    private void search() {
        String strPerson = personTag.getEditText().getText().toString().trim();
        String strLocation = locationTag.getEditText().getText().toString().trim();

        if ((radAnd.isSelected() || radOr.isSelected()) && (strPerson.isEmpty() || strLocation.isEmpty())) {

        }
    }

    private void reset() {
        personTag.getEditText().setText("");
        locationTag.getEditText().setText("");
        andOr.clearCheck();
    }
}
