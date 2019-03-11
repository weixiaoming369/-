package cn.wxm.game;

import java.awt.Color;
//������
public class Point {
	// �����ϵ�x����
	private int x;
	// �����ϵ�Y����
	private int y;
	// ��ɫ
	private Color color;
	// ֱ������
	public static final int DIAMETER = 30;

	public Point(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}

	// �õ������ϵ�X����
	public int getX() {
		return x;
	}

	// �õ������ϵ�Y����
	public int getY() {
		return y;
	}

	// �õ���ɫ
	public Color getColor() {
		return color;
	}
}
