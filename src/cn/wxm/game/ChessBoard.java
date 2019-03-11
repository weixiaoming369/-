package cn.wxm.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//������
@SuppressWarnings({ "serial", "unused" })
public class ChessBoard extends JPanel implements MouseListener {
	// �߾�
	public static final int MARGIN = 30;
	// ������
	public static final int GRID_SPAN = 35;
	// ��������
	public static final int ROWS = 20;
	// ��������
	public static final int COLS = 20;
	int chessCount;
	// �������Ӹ���
	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];
	// ��ǰ���ӵ�����
	int xindex, yindex;
	// Ĭ�Ͽ��ֺ�������
	boolean isBack = true;
	// �ж���Ϸ�Ƿ��Ѿ�����
	private boolean gameOver = false;

	public ChessBoard() {
		// ���ñ���ɫΪ�ٻ�ɫ
		setBackground(Color.ORANGE);
		addMouseListener(this);
		// �����ڲ���
		addMouseMotionListener(new MouseMotionListener() {
			// ��갴��������ϰ��²��϶�ʱ��Ҫʵ�ִ˷���
			@Override
			public void mouseDragged(MouseEvent e) {
                     //��ʵ��
			}
			// ��������ƶ�������������ʾ����״
			@Override
			public void mouseMoved(MouseEvent e) {
				// ��굥��������λ��ת��Ϊ��������
				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				// ��Ϸ�Ѿ���������������
				// ���ó�����
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// ������
		for (int i = 0; i <= ROWS; i++) { // ������
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) { // ��ֱ��
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}
		// ������
		for (int i = 0; i < chessCount; i++) {
			// ���񽻲��x����
			int xpos = chessList[i].getX() * GRID_SPAN + MARGIN;
			// ���񽻲��y����
			int ypos = chessList[i].getY() * GRID_SPAN + MARGIN;
			// ������ɫ
			g.setColor(chessList[i].getColor());
			// ������һ�����ӵĺ���ο�
			g.fillOval(xpos - Point.DIAMETER / 2, ypos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			// ���һ������
			if (i == chessCount - 1) {
				g.setColor(Color.red);
				g.drawRect(xpos - Point.DIAMETER / 2, ypos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			}
		}
	}

	// �������в����Ƿ�������Ϊx,y�����Ӵ���
	private boolean findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}

	// �������
	 public void goback(){
		 if(isWin()) {return;}
		   if(chessCount==0)
			   return ;
		   chessList[chessCount-1]=null;
		   chessCount--;
		   if(chessCount>0){
			   xindex=chessList[chessCount-1].getX();
			   yindex=chessList[chessCount-1].getY();
		   }
		   isBack=!isBack;
		   repaint();
	 }

	// ���¿�ʼ��ʼ��
	public void restartGame(){
		   //�������
		   for(int i=0;i<chessList.length;i++){
			   chessList[i]=null;
		   }
		   //�ָ���Ϸ��صı���ֵ
		   isBack=true;
		   gameOver=false; //��Ϸ�Ƿ����
		   chessCount =0; //��ǰ�������Ӹ���
		   repaint();
	   }

	// �ж���һ��Ӯ
	public boolean isWin() {
		// �������ӵĸ���
		int continueCount = 1;
		Color c = isBack ? Color.black : Color.white;
		// 45�Ⱥ�135�ȷ�λѰ��
		// ��������
		for (int x = xindex - 1; x >= 0; x--) {
			if (getChess(x, yindex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ������
		for (int x = xindex + 1; x <= ROWS; x++) {
			if (getChess(x, yindex, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount == 5) {
			return true;
		} else
			continueCount = 1;

		// ��������
		for (int y = yindex - 1; y >= 0; y--) {
			if (getChess(xindex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ��������
		for (int y = yindex + 1; y <= ROWS; y++) {
			if (getChess(xindex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount == 5) {
			return true;
		} else
			continueCount = 1;

		// ����Ѱ�� ������ֲ�
		for (int x = xindex + 1, y = yindex - 1; y >= 0 && x < COLS; x++, y--) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ����Ѱ��
		for (int x = xindex - 1, y = yindex + 1; x >= 0 && y <= ROWS; x--, y++) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount == 5) {
			return true;
		} else
			continueCount = 1;
		// ����Ѱ��
		for (int x = xindex + 1, y = yindex + 1; y >= 0 && x < COLS; x++, y++) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ����Ѱ��
		for (int x = xindex - 1, y = yindex - 1; x >= 0 && y >= 0; x--, y--) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount == 5) {
			return true;
		} else
			continueCount = 1;

		return false;
	}

	// �õ�����
	private Point getChess(int xindex, int yindex, Color color) {
		for (Point c : chessList) {
			if (c != null && c.getX() == xindex && c.getY() == yindex && c.getColor() == color) {
				return c;
			}
		}
		return null;
	}

	public Dimension getPreferredSize() {
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (gameOver)
			return;
		String colorName = isBack ? "����" : "����";
		// ����굥��������λ��ת������������
		xindex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yindex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		// ���������⣬������
		if (xindex < 0 || xindex > ROWS || yindex < 0 || yindex > COLS)
			return;
		if (findChess(xindex, yindex))// x,yλ���Ѿ������Ӵ��ڣ�������
			return;
		Point ch = new Point(xindex, yindex, isBack ? Color.black : Color.white);
		chessList[chessCount++] = ch;
		repaint();// ֪ͨϵͳ���»���
		if (isWin()) {
			String msg = String.format("��ϲ��%sӮ��~", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}else if (chessCount == (COLS + 1) * (ROWS + 1))// ��������
		{
			String msg = String.format("����൱��������~");
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}
		isBack = !isBack;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
