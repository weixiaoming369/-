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

//棋盘类
@SuppressWarnings({ "serial", "unused" })
public class ChessBoard extends JPanel implements MouseListener {
	// 边距
	public static final int MARGIN = 30;
	// 网格间距
	public static final int GRID_SPAN = 35;
	// 棋盘行数
	public static final int ROWS = 20;
	// 棋盘列数
	public static final int COLS = 20;
	int chessCount;
	// 棋盘棋子个数
	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];
	// 当前棋子的索引
	int xindex, yindex;
	// 默认开局黑旗先下
	boolean isBack = true;
	// 判断游戏是否已经结束
	private boolean gameOver = false;

	public ChessBoard() {
		// 设置背景色为橘黄色
		setBackground(Color.ORANGE);
		addMouseListener(this);
		// 匿名内部类
		addMouseMotionListener(new MouseMotionListener() {
			// 鼠标按键在组件上按下并拖动时需要实现此方法
			@Override
			public void mouseDragged(MouseEvent e) {
                     //空实现
			}
			// 监听鼠标移动区域来决定显示的形状
			@Override
			public void mouseMoved(MouseEvent e) {
				// 鼠标单击的坐标位置转换为网格索引
				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				// 游戏已经结束，不能再下
				// 设置成手性
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 画棋盘
		for (int i = 0; i <= ROWS; i++) { // 画横线
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) { // 画直线
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}
		// 画棋子
		for (int i = 0; i < chessCount; i++) {
			// 网格交叉的x坐标
			int xpos = chessList[i].getX() * GRID_SPAN + MARGIN;
			// 网格交叉的y坐标
			int ypos = chessList[i].getY() * GRID_SPAN + MARGIN;
			// 设置颜色
			g.setColor(chessList[i].getColor());
			// 标记最后一个棋子的红矩形框
			g.fillOval(xpos - Point.DIAMETER / 2, ypos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			// 最后一个棋子
			if (i == chessCount - 1) {
				g.setColor(Color.red);
				g.drawRect(xpos - Point.DIAMETER / 2, ypos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			}
		}
	}

	// 在数组中查找是否有索引为x,y的棋子存在
	private boolean findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}

	// 悔棋操作
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

	// 重新开始初始化
	public void restartGame(){
		   //清除棋子
		   for(int i=0;i<chessList.length;i++){
			   chessList[i]=null;
		   }
		   //恢复游戏相关的变量值
		   isBack=true;
		   gameOver=false; //游戏是否结束
		   chessCount =0; //当前棋盘棋子个数
		   repaint();
	   }

	// 判断哪一方赢
	public boolean isWin() {
		// 连续棋子的个数
		int continueCount = 1;
		Color c = isBack ? Color.black : Color.white;
		// 45度和135度方位寻找
		// 横向向西
		for (int x = xindex - 1; x >= 0; x--) {
			if (getChess(x, yindex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 横向向东
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

		// 纵向向上
		for (int y = yindex - 1; y >= 0; y--) {
			if (getChess(xindex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 纵向向下
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

		// 东北寻找 坐标轴分布
		for (int x = xindex + 1, y = yindex - 1; y >= 0 && x < COLS; x++, y--) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 西南寻找
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
		// 东南寻找
		for (int x = xindex + 1, y = yindex + 1; y >= 0 && x < COLS; x++, y++) {
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 西北寻找
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

	// 得到棋子
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
		String colorName = isBack ? "黑棋" : "白棋";
		// 将鼠标单击的坐标位置转换成网格索引
		xindex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yindex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		// 落在棋盘外，不能下
		if (xindex < 0 || xindex > ROWS || yindex < 0 || yindex > COLS)
			return;
		if (findChess(xindex, yindex))// x,y位置已经有棋子存在，不能下
			return;
		Point ch = new Point(xindex, yindex, isBack ? Color.black : Color.white);
		chessList[chessCount++] = ch;
		repaint();// 通知系统重新绘制
		if (isWin()) {
			String msg = String.format("恭喜，%s赢啦~", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}else if (chessCount == (COLS + 1) * (ROWS + 1))// 下满棋盘
		{
			String msg = String.format("棋鼓相当，棒棒哒~");
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
