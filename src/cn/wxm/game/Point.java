package cn.wxm.game;

import java.awt.Color;
//棋子类
public class Point {
	// 棋盘上的x索引
	private int x;
	// 棋盘上的Y索引
	private int y;
	// 颜色
	private Color color;
	// 直径常量
	public static final int DIAMETER = 30;

	public Point(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}

	// 拿到棋盘上的X索引
	public int getX() {
		return x;
	}

	// 拿到棋盘上的Y索引
	public int getY() {
		return y;
	}

	// 得到颜色
	public Color getColor() {
		return color;
	}
}
