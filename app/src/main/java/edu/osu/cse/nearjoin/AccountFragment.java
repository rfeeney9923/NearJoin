package edu.osu.cse.nearjoin;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirm;
    private DatabaseHelper dh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.account_fragment, container, false);
        etUsername= (EditText)v.findViewById(R.id.username);
        etPassword= (EditText)v.findViewById(R.id.password);
        etConfirm = (EditText)v.findViewById(R.id.password_confirm);
        View btnAdd= (Button)v.findViewById(R.id.done_button);
        btnAdd.setOnClickListener(this);
        View btnCancel= (Button)v.findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(this);
        return v;
    }

    private void CreateAccount(){
        //this.output = (TextView) this.findViewById(R.id.out_text);
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirm	= etConfirm.getText().toString();
        if ((password.equals(confirm))&&(!username.equals(""))&&(!password.equals(""))&&(!confirm.equals(""))){
            this.dh = new DatabaseHelper(this.getActivity());
            this.dh.insert(username, password);
            Toast.makeText(this.getActivity(), "new record inserted",Toast.LENGTH_SHORT).show();
        }
        else if((username.equals(""))||(password.equals(""))||(confirm.equals(""))){
            Toast.makeText(AccountFragment.this.getActivity(), "Missing entry", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirm)){
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("Error")
                    .setMessage("passwords do not match")
                    .setNeutralButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    })

                    .show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_button:
                CreateAccount();
                break;
            case R.id.cancel_button:
                etUsername.setText("");
                etPassword.setText("");
                etConfirm.setText("");
                break;
        }
    }
}