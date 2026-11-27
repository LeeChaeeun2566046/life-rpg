package liferpg.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import liferpg.controller.ListController;
import liferpg.controller.MainController;

import java.awt.Component;
import java.awt.Dimension;

public class MainScreen extends JPanel {
	public MainScreen(MainController controller, ListController listController) {
		setLayout(new BorderLayout());
		 setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 80));
		
		JPanel profilePanel= new JPanel();
		profilePanel.setLayout(
			    new BoxLayout(profilePanel, BoxLayout.Y_AXIS)
			);
		
		JLabel nameLabel= new JLabel("닉네임: "+controller.getNickname());
		JLabel levelLabel= new JLabel("Lv."+controller.getLevel());
		JLabel expLabel=new JLabel("EXP"+controller.getExp()+"/"+controller.getRequiredExp());
		JProgressBar expBar = new JProgressBar(0, controller.getRequiredExp());
		expBar.setValue(controller.getExp());
		expBar.setPreferredSize(new Dimension(250, 18));
		expBar.setMaximumSize(new Dimension(250, 18));
		JPanel abilityPanel= new JPanel( new FlowLayout(FlowLayout.LEFT, 10, 0));
		JLabel intellLabel=new JLabel("지능 "+controller.getIntelligence()+"%");
		JLabel physicalLabel= new JLabel("체력 "+controller.getStamina()+"%");
		JLabel cleanLabel=new JLabel("청결도 "+controller.getCleanliness()+"%");
		abilityPanel.setMaximumSize(new Dimension(400, 35));
		abilityPanel.add(intellLabel);
		abilityPanel.add(physicalLabel);
		abilityPanel.add(cleanLabel);
		
		nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		levelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		expLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		expBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		abilityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		profilePanel.add(nameLabel);
		profilePanel.add(Box.createVerticalStrut(15));

		profilePanel.add(levelLabel);
		profilePanel.add(Box.createVerticalStrut(15));

		profilePanel.add(expLabel);
		profilePanel.add(Box.createVerticalStrut(5));

		profilePanel.add(expBar);
		profilePanel.add(Box.createVerticalStrut(40));

		profilePanel.add(abilityPanel);
		
		JPanel characterPanel= new JPanel();
		characterPanel.add(new JLabel("캐릭터 이미지"));
		
		JButton questButton= new JButton("퀘스트/업적 목록보기");

		questButton.addActionListener(e -> {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.setContentPane(new ListScreen(controller, listController));
			frame.revalidate();
			frame.repaint();
		});

		
		JPanel centerPanel= new JPanel(new GridLayout(1,2));
		centerPanel.add(profilePanel);
		centerPanel.add(characterPanel);
		
		add(centerPanel, BorderLayout.CENTER);
		add(questButton,BorderLayout.SOUTH);
		
	}

}
