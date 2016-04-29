package com.jikexueyuan.playsoundandlrc;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;//播放器
    private Lrc lrcHandler;//歌词对象
    private TextView tv;
    private List<String> words;//歌词集合
    private List<Integer> times;//时间集合
    private Timer timer;//计时器
    private TimerTask task;//开启任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化
        playMusic();//播放音乐
    }

    //初始化界面
    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        lrcHandler = new Lrc();//创建一个新的歌词对象
        InputStream inputStream = getResources().openRawResource(R.raw.lrc);//传入数据流文件lrc
        lrcHandler.readLrc(inputStream);//获取到歌词文件
        words = lrcHandler.getmWords();//拿出getmWords里面的数据放入words
        times = lrcHandler.getmTimes();


        //给歌词设置开头
        if (words.size() > 0) {
            tv.setText(words.get(0));//将歌词集合words里面的输出拿出来传到sv里面显示
        } else {
            tv.setText("歌曲没有歌词，快下载最新歌词吧 。。。");
        }
    }

    int time = 0;//记录当前时间的参数

    //播放资源文件下的MP3
    private void playMusic() {

        if (player == null) {//判断播放器的对象是否为空
            player = MediaPlayer.create(this, R.raw.mo);//创建播放资源文件mo
            player.start();//开始播放
        }


        //用计时器 来动态改变歌词
        timer = new Timer();//创建一个新的计时器对象
        task = new TimerTask() {//开启一个新的计时任务
            @Override
            public void run() {
                setTextLrc();//设置歌词
            }
        };
        timer.schedule(task, 0, 9);//每9微秒开始计时一次


        //当结束的时候清空
        if (time == player.getDuration()) {
            player.stop();
            time = 0;
            task.cancel();//停止计时器
            player = null;
        }
    }

    //设置歌词
    private void setTextLrc() {
        time++;//time每9毫秒开始加一次
        for (int j = 0; j < times.size(); j++) {
            if (time == times.get(j)) {//查看集合times的数据是否与time相同
                final int f = j;//调用内部类的方法
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        tv.setText(words.get(f));//从words里面取出第F个元素显示到tv
                    }
                });
            }
        }
    }

    //销毁回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();//停止播放
        time = 0;//初始化计时器
        task.cancel();//停止执行任务
        player = null;//将播放器置为空
    }
}
