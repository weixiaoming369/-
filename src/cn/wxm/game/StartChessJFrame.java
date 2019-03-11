package cn.wxm.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class StartChessJFrame extends JFrame {
    ChessBoard chessboard=new ChessBoard();
	private JPanel toolbar;
	private JButton btnStart;
	private JButton btnBack;
	private JButton btnExit;
	private JMenuBar menuBar;
	private JMenu sysMenu;
	private JMenuItem startMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem backMenuItem;

	public StartChessJFrame() {
		// 设置标题		
		setTitle("我的单机版简易五子棋");
		// 创建和添加菜单
		menuBar = new JMenuBar();
		// 初始化菜单栏
		sysMenu = new JMenu("系统");
		// 初始化菜单
		startMenuItem = new JMenuItem("重新开始");
		exitMenuItem = new JMenuItem("退出");
		backMenuItem = new JMenuItem("悔棋");
		// 初始化菜单项，将三个菜单项添加到菜单上
		sysMenu.add(startMenuItem);
		sysMenu.add(backMenuItem);
		sysMenu.add(exitMenuItem);
		// 初始化按钮事件监听器内部类,将三个菜单项注册到事件监听器上
		MyItemListener lis = new MyItemListener();
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		// 将系统菜单添加到菜单栏上
		menuBar.add(sysMenu);
		// 将menuBar设置成菜单栏
		setJMenuBar(menuBar);
		// 工具面板栏实例化
		toolbar = new JPanel();
		// 三个按钮初始化
		btnStart = new JButton("开始游戏");
		btnBack = new JButton("悔棋");
		btnExit = new JButton("退出");
		// 将工具面板按钮使用FlowLayout布局
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 将三个按钮添加到工具面板上
		toolbar.add(btnStart);
		toolbar.add(btnBack);
		toolbar.add(btnExit);
		// 为三个按钮注册监听事件
		btnStart.addActionListener(lis);
		btnBack.addActionListener(lis);
		btnExit.addActionListener(lis);
		// 将工具面板布局到界面的南方即就是下面
		add(toolbar, BorderLayout.SOUTH);
		// 将棋盘对象添加到窗体中间区域
		add(chessboard, BorderLayout.CENTER);
		// 设置界面关闭事件
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 自适应大小
		setSize(600, 650);
		setResizable(false);//不允许改变窗体大小
		pack();
	}

	// 事件监听器内部类
	private class MyItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();
			if (obj == StartChessJFrame.this.startMenuItem || obj == btnStart) {
				//System.out.println("重新开始...");
				chessboard.restartGame();
			} else if (obj == exitMenuItem || obj == btnExit) {
				System.exit(0);
			} else if (obj == backMenuItem || obj == btnBack) {
				//System.out.println("悔棋...");
				chessboard.goback();
			} 
		}
	}

	public static void main(String[] args) {
		// 创建主框架
		StartChessJFrame f = new StartChessJFrame();
		// 显示主框架
		f.setVisible(true);
	}
}
