package views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yossibarel.drummap.DrumMapJni;
import com.yossibarel.drummap.R;

import java.io.File;

import Utils.TimeUtils;

/**
 * Created by yossibarel on 17/04/16.
 */
public class GlobalRecordDialog  extends Dialog implements DialogInterface.OnDismissListener {

    private final int mChannel;
    private final FileDialog.IOnFileSelected mListener;
    private Context mContext;
    private final DrumMapJni mDrumMap;
    private State mState;
    private Thread mPositonThread;
    private Runnable mPositionRunnable;
    String mFilePath;
    private boolean mIsAlive;
    private File mFileRecord;

    @Override
    public void onDismiss(DialogInterface dialog) {
        mIsAlive = false;
        mDrumMap.resetRecord();
        mPositonThread.interrupt();
    }

    enum State {NONE, RECORD, PLAY}

    private File mCurrentDir;

    public GlobalRecordDialog(Context context, DrumMapJni drumMapJni, int channel, FileDialog.IOnFileSelected listener) {
        super(context);
        mIsAlive = true;
        mContext = context;
        mDrumMap = drumMapJni;
        mChannel = channel;
        mListener = listener;

        mCurrentDir = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_MUSIC + "/SynthPro/");
        if (!mCurrentDir.exists())
            mCurrentDir.mkdir();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global_record);
        final EditText tvFileName = (EditText) findViewById(R.id.etFileName);
        final TextView tvPosition = (TextView) findViewById(R.id.tvPosition);
        mState = State.NONE;
        mFileRecord = null;
        mFilePath = null;
        //final Button btnRecord = (Button) findViewById(R.id.btnRecord);
        final Button btnSelect = (Button) findViewById(R.id.btnSelect);
        final Button btnCancle = (Button) findViewById(R.id.btnCancle);
        final Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setEnabled(true);
        btnSelect.setEnabled(true);
        tvFileName.setText(TimeUtils.getFileTimeString());
        /*btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mState == State.NONE) {
                    mFilePath = mCurrentDir + "/" + tvFileName.getText() + ".wav";
                    mDrumMap.startRecord(mFilePath);
                    btnPlay.setEnabled(false);
                    btnRecord.setText("Stop");
                    tvFileName.setEnabled(false);
                    mState = State.RECORD;
                    btnSelect.setEnabled(false);
                } else if (mState == State.RECORD) {
                    mDrumMap.stopRecord();
                    btnPlay.setEnabled(true);
                    btnRecord.setText("Rec");
                    mFileRecord = new File(mFilePath);
                    tvFileName.setEnabled(true);
                    btnSelect.setEnabled(true);
                    mState = State.NONE;
                }
            }
        });*/
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mState == State.NONE) {
                    if (mDrumMap.startPlayRecord()) {
                        btnPlay.setText("Pause");
                        mState = State.PLAY;
                        //    btnRecord.setEnabled(false);
                    }
                } else if (mState == State.PLAY) {
                    mDrumMap.stopPlayRecord();
                    mState = State.NONE;
                    // btnRecord.setEnabled(true);
                    btnPlay.setText("Play");
                }
            }
        });
        mPositionRunnable = new Runnable() {
            @Override
            public void run() {
                while (mIsAlive) {
                    tvPosition.post(new Runnable() {
                        @Override
                        public void run() {
                            tvPosition.setText(TimeUtils.getStringPosition(mDrumMap.getRecordPosition()));
                        }
                    });

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFileRecord != null) {
                    mListener.OnSelected(mFileRecord, mChannel);
                    dismiss();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });
        mPositonThread = new Thread(mPositionRunnable);
        mPositonThread.start();
        setOnDismissListener(this);

    }


    public static void showDialog(DrumMapJni drumMapJni, int channel, Context context, FileDialog.IOnFileSelected listener) {

        GlobalRecordDialog recordDialog = new GlobalRecordDialog(context, drumMapJni, channel, listener);
        recordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        recordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recordDialog.show();
        recordDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}