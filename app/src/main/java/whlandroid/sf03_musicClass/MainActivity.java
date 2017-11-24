package whlandroid.sf03_musicClass;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener{
    //声明变量
//    Button mBtn_getMusic,mBtn_getVideo;
    Button mBtn_getVideo, mBtn_newshow;
    ImageButton mImgB_play,mImgB_stop,mImgB_loop,mImgB_forward,mImgB_backward,mImgB_info;
    TextView mTxv_showName,mTxv_showUri;
    VideoView mTxv_videoView;
//    Uri mUri;
    Uri mUri1;
    MediaPlayer mper;
    Toast mToast;
    boolean islooping=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgB_play=(ImageButton)findViewById(R.id.imgB_play);
        mImgB_stop=(ImageButton)findViewById(R.id.imgB_stop);


        initView();
//        mUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.welcome);
//        mTxv_showName.setText("welcome.mp3");
//        mTxv_showUri.setText("程序内音乐："+mUri.toString());
//        prepareMusic();
//

        mUri1=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.camera);
        mTxv_showName.setText("camera.mp4");
        mTxv_showUri.setText("程序内视频："+mUri1.toString());
        prepareVideo();
    }

    //Activity类的后边界
    void initView(){
        mper=new MediaPlayer();
//        mBtn_getMusic=(Button)findViewById(R.id.btn_getMusic);
        mBtn_getVideo=(Button)findViewById(R.id.btn_getVideo);
        mImgB_play=(ImageButton)findViewById(R.id.imgB_play);
        mImgB_stop=(ImageButton)findViewById(R.id.imgB_stop);
        mImgB_backward=(ImageButton)findViewById(R.id.imgB_backward);
        mImgB_forward=(ImageButton)findViewById(R.id.imgB_forward);
        mImgB_info=(ImageButton)findViewById(R.id.imgB_info);
        mImgB_loop=(ImageButton)findViewById(R.id.imgB_looping);
        mBtn_newshow=(Button)findViewById(R.id.new_show);
        mTxv_showName=(TextView)findViewById(R.id.txv_videoName);
        mTxv_showUri=(TextView)findViewById(R.id.txv_videoUri);
        mTxv_videoView=(VideoView)findViewById(R.id.txv_videoview);
/*        mUri1=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.camera);*/
        mTxv_videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.camera);
        /*mTxv_videoView.*/
//        mTxv_showName=(TextView)findViewById(R.id.txv_musicName);
//        mTxv_showUri=(TextView)findViewById(R.id.txv_musicUri);

//        mBtn_getMusic.setOnClickListener(this);
        mBtn_getVideo.setOnClickListener(this);
        mBtn_newshow.setOnClickListener(this);
        mImgB_play.setOnClickListener(this);
        mImgB_backward.setOnClickListener(this);
        mImgB_forward.setOnClickListener(this);
        mImgB_stop.setOnClickListener(this);
        mImgB_loop.setOnClickListener(this);
        mImgB_info.setOnClickListener(this);
        mper.setOnPreparedListener(this);
        mper.setOnErrorListener(this);
        mper.setOnCompletionListener(this);

        mToast=Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }
/*    public void prepareMusic(){
        mImgB_play.setEnabled(false);
        mImgB_stop.setEnabled(false);
        mImgB_backward.setEnabled(false);
        mImgB_forward.setEnabled(false);
        try {
            mper.reset();
            mper.setDataSource(this,mUri);
            mper.setLooping(islooping);
            mper.prepareAsync();
        }catch(IOException e){
            e.printStackTrace();
            mToast.setText("指定文件错误"+e.toString());
            mToast.show();
        }
    }*/


    public void prepareVideo(){
        mImgB_play.setEnabled(false);
        mImgB_stop.setEnabled(false);
        mImgB_backward.setEnabled(false);
        mImgB_forward.setEnabled(false);
        try {
            mper.reset();
            mTxv_videoView.setVideoURI(mUri1);
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


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mImgB_play.setEnabled(true);
        mTxv_videoView.start();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.new_show:
                // 生成一个Intent对象
                Intent intent=new Intent();
                String path = mUri1.toString();
                int current_posi = mTxv_videoView.getCurrentPosition();
                String current_posi1 = Integer.toString(current_posi);
                intent.putExtra("URL",path);
                intent.putExtra("current", current_posi1);
                intent.setClass(MainActivity.this, VideoViewActivity.class); //设置跳转的Activity
                MainActivity.this.startActivity(intent);
            case R.id.imgB_play:
                if(mTxv_videoView.isPlaying()){
//                    mper.pause();
                    mTxv_videoView.pause();
                    mImgB_play.setImageResource(R.drawable.ic_media_play);
                }else{
//                    mper.start();
                    mTxv_videoView.start();
                    mImgB_play.setImageResource(R.drawable.ic_media_pause);
                }
                mImgB_forward.setEnabled(true);
                mImgB_stop.setEnabled(true);
                mImgB_backward.setEnabled(true);
                mToast=Toast.makeText(this,"视频播放",Toast.LENGTH_SHORT);
                break;
            //停止播放视频
            case R.id.imgB_stop:
//                mper.pause();//暂停播放
                mTxv_videoView.pause();
                mTxv_videoView.seekTo(0);
//                mper.seekTo(0);
                mImgB_stop.setEnabled(false);
                mImgB_play.setImageResource(R.drawable.ic_media_play);
                mToast=Toast.makeText(this,"停止播放",Toast.LENGTH_SHORT);
                break;
            //获取视频长度
            case R.id.imgB_info:
                if (!mImgB_play.isEnabled()) return;
//                int lenght = mper.getDuration();
                int lenght = mTxv_videoView.getDuration();
                mToast.setText("视频长度为：" + lenght/1000);
                mToast.show();
                break;
            //视频前进
            case R.id.imgB_forward:
                if (!mImgB_play.isEnabled()) return;//如果播放按钮禁用，则不处理
                int len = mTxv_videoView.getDuration();
                int pos = mTxv_videoView.getCurrentPosition();
                pos += 10000;//前进10秒
                if (pos > len) pos = len;//不可大于总长度
                mTxv_videoView.seekTo(pos);//移动到播放位置
                mToast.setText("前进10秒：" + pos/1000 + "/" + len/1000);//显示信息
                mToast.show();
                break;

            case R.id.imgB_backward:
                if (!mImgB_play.isEnabled()) return;

                int len1 = mTxv_videoView.getDuration();

                int pos1 = mTxv_videoView.getCurrentPosition();
                pos1 -= 10000;
                if (pos1 < 0) pos1 = 0;//不可小于0
                mTxv_videoView.seekTo(pos1);
                mToast.setText("倒退10秒：" + pos1/1000 + "/" + len1/1000);
                mToast.show();
                break;
            /*//启动获取音乐程序
            case R.id.btn_getMusic:
                //创建动作（action）为“选择内容”的Intent对象
                Intent itMusic = new Intent(Intent.ACTION_GET_CONTENT);
                //选择所有音乐类型
                itMusic.setType("audio*//*");//获取音频文件库的所有类型的音频文件。
                //以请求号为11 来启动外部程序
                startActivityForResult(itMusic,11);
//                mToast=Toast.makeText(this,"获取外部音乐成功！",Toast.LENGTH_SHORT);
                break;*/
            case R.id.btn_getVideo:
                Intent itVideo = new Intent(Intent.ACTION_GET_CONTENT);
                itVideo.setType("video/*");
                startActivityForResult(itVideo,12);
                break;
            default:
                break;
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
/*                case 11:
                    //data.getData()获取用户选取的Uri
                    mUri = convertUri(data.getData());
                    //返回mUri的最后一段文字，即文件名部分
                    mTxv_showName.setText("歌曲名：" + mUri.getLastPathSegment().toString());
                    //获取mUri中的文件路径
                    mTxv_showUri.setText("文件路径：" + mUri.getPath().toString());
                    prepareMusic();//准备播放音乐文件
                    break;*/
                case 12:
                    //data.getData()获取用户选取的Uri
                    mUri1 = convertUri(data.getData());
                    //返回mUri的最后一段文字，即文件名部分
                    mTxv_showName.setText("视频名：" + mUri1.getLastPathSegment().toString());
                    //获取mUri中的文件路径
                    mTxv_showUri.setText("文件路径：" + mUri1.getPath().toString());
//                    prepareVideo();//准备播放视频文件
                    Intent intent=new Intent();
                    String path = mUri1.toString();
                    int current_posi = mTxv_videoView.getCurrentPosition();
                    String current_posi1 = Integer.toString(current_posi);
                    intent.putExtra("URL",path);
                    intent.putExtra("current", current_posi1);
                    intent.setClass(MainActivity.this, VideoViewActivity.class); //设置跳转的Activity
                    MainActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
    //将“content://”类型的Uri转换为"file://"的Uri
    Uri convertUri(Uri uri){
        //判断如果是以content开头
        if(uri.toString().substring(0,7).equals("content")){
            //声明要查询的字段
            String[] colName ={MediaStore.MediaColumns.DATA};
            //对uri进行查询；对共享库的查询都采用getContentResolver()方法
            Cursor cusor = getContentResolver().query(uri,colName,null,null,null);
            //移动查询结果的第一个记录
            cusor.moveToFirst();
            //将路径转换为"file://"开头的Uri
            mUri1 = Uri.parse("file://" + cusor.getString(0));
            //关闭查询结构
            cusor.close();
        }
        return uri;//返回Uri对象
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }
    //Activity活动暂停时，音乐播放暂停，并设置播放按钮图片
    @Override
    protected void onPause() {
        super.onPause();//执行父类的事件方法以处理必须事宜
        if(mper.isPlaying()){
            mper.pause();//音乐播放暂停，
            mImgB_play.setImageResource(R.drawable.ic_media_play);//设置播放按钮图片
        }
        mToast=Toast.makeText(this,"熄屏暂停播放测试成功！",Toast.LENGTH_SHORT);
    }
    //当Activity结束时，将MediaPlayer对象释放掉release()
    @Override
    protected void onDestroy() {
        mper.release();//释放MediaPlayer对象
        super.onDestroy();//释放父类的销毁方法以处理相关销毁事宜
        mToast=Toast.makeText(this,"熄屏暂停播放测试成功！",Toast.LENGTH_SHORT);
    }


}