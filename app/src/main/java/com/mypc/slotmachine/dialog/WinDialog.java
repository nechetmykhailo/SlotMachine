package com.mypc.slotmachine.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mypc.slotmachine.R;
import com.mypc.slotmachine.singleton.Win;

public class WinDialog extends DialogFragment {

    private TextView tvWin;
    private UpdateWin updateWin;

    public WinDialog(){
    }

    public interface UpdateWin {
        void onDismiss();
    }

    public void setUpdateWin(UpdateWin updateWin) {
        this.updateWin = updateWin;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.win_dialog, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setView(view);

        tvWin = view.findViewById(R.id.tvWin);

        tvWin.setText(String.valueOf(Win.getInstance().getCounter()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);

        Dialog dialog = adb.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }



}
