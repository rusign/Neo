package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityCirclecontactsBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.contacts.ContactsActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CircleContactsActivity extends BaseActivity<ActivityCirclecontactsBinding, CircleContactsViewModel> implements CircleContactsNavigator {

    @Inject
    CircleContactsViewModel _CircleContactsVM;
    private ActivityCirclecontactsBinding _mActivityCircleContactsBinding;
    List<CircleContactsItem> lstCircle ;
    RecyclerView myrv;
    int cicle_id;


    public static Intent newIntent(Context context) {
        return new Intent(context, CircleContactsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_circlecontacts;
    }

    @Override
    public CircleContactsViewModel getViewModel() {
        return _CircleContactsVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityCircleContactsBinding = getViewDataBinding();
        _CircleContactsVM.setNavigator(this);

        lstCircle = new ArrayList<>();

        myrv = (RecyclerView) findViewById(R.id.recyclerviewcontacts_id);
        CircleContactsAdapter myAdapter = new CircleContactsAdapter(this,lstCircle);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);
        cicle_id =getIntent().getIntExtra("id", 0);
        _CircleContactsVM.getContacts(cicle_id);
    }

    @Override
    public void setCircles(String email, String name, int user_id) {
        Log.i("setCircles ", "setCircles");
        lstCircle.add(new CircleContactsItem(name,email, user_id));

        myrv.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void displayDialogInvite() {
        Log.i("displayDialogAddCircle", "displayDialogAddCircle");
        final EditText input = new EditText(CircleContactsActivity.this);
        input.setHint("Email de l'invité");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(CircleContactsActivity.this);
        builder.setTitle("Inviter une personne au cercle")
                .setView(input)
                .setPositiveButton("Inviter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (input.getText().toString().equals(""))
                            Toast.makeText(CircleContactsActivity.this, "Echec : l'email est vide" , Toast.LENGTH_SHORT).show();
                        else
                        {
                            _CircleContactsVM.inviteToCircle(input.getText().toString(), cicle_id);
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(CircleContactsActivity.this, message , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openHome() {
        Intent intent = HomeActivity.newIntent(CircleContactsActivity.this);
        startActivity(intent);
    }
}