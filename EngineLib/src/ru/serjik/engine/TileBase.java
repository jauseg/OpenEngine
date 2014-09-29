package ru.serjik.engine;

public class TileBase
{
	public Texture texture;
	public float u1, v1, u2, v2;
	public float ox, oy, width, height;

	public TileBase(Texture texture, float u1, float v1, float u2, float v2, float ox, float oy, float width,
			float height)
	{
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.ox = (ox - u1) * (width / (u2 - u1));
		this.oy = (oy - v1) * (height / (v2 - v1));

		this.u1 = u1 / (float) texture.width;
		this.v1 = v1 / (float) texture.height;
		this.u2 = u2 / (float) texture.width;
		this.v2 = v2 / (float) texture.height;
	}

	public TileBase(Texture texture, float u1, float v1, float u2, float v2, float ox, float oy)
	{
		this(texture, u1, v1, u2, v2, ox, oy, u2 - u1, v2 - v1);
	}

	public TileBase(Texture texture, float u1, float v1, float u2, float v2)
	{
		this(texture, u1, v1, u2, v2, (u1 + u2) * 0.5f, (v1 + v2) * 0.5f);
	}

	public void draw(BatchDrawer bd, float[] v)
	{
		bd.draw(texture, v[0], v[1], u1, v1, v[2], v[3], u2, v1, v[4], v[5], u2, v2);
		bd.draw(texture, v[0], v[1], u1, v1, v[4], v[5], u2, v2, v[6], v[7], u1, v2);
	}

}
