package com.ty.highway.highwaysystem.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;

/**
 * Created by ${dzm} on 2015/9/16 0016.
 */

public class AddPictrueDialog extends BaseDialog implements View.OnClickListener {

    public static final int CARMERA = 0X0001;
    public static final int ALBUM = 0X0002;
    private Context context = null;
    private TextView tv_carmera = null;
    private TextView tv_album = null;
    private AddPicDialogActListener dialogListener;

    public AddPictrueDialog(Context context) {

        super(context, R.style.mDialog);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_picture_dailog);
        //CommonUtils.setDialogWidthAndHeight(context, this);
        tv_carmera = (TextView) findViewById(R.id.tv_carmera);
        tv_album = (TextView) findViewById(R.id.tv_album);
        tv_carmera.setOnClickListener(this);
        tv_album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;
        if (textView == tv_carmera) {
            dialogListener.onClickDialog(CARMERA);
            dismiss();
        } else if (textView == tv_album) {
            dialogListener.onClickDialog(ALBUM);
            dismiss();
        }

    }

    public void setOnclickListener(AddPicDialogActListener dialogListener) {

        this.dialogListener = dialogListener;
    }

    public interface AddPicDialogActListener {

        public void onClickDialog(int listenerType);
    }

    public void onClickDialog(int listenerType) {

    }

}