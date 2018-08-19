package jp.techinstitute.oshimaryo.threadhandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {

    private Thread mThread;
    private Handler mHandler;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mHandler = new Handler() {
            //メッセージ受信
            public void handleMessage(android.os.Message message) {
//メッセージの表示
                String text = (String) message.obj;
                mTextView.setText(text);
//メッセージの種類に応じてswitch文で制御すれば
//イベント制御に利用可能
            }

            ;
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mThread = new Thread(this);
//スレッド処理を開始
        if (mThread != null) {
            mThread.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//スレッドを削除
        mThread = null;
    }

    public void run() {
        long time = System.currentTimeMillis();
        long count = 0;
        while (mThread != null) {
            long now = System.currentTimeMillis();
            if(now - time > 1000){
                final String text = new String("ループが"+
                        count + "回終了しました");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
// UIスレッドで動作する
                        mTextView.setText( text );
                    }
                });
//スレッドの利用変数を初期化
                time = now;
                count++;
            }
        }
    }
}
