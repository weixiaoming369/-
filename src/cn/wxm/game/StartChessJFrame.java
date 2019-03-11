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
		// ���ñ���		
		setTitle("�ҵĵ��������������");
		// ��������Ӳ˵�
		menuBar = new JMenuBar();
		// ��ʼ���˵���
		sysMenu = new JMenu("ϵͳ");
		// ��ʼ���˵�
		startMenuItem = new JMenuItem("���¿�ʼ");
		exitMenuItem = new JMenuItem("�˳�");
		backMenuItem = new JMenuItem("����");
		// ��ʼ���˵���������˵�����ӵ��˵���
		sysMenu.add(startMenuItem);
		sysMenu.add(backMenuItem);
		sysMenu.add(exitMenuItem);
		// ��ʼ����ť�¼��������ڲ���,�������˵���ע�ᵽ�¼���������
		MyItemListener lis = new MyItemListener();
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		// ��ϵͳ�˵���ӵ��˵�����
		menuBar.add(sysMenu);
		// ��menuBar���óɲ˵���
		setJMenuBar(menuBar);
		// ���������ʵ����
		toolbar = new JPanel();
		// ������ť��ʼ��
		btnStart = new JButton("��ʼ��Ϸ");
		btnBack = new JButton("����");
		btnExit = new JButton("�˳�");
		// ��������尴ťʹ��FlowLayout����
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ��������ť��ӵ����������
		toolbar.add(btnStart);
		toolbar.add(btnBack);
		toolbar.add(btnExit);
		// Ϊ������ťע������¼�
		btnStart.addActionListener(lis);
		btnBack.addActionListener(lis);
		btnExit.addActionListener(lis);
		// ��������岼�ֵ�������Ϸ�����������
		add(toolbar, BorderLayout.SOUTH);
		// �����̶�����ӵ������м�����
		add(chessboard, BorderLayout.CENTER);
		// ���ý���ر��¼�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ����Ӧ��С
		setSize(600, 650);
		setResizable(false);//������ı䴰���С
		pack();
	}

	// �¼��������ڲ���
	private class MyItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();
			if (obj == StartChessJFrame.this.startMenuItem || obj == btnStart) {
				//System.out.println("���¿�ʼ...");
				chessboard.restartGame();
			} else if (obj == exitMenuItem || obj == btnExit) {
				System.exit(0);
			} else if (obj == backMenuItem || obj == btnBack) {
				//System.out.println("����...");
				chessboard.goback();
			} 
		}
	}

	public static void main(String[] args) {
		// ���������
		StartChessJFrame f = new StartChessJFrame();
		// ��ʾ�����
		f.setVisible(true);
	}
}
