package com.example.game.gamehit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.gamehit.common.Const;
import com.example.game.gamehit.util.MUtils;


public class MenuActivity extends Activity {

	private Context mContext;
	private MediaPlayer mBgMediaPlayer;//
	//private EmotionsView ev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
		
		init();
		
		setContentView(R.layout.activity_main);
		Button btnLevel = (Button) findViewById(R.id.btnGameLevel);
		btnLevel.setOnClickListener(lisn);
		//Button btnRandom = (Button) findViewById(R.id.btnGameRandom);
		//btnRandom.setOnClickListener(lisn);
		Button btnTimer = (Button) findViewById(R.id.btnGameTime);
		btnTimer.setOnClickListener(lisn);
		Button btnSuper = (Button) findViewById(R.id.btnGameSuper);
		btnSuper.setOnClickListener(lisn);
		//Button btnInfo = (Button) findViewById(R.id.btnGameInfo);
		//btnInfo.setOnClickListener(lisn);

        //Button btnSetting = (Button) findViewById(R.id.btnSetting);
        //btnSetting.setOnClickListener(lisn);

	}

	View.OnClickListener lisn = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/*if(v.getId()==R.id.btnGameInfo){
//				Toast.makeText(mContext, "游戏介绍页面", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setInverseBackgroundForced(true);
				TextView customTitleView = new TextView(mContext);
				customTitleView.setText("关于");
				customTitleView.setTextSize(24);
				customTitleView.setGravity(Gravity.CENTER);
				customTitleView.setBackgroundColor(getResources().getColor(R.color.frameColor));
				customTitleView.setTextColor(getResources().getColor(R.color.fontColor));
				builder.setCustomTitle(customTitleView);
				builder.setMessage("夏天到了\r\n来点新鲜可口的水果吧～\r\n点击水果，避开炸弹\r\n加油哦~\r\n\r\n郭家宁\r\n2017年6月");
			    builder.setPositiveButton("确定", null);
			    builder.show();
				return;
			}*/

			if(authController(v.getId())){
				Intent intent = new Intent(mContext, MainActivity.class);
				Bundle bundle = new Bundle();
				switch (v.getId()) {
				case R.id.btnGameLevel:
					Const.gameMode=Const.gameMode_Level;//闯关模式
					break;
				/*
				case R.id.btnGameRandom:
					Const.gameMode=Const.gameMode_Random;//随机模式
					Const.hpNum = 10;
					break;
					*/
				case R.id.btnGameTime:
					Const.gameMode=Const.gameMode_Timer;//计时模式
					showSelTime(intent);
					break;
				case R.id.btnGameSuper:
					Const.gameMode=Const.gameMode_Super;//无尽模式
					break;
				}
				if(v.getId()!=R.id.btnGameTime){
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		}

	};
	private void showSelTime(final Intent intent) {
		TextView textView = new TextView(mContext);
    	textView.setText("请选择挑战时间");
    	textView.setTextSize(24);
    	textView.setGravity(Gravity.CENTER);
    	textView.setBackgroundColor(getResources().getColor(R.color.frameColor));
    	textView.setTextColor(getResources().getColor(R.color.fontColor));
		new AlertDialog.Builder(mContext)
		.setView(textView)
		.setPositiveButton("30秒", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Const.timeNum = 30;
				startActivity(intent);
			}})
		.setNeutralButton("45秒", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Const.timeNum = 45;
				startActivity(intent);
			}})
		.setNegativeButton("60秒", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Const.timeNum = 60;
				startActivity(intent);
			}})
		.show();
	}
	
	/**
	 * 
	 */
	private void init() {
		mContext = MenuActivity.this;
		mBgMediaPlayer = MediaPlayer.create(mContext, R.raw.background);
		mBgMediaPlayer.setLooping(true);//循环
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    	Const.CURRENT_SCREEN_WIDTH = mDisplayMetrics.widthPixels;
    	Const.CURRENT_SCREEN_HEIGHT = mDisplayMetrics.heightPixels;
    	Const.CURREN_SCALE = Const.CURRENT_SCREEN_WIDTH/Const.DEF_SCREEN_WIDTH;
    	Const.CURREN_WIDTH_SCALE = Const.CURRENT_SCREEN_WIDTH/Const.DEF_SCREEN_WIDTH;
    	Const.CURREN_HEIGHT_SCALE = Const.CURRENT_SCREEN_HEIGHT/Const.DEF_SCREEN_HEIGHT;
    	Const.CURRENT_BLOCK_WIDTH_HEIGHT = Const.CURREN_SCALE*Const.DEF_BLOCK_WIDTH_HEIGHT;
    	Const.CURRENT_BLOCK_WIDTH = Const.CURREN_WIDTH_SCALE*Const.DEF_BLOCK_WIDTH_HEIGHT;
    	Const.CURRENT_BLOCK_HEIGHT = Const.CURREN_HEIGHT_SCALE*Const.DEF_BLOCK_WIDTH_HEIGHT;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			existGame();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	//
	private void existGame(){
		TextView textView = new TextView(mContext);
    	textView.setText("再玩一会吧～");
    	textView.setTextSize(24);
    	textView.setGravity(Gravity.CENTER);
    	textView.setBackgroundColor(getResources().getColor(R.color.frameColor));
    	textView.setTextColor(getResources().getColor(R.color.fontColor));


		//添加卸载
		new AlertDialog.Builder(mContext)
				.setCustomTitle(textView)
				.setPositiveButton("好啊(◐‿◑)", null)
				.setNeutralButton("一会儿再来，我去歇会^-^", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setContentView(R.layout.activity_end);
						Handler handler = new Handler();
						//当计时结束,跳转至主界面
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								finish();
							}
						}, 3000);
					}
				})
				.setNegativeButton("不玩了，我要去学习T^T", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Uri packageUri = Uri.parse("package:"+MenuActivity.this.getPackageName());
						Intent intent = new Intent(Intent.ACTION_DELETE,packageUri);
						startActivity(intent);
					}
				})
				.show();
	}


	//播放器
	//自动播放
	@Override
	protected void onResume() {
		adMethoe();
		if(Const.backgroundMusicOn && mBgMediaPlayer!=null && !mBgMediaPlayer.isPlaying()){
			mBgMediaPlayer.start();
		}
		super.onResume();
	}

	//暂停
	@Override
	protected void onPause() {
		if (this.mBgMediaPlayer != null && this.mBgMediaPlayer.isPlaying()) {
			this.mBgMediaPlayer.pause();
        }
		super.onPause();
	}

	//销毁
	@Override
	protected void onDestroy() {
		if (this.mBgMediaPlayer != null) {
			this.mBgMediaPlayer.stop();
			this.mBgMediaPlayer.release();
			this.mBgMediaPlayer = null;
        }
		super.onDestroy();
	}

	private void adMethoe() {
		MUtils.getInstance(mContext);
		MUtils.getPush();
    	MUtils.getInstance(mContext);
		MUtils.showRight();
    	MUtils.getInstance(mContext);
		MUtils.showTop();
	}
//调用系统自带，文字正常，图片只能分享到微信
	//分享
	//分享单张图片
	//申请腾讯开发者
	public void share(View view) {
		String path = getResourcesUri(R.drawable.share);
		Intent imageIntent = new Intent(Intent.ACTION_SEND);
		imageIntent.setType("image/png");
		imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
		startActivity(Intent.createChooser(imageIntent, "分享"));
	}

	private String getResourcesUri( int id) {
		Resources resources = getResources();
		String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
				resources.getResourcePackageName(id) + "/" +
				resources.getResourceTypeName(id) + "/" +
				resources.getResourceEntryName(id);
		Toast.makeText(this, "Uri:" + uriPath, Toast.LENGTH_SHORT).show();
		return uriPath;
	}


//关于页面跳转
	public void about(View view) {
		setContentView(R.layout.about);
	}
	//返回键
	public void areturn(View view) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		//参数是包名，类全限定名，注意直接用类名不行
		ComponentName cn = new ComponentName("com.example.game.gamehit",
				"com.example.game.gamehit.MenuActivity");
		intent.setComponent(cn);
		startActivity(intent);
		finish();//不要忘啊！！！结束页
	}

	public void eat(View view) {
		Uri uri = Uri.parse("http://baike.baidu.com/link?url=7n5lUPe9da7zIsfpF1qnFwKzD3SzSymVBkRV6i6u8sNZO84geK0Vgxp1Z87wnpc-DrBx__Ei4JsNte-t5u8lhFjGPj7JN0zRcasJkBSXj9BLSY1_kjgpmwY72Us3b7jO");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	
	//---------------------以下为权限控制代码---------------------
	private boolean authController(int freeMenu){
		if(freeMenu==R.id.btnGameLevel){
			return true;
		}
//		Toast.makeText(mContext, "有权限开始游戏", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	private void saveOrUpdateAuth(int menu){
		switch (menu) {
		case R.id.btnGameLevel:
			break;
		/*
		case R.id.btnGameRandom:
			break;
			*/
		case R.id.btnGameTime:
			break;
		case R.id.btnGameSuper:
			break;
		case R.id.btnGameInfo:
			break;


		case R.id.btnSetting:
			break;
		}
	}
	
	private boolean getAuth(int menu){
		boolean flag = false;
		
		return flag;
	}

/*

	int score = 0;
	BreakIterator scoreText;
	int topScore;
	public void updateScore(int add){
		score += add;
		scoreText.setText(score+"");
		if(score>topScore.getTopScode())
			topScore.setTopScode(score);
		topScoreText.setText(topScore.getTopScode()+"");
	}*/
}

