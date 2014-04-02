package ru.engine.test;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity
{
	private GLSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		surfaceView = new TestView(this);
		setContentView(surfaceView);
		
		
	}

	@Override
	protected void onResume()
	{

		super.onResume();
		surfaceView.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		surfaceView.onPause();
	}

}
