package com.qc4w.ilocker.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.qc4w.ilocker.R;
import com.qc4w.ilocker.ui.adapter.MenuAdapter;
import com.qc4w.ilocker.ui.fragment.AboutFragment;
import com.qc4w.ilocker.ui.fragment.MainScreenFragment;
import com.qc4w.ilocker.ui.fragment.MainStageFragment;
import com.qc4w.ilocker.ui.fragment.SettingsFragment;
import com.qc4w.ilocker.ui.fragment.WallpaperFragment;
import com.qc4w.ilocker.util.AppUtils;
import com.qc4w.ilocker.util.MIUIHelper;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("ResourceAsColor")
public class MainStageActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private MenuAdapter mMenuAdapter;
	
	private FragmentManager mFM;

	private Fragment mOldFragment;
	
	private long mLastKeyTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();

		MobclickAgent.setDebugMode(true);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
	}
	

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName());
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}
	

	private void initUI() {
		mFM = getSupportFragmentManager();
		
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		FragmentTransaction ft = mFM.beginTransaction();
		ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		mOldFragment = new MainStageFragment();
		ft.replace(R.id.rl_fragment_container, mOldFragment);
		ft.commit();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);
		
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		mMenuAdapter = new MenuAdapter(this);
		mDrawerList.setAdapter(mMenuAdapter);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDrawerLayout.closeDrawer(mDrawerList);
				if(mMenuAdapter.getSelectPosition() == position) {
					return;
				}
				mMenuAdapter.setSelectPosition(position);
				switch (position) {
					case 0: // 首页
						if(!(mOldFragment instanceof MainStageFragment)) {
							FragmentTransaction ft = mFM.beginTransaction();
							ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mOldFragment = new MainStageFragment();
							ft.replace(R.id.rl_fragment_container, mOldFragment, MainStageFragment.TAG);
							ft.commit();
						}
						break;
					case 1: // 墙纸
						if(!(mOldFragment instanceof WallpaperFragment)) {
							FragmentTransaction ft = mFM.beginTransaction();
							ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mOldFragment = new WallpaperFragment();
							ft.replace(R.id.rl_fragment_container, mOldFragment, WallpaperFragment.TAG);
							ft.commit();
						}
						break;
					case 2: // 部件
						if(!(mOldFragment instanceof MainScreenFragment/*WidgetFragment*/)) {
							FragmentTransaction ft = mFM.beginTransaction();
							ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mOldFragment = new MainScreenFragment/*WidgetFragment*/();
							ft.replace(R.id.rl_fragment_container, mOldFragment, MainScreenFragment/*WidgetFragment*/.TAG);
							ft.commit();
						}
						break;
					case 3: // 设置
						if(!(mOldFragment instanceof SettingsFragment)) {
							FragmentTransaction ft = mFM.beginTransaction();
							ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mOldFragment = new SettingsFragment();
							ft.replace(R.id.rl_fragment_container, mOldFragment, SettingsFragment.TAG);
							ft.commit();
						}
						break;
					case 4: // 关于
						if(!(mOldFragment instanceof AboutFragment)) {
							FragmentTransaction ft = mFM.beginTransaction();
							ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
							mOldFragment = new AboutFragment();
							ft.replace(R.id.rl_fragment_container, mOldFragment, AboutFragment.TAG);
							ft.commit();
						}
						break;
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(MIUIHelper.isMiuiV5()) {
			getMenuInflater().inflate(R.menu.main_menu_miui, menu);
		} else {
			getMenuInflater().inflate(R.menu.main_menu, menu);
		}
		return true;
	}
	
	public void change2Home() {
		mDrawerLayout.closeDrawer(mDrawerList);
		if(mMenuAdapter.getSelectPosition() == 0) {
			return;
		}
		mMenuAdapter.setSelectPosition(0);
		if(!(mOldFragment instanceof MainStageFragment)) {
			FragmentTransaction ft = mFM.beginTransaction();
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			mOldFragment = new MainStageFragment();
			ft.replace(R.id.rl_fragment_container, mOldFragment, MainStageFragment.TAG);
			ft.commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				break;
			case R.id.menu_mi:
				Intent intent = new Intent(this, MIUISettingsActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_feedback:
				AppUtils.feedback(this);
				break;
			case R.id.menu_help:
				intent = new Intent(this, HelpActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_rate:
				AppUtils.openMarket(this, getPackageName());
				break;
//			case R.id.menu_share:
//				Toast.makeText(this, "font10", Toast.LENGTH_LONG).show();
//				intent = new Intent(this, MenuDemoActivity.class);
//				startActivity(intent);
//				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if(mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
				return true;
			} else if(!(mOldFragment instanceof MainStageFragment)) {
				FragmentTransaction ft = mFM.beginTransaction();
				ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				mOldFragment = mFM.findFragmentByTag(MainStageFragment.TAG);
				if(mOldFragment == null) {
					mOldFragment = new MainStageFragment();
				}
				ft.replace(R.id.rl_fragment_container, mOldFragment, MainStageFragment.TAG);
				ft.commit();
				mMenuAdapter.setSelectPosition(0);
				return true;
			} else if(System.currentTimeMillis() - mLastKeyTime > 2000) {
				Toast.makeText(this, R.string.press_back_to_finish, Toast.LENGTH_LONG).show();
				mLastKeyTime = System.currentTimeMillis();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyUp(keyCode, event);
	}
}
