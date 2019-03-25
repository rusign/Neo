package com.mindorks.framework.mvvm.ui.contacts.circleinvitation;

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
import com.mindorks.framework.mvvm.databinding.ActivityCircleinvitationBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CircleInvitationActivity extends BaseActivity<ActivityCircleinvitationBinding, CircleInvitationViewModel> implements CircleInvitationNavigator {

    @Inject
    CircleInvitationViewModel _CircleInvitationVM;
    private ActivityCircleinvitationBinding _mActivityCircleInvitationBinding;
    List<CircleInvitationItem> lstCircle ;
    RecyclerView myrv;
    public static Intent newIntent(Context context) {
        return new Intent(context, CircleInvitationActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_circleinvitation;
    }

    @Override
    public CircleInvitationViewModel getViewModel() {
        return _CircleInvitationVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityCircleInvitationBinding = getViewDataBinding();
        _CircleInvitationVM.setNavigator(this);

        lstCircle = new ArrayList<>();

        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        CircleInvitationAdapter myAdapter = new CircleInvitationAdapter(this,lstCircle);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);
        myAdapter.activity = CircleInvitationActivity.this;
        _CircleInvitationVM.getCircles();
    }




    @Override
    public void setCircles(String name, int id) {
        Log.i("setCircles ", "setCircles");
        lstCircle.add(new CircleInvitationItem(name,"cercle neo", id));

        myrv.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void displayDialogAddCircle() {
        Log.i("displayDialogAddCircle", "displayDialogAddCircle");
        final EditText input = new EditText(CircleInvitationActivity.this);
        input.setHint("Nom du cercle");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(CircleInvitationActivity.this);
        builder.setTitle("Nouveaux Cercle")
                .setView(input)
                .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (input.getText().toString().equals(""))
                            Toast.makeText(CircleInvitationActivity.this, "Echec : le nom de vide" , Toast.LENGTH_SHORT).show();
                        else
                        {
                            _CircleInvitationVM.createCircle(input.getText().toString());
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(CircleInvitationActivity.this, message , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openHome() {
        Intent intent = HomeActivity.newIntent(CircleInvitationActivity.this);
        startActivity(intent);
    }

    @Override
    public void openInvitation() {
        Intent intent = HomeActivity.newIntent(CircleInvitationActivity.this);
        startActivity(intent);
    }
}
