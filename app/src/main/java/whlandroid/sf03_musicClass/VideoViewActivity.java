package whlandroid.sf03_musicClass;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    private VideoView new_video;
    MediaPlayer mper;
    String URL;
    String current;
    Uri mUri1;
    Toast mToast;
    boolean islooping=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //阻止屏幕休眠
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.new_show);
        new_video = (VideoView) findViewById(R.id.new_video); ////获取VideoView的id
        Intent intent=getIntent();
        URL=intent.getStringExtra("URL");
        current = intent.getStringExtra("current");
        initView();
        prepareVideo();

//        mUri1= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.camera);
//        //设置播放的来源
        new_video.setVideoPath(URL);
//        new_video.setVideoURI(mUri1);
        new_video.start();
        //实例化媒体控制器
        MediaController mediaController=new MediaController(this);
        mediaController.setMediaPlayer(new_video);
        new_video.setMediaController(mediaController);
    }



    void initView() {
        mper = new MediaPlayer();
//        mper.setOnPreparedListener(this);
//        mper.setOnErrorListener(this);
//        mper.setOnCompletionListener(this);
    }


    public void prepareVideo() {
        try {
            mUri1= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.camera);
            mper.reset();
            new_video.setVideoURI(mUri1);
            int i=Integer.parseInt(current);
            new_video.seekTo(i);
            mper.setDataSource(this,mUri1);
            mper.setLooping(islooping);
            mper.prepareAsync();
//            mTxv_videoView.start();
        }catch(Exception e){
            e.printStackTrace();
            mToast.setText("指定文件错误"+e.toString());
            mToast.show();
        }
    }
}

