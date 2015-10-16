package com.qc4w.ilocker.ui.fragment;

import android.support.v4.app.Fragment;


public class FragmentBase extends Fragment {
	
	public String getTAG() {
		return this.getClass().getSimpleName();
	}
	
}
