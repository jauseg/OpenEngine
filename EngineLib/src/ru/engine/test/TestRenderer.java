package ru.engine.test;

import java.io.IOException;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import ru.serjik.engine.AtlasGenerator;
import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.EngineRenderer2D;
import ru.serjik.engine.Sprite;
import ru.serjik.engine.Texture;
import ru.serjik.utils.BitmapUtils;
import ru.serjik.utils.FileUtils;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.SystemClock;

public class TestRenderer extends EngineRenderer2D
{
	public TestRenderer(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	private BatchDrawer bd;
	private Texture atlas;
	private Sprite background, sparkle, star;

	private LampsSystem lampsSystem;
	private SnowSystemNative ss;
	private float offset = 0.5f;

	Random rnd = new Random(SystemClock.elapsedRealtime());

	@Override
	public void created(GL10 gl)
	{
		AtlasGenerator ag = new AtlasGenerator(1024);

		AssetManager am = context.getAssets();

		background = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "background.jpg"), true));
		sparkle = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "sparkle.png"), true));
		star = new Sprite(ag.tile(BitmapUtils.loadBitmapFromAsset(am, "star.png"), true));

		atlas = new Texture(ag.atlas(), gl);
		ag.atlas().recycle();

		bd = new BatchDrawer(4096, gl);

		try
		{
			lampsSystem = new LampsSystem(FileUtils.readAllLines(am.open("lamps.csv"), true));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changed(GL10 gl, int width, int height)
	{
		Texture.enable(gl);
		atlas.bind();

		Texture.filter(gl, GL10.GL_LINEAR, GL10.GL_LINEAR);
		gl.glShadeModel(GL10.GL_SMOOTH);

		ss = new SnowSystemNative(width(), height(), star);
		background.scale(height() > width() ? (height() / background.height()) : (width() / background.width()));
	}

	@Override
	public void draw(GL10 gl)
	{
		float bgsw = background.width() * background.scale();
		float bgd = bgsw - width();
		background.position(width() / 2.0f + (bgd * (0.5f - offset)), height() / 2.0f);

		// gl.glColor4f(1, 1, 1, 0);
		gl.glDisable(GL10.GL_BLEND);
		background.draw(bd);
		bd.flush();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

		lampsSystem.draw(bd, background, sparkle);

		bd.flush();

		ss.draw(gl);
	}

	public void offset(float value)
	{
		offset = value;
	}
}
