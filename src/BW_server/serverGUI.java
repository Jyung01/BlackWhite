package BW_server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class serverGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel myp, enemyp;
	JPanel roundp;
	JLabel rndL;
	JTextField rndF;
	JPanel mynamep, enamep;
	JLabel mynameL, enameL;
	JTextField mynameF, enameF;
	JPanel mypointp, epointp;
	JLabel mypointL, epointL;
	JTextField mypointF, epointF;
	JTextField resF, progF;
	JButton mydeck[], enemydeck[];
	Color color;
	String name, ename;
	
	Socket skt;
	ServerSocket svskt;
	BufferedReader br;
	PrintWriter writer;
	
	int rnd     = 1;
	int mypoint = 0;
	int epoint  = 0;
	boolean myTurn = false;
	int my_num, e_num;
	int ev = 0;
	int od = 1;
	
	
	public serverGUI() {
		name = "Player1";
		ename = "Player2";
		/* ��� ���� */
		ImageIcon icon = new ImageIcon("image/background.png");
		Image img = icon.getImage();
		Image chimg = img.getScaledInstance(1300, 800, Image.SCALE_SMOOTH);
		ImageIcon chbg = new ImageIcon(chimg);
		JLabel sizebg = new JLabel(chbg);
		
		/* �� �� ���� */
		myp = new JPanel();
		myp.setLayout(new GridLayout(1, 9, 0, 0));
		myp.setBounds(50, 550, 900, 150);
		String mydeck_name[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
		
		mydeck = new JButton[mydeck_name.length];
		for (int i=0; i<mydeck_name.length; i++) {
			mydeck[i] = new JButton(mydeck_name[i]);
			mydeck[i].setFont(new Font("���� ���", Font.BOLD, 60));
			if((i==0) || (i==2) || (i==4) || (i==6) || (i==8)) {
				mydeck[i].setBackground(color = new Color(5, 5, 5));
				mydeck[i].setForeground(Color.WHITE);
			} else {
				mydeck[i].setBackground(color = new Color(255, 255, 255));
				mydeck[i].setForeground(Color.BLACK);
			}
			myp.add(mydeck[i]);
			mydeck[i].addActionListener(new btListener());
		}
		
		/* ���� �� ���� */
		enemyp = new JPanel();
		enemyp.setLayout(new GridLayout(1, 9, 0, 0));
		enemyp.setBounds(300, 50, 900, 150);
		String enemydeck_name[] = {"?", "?", "?", "?", "?", "?", "?", "?", "?"};
		
		enemydeck = new JButton[enemydeck_name.length];
		for (int i=0; i<enemydeck_name.length; i++) {
			enemydeck[i] = new JButton(enemydeck_name[i]);
			enemydeck[i].setFont(new Font("���� ���", Font.BOLD, 60));
			if((i==0) || (i==2) || (i==4) || (i==6) || (i==8)) {
				enemydeck[i].setBackground(color = new Color(5, 5, 5));
				enemydeck[i].setForeground(Color.WHITE);
			} else {
				enemydeck[i].setBackground(color = new Color(255, 255, 255));
				enemydeck[i].setForeground(Color.BLACK);
			}
			enemyp.add(enemydeck[i]);
			
		}
		
		/* ���� ǥ�� */
		roundp = new JPanel();
		roundp.setLayout(new FlowLayout());
		roundp.setBounds(1050, 350, 150, 50);
		roundp.setBackground(Color.YELLOW);
		rndL = new JLabel("Round");
		rndL.setFont(new Font("���� ���", Font.PLAIN, 20));
		rndF = new JTextField();
		rndF.setBackground(null);
		rndF.setBorder(null);
		rndF.setEditable(false);
		rndF.setFont(new Font("���� ���", Font.BOLD, 20));
		rndF.setText(Integer.toString(rnd));
		roundp.add(rndL);
		roundp.add(rndF);
		
		
		/* �� ����Ʈ ǥ�� */
		mypointp = new JPanel();
		mypointp.setLayout(new GridLayout(2, 1));
		mypointp.setBounds(1070, 600, 80, 100);
		mypointp.setBackground(color = new Color(87, 16, 20));
		mypointL = new JLabel("����");
		mypointL.setFont(new Font("���� ���", Font.BOLD, 20));
		mypointL.setForeground(Color.ORANGE);
		mypointL.setHorizontalAlignment(JLabel.CENTER);
		mypointF = new JTextField();
		mypointF.setBackground(null);
		mypointF.setBorder(null);
		mypointF.setEditable(false);
		mypointF.setFont(new Font("���� ���", Font.BOLD, 25));
		mypointF.setForeground(Color.YELLOW);
		mypointF.setText(Integer.toString(mypoint));
		mypointF.setHorizontalAlignment(JTextField.CENTER);
		mypointp.add(mypointL);
		mypointp.add(mypointF);
		
		/* �� ����Ʈ ǥ�� */
		epointp = new JPanel();
		epointp.setLayout(new GridLayout(2, 1));
		epointp.setBounds(100, 50, 80, 100);
		epointp.setBackground(color = new Color(87, 16, 20));
		epointL = new JLabel("��� ����");
		epointL.setFont(new Font("���� ���", Font.BOLD, 15));
		epointL.setForeground(Color.ORANGE);
		epointL.setHorizontalAlignment(JLabel.CENTER);
		epointF = new JTextField();
		epointF.setBackground(null);
		epointF.setBorder(null);
		epointF.setEditable(false);
		epointF.setFont(new Font("���� ���", Font.BOLD, 25));
		epointF.setForeground(Color.YELLOW);
		epointF.setText(Integer.toString(epoint));
		epointF.setHorizontalAlignment(JTextField.CENTER);
		epointp.add(epointL);
		epointp.add(epointF);
		
		/* �� �̸��� ��� �̸� ǥ�� */
		mynamep = new JPanel();
		mynamep.setLayout(new FlowLayout());
		mynamep.setBounds(1000, 500, 220, 50);
		mynamep.setBackground(color = new Color(255, 0, 0, 0));
		mynameL = new JLabel("Player : ");
		mynameL.setFont(new Font("���� ���", Font.BOLD, 20));
		mynameL.setForeground(Color.WHITE);
		mynameF = new JTextField();
		mynameF.setBackground(null);
		mynameF.setBorder(null);
		mynameF.setEditable(false);
		mynameF.setFont(new Font("���� ���", Font.BOLD, 21));
		mynameF.setForeground(Color.WHITE);
		mynameF.setText(name);
		mynamep.add(mynameL);
		mynamep.add(mynameF);
		
		enamep = new JPanel();
		enamep.setLayout(new FlowLayout());
		enamep.setBounds(30, 200, 220, 50);
		enamep.setBackground(color = new Color(255, 0, 0, 0));
		enameL = new JLabel("Player : ");
		enameL.setFont(new Font("���� ���", Font.BOLD, 20));
		enameL.setForeground(Color.WHITE);
		enameF = new JTextField();
		enameF.setBackground(null);
		enameF.setBorder(null);
		enameF.setEditable(false);
		enameF.setFont(new Font("���� ���", Font.BOLD, 21));
		enameF.setForeground(Color.WHITE);
		enameF.setText(ename);
		enamep.add(enameL);
		enamep.add(enameF);
		
		/* ��� ��� ǥ��â */
		resF = new JTextField();
		resF.setEditable(false);
		resF.setBounds(400, 250, 500, 200);
		resF.setBackground(color = new Color(140, 70, 60));
		resF.setBorder(null);
		resF.setFont(new Font("���� ���", Font.BOLD, 45));
		resF.setForeground(Color.ORANGE);
		resF.setHorizontalAlignment(JTextField.CENTER);
		//resF.setText("ex) Player1 �¸�");
		resF.setText("��� ���� �����");
		
		/* ���� ���� ��� ǥ��â */
		progF = new JTextField();
		progF.setEditable(false);
		progF.setBounds(150, 500, 300, 50);
		progF.setBackground(color = new Color(140, 70, 60));
		progF.setBorder(null);
		progF.setFont(new Font("���� ���", Font.BOLD, 20));
		progF.setForeground(Color.yellow);
		//progF.setText("ex) Player1 ��");
		
		
		

		setContentPane(sizebg);
		add(myp);
		add(enemyp);
		add(roundp);
		add(mypointp);
		add(epointp);
		add(mynamep);
		add(enamep);
		add(resF);
		add(progF);
		
		setLayout(null);
		//setContentPane(new JLabel(new ImageIcon("image/background.png")));
		setVisible(true);
		setTitle("����� - server");
		setSize(1300, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class btListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String btname = e.getActionCommand();

			if (myTurn) {
				if ((btname.equals("0")) || (btname.equals("1")) || (btname.equals("2")) ||
					(btname.equals("3")) || (btname.equals("4")) || (btname.equals("5")) ||
					(btname.equals("6")) || (btname.equals("7")) || (btname.equals("8"))) {
						int sel_num = JOptionPane.showConfirmDialog(null, btname + " ��ư�� �����Ͻðڽ��ϱ�?", 
							"Ȯ�� �޽���", JOptionPane.OK_CANCEL_OPTION);
							
					if (sel_num == 0) {
						switch (btname) {
							case "0": 
								mydeck[0].setVisible(false);
								my_num = 0;
								break;
							case "1":
								mydeck[1].setVisible(false);
								my_num = 1;
								break;
							case "2":
								mydeck[2].setVisible(false);
								my_num = 2;
								break;
							case "3":
								mydeck[3].setVisible(false);
								my_num = 3;
								break;
							case "4":
								mydeck[4].setVisible(false);
								my_num = 4;
								break;
							case "5":
								mydeck[5].setVisible(false);
								my_num = 5;
								break;
							case "6":
								mydeck[6].setVisible(false);
								my_num = 6;
								break;
							case "7":
								mydeck[7].setVisible(false);
								my_num = 7;
								break;
							case "8":
								mydeck[8].setVisible(false);
								my_num = 8;
								break;
						}
						myTurn = false;
						progF.setText("������ ��");
						writer.println("02");
						writer.println("06" + my_num);
						writer.flush();
					}
				}
			}		
		}
	}
	
	
	void connect() {
		try {
			svskt = new ServerSocket(10101);
		} catch (IOException e) {
			System.out.println("�ش� ��Ʈ�� �����ֽ��ϴ�.");
		}
		
		try {
			System.out.println("���� ���� �Ϸ�");
			skt = svskt.accept();
			System.out.println("Client ���� �Ϸ�");
			resF.setText("1 ���带 �����մϴ�");
			br = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			writer = new PrintWriter(skt.getOutputStream());
			
			String msg;
			
			while ((msg = br.readLine()) != null) {
				
				switch (msg) {
				case "00":
					progF.setText("���� ��");
					myTurn = true;
					break;
				case "01":
					progF.setText("���� ��");
					myTurn = true;
					break;
				case "03":
					JOptionPane.showMessageDialog(null, "����� �й��Ͽ����ϴ�.", "Server ���",
							JOptionPane.INFORMATION_MESSAGE);
					epoint++;
					rnd++;
					epointF.setText(Integer.toString(epoint));
					rndF.setText(Integer.toString(rnd));
					progF.setText("���� ��");
					resF.setText(rnd +" ���带 �����մϴ�.");
					break;
				case "04":
					//resF.setText("�����ϴ�.");
					JOptionPane.showMessageDialog(null, "�����ϴ�.", "Server ���",
														JOptionPane.INFORMATION_MESSAGE);
					rnd++;
					rndF.setText(Integer.toString(rnd));
					progF.setText("���� ��");
					resF.setText(rnd +" ���带 �����մϴ�.");
					break;
				case "05":
					JOptionPane.showMessageDialog(null, "����� �̰���ϴ�.", "Server ���",
							JOptionPane.INFORMATION_MESSAGE);
					mypoint++;
					rnd++;
					mypointF.setText(Integer.toString(mypoint));
					rndF.setText(Integer.toString(rnd));
					progF.setText("���� ��");
					resF.setText(rnd +" ���带 �����մϴ�.");
					break;
				case "07":
					rnd = 9;
					rndF.setText(Integer.toString(rnd));
					resF.setText("������ ����Ǿ����ϴ�.");
					progF.setText("");
					
					if (mypoint > epoint) {
						JOptionPane.showMessageDialog(null, "����� �¸��Ͽ����ϴ�. ( WIN ) ������ ����Ǿ����ϴ�.", "���� ��� ( Server )",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (mypoint == epoint) {
						JOptionPane.showMessageDialog(null, "���� �����ϴ�. ( DRAW ) ������ ����Ǿ����ϴ�.", "���� ��� ( Server )",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (mypoint < epoint) {
						JOptionPane.showMessageDialog(null, "����� �й��Ͽ����ϴ�. ( LOSE ) ������ ����Ǿ����ϴ�.", "���� ��� ( Server )",
								JOptionPane.INFORMATION_MESSAGE);
					}		
					System.exit(0);
					break;
				default:
					e_num = Integer.parseInt(msg.substring(msg.length()-1));
					
					if (e_num % 2 == 0) {
						enemydeck[ev].setVisible(false);
						ev = ev + 2;
					} else {
						enemydeck[od].setVisible(false);
						od = od + 2;
					}
						
					
				}
				
				
			}
			
			
			
		} catch (IOException e) {
			System.out.println("���� ����");
		}
	}
}
