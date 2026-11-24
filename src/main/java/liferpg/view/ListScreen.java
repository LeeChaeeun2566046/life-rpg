package liferpg.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ListScreen extends JPanel {
	public ListScreen() {
		setLayout(new BorderLayout());
		JPanel centerPanel= new JPanel(new GridLayout(1,2));
		
		//퀘스트 //
		JPanel questPanel= new JPanel();
		questPanel.setLayout(new BorderLayout());
		JLabel questLabel= new JLabel("퀘스트 목록");
		questPanel.add(questLabel,BorderLayout.NORTH);
		//퀘스트 목록 패널//
		JPanel questListPanel = new JPanel();
		questListPanel.setLayout(new BoxLayout(questListPanel, BoxLayout.Y_AXIS));
		JScrollPane questScrollPane = new JScrollPane(questListPanel);
		questPanel.add(questScrollPane, BorderLayout.CENTER);
		//퀘스트 하나에 있는 상세 정보//
		addQuestItem(questListPanel, "자바 공부", "공부", "어려움");
		JButton addQuestButton = new JButton("+퀘스트 추가");
		questPanel.add(addQuestButton, BorderLayout.SOUTH);

addQuestButton.addActionListener(e -> {

    // 퀘스트 추가 입력창을 담는 패널
    JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

    // 퀘스트 이름 입력
    JTextField nameField = new JTextField(15);

    // 카테고리 선택
    String[] categories = {"공부", "체력", "청결"};
    JComboBox<String> categoryBox = new JComboBox<>(categories);

    // 난이도 선택
    String[] difficulties = {"쉬움", "중간", "어려움"};
    JComboBox<String> difficultyBox = new JComboBox<>(difficulties);

    // 입력창에 구성요소 추가
    inputPanel.add(new JLabel("퀘스트 이름"));
    inputPanel.add(nameField);

    inputPanel.add(new JLabel("카테고리"));
    inputPanel.add(categoryBox);

    inputPanel.add(new JLabel("난이도"));
    inputPanel.add(difficultyBox);

    // 등록 및 취소 버튼이 있는 창 표시
    int result = JOptionPane.showConfirmDialog(
    	    this,
    	    inputPanel,
    	    "퀘스트 추가",
    	    JOptionPane.OK_CANCEL_OPTION
    	);

    	if (result == JOptionPane.OK_OPTION) {
    	    String questName = nameField.getText().trim();
    	    String category = (String) categoryBox.getSelectedItem();
    	    String difficulty = (String) difficultyBox.getSelectedItem();

    	    if (questName.isEmpty()) {
    	        JOptionPane.showMessageDialog(this, "퀘스트 이름을 입력하세요.");
    	        return;
    	    }

    	    addQuestItem(questListPanel, questName, category, difficulty);
    	}
});

		
//업적 전체 패널
JPanel achievementsPanel = new JPanel(new BorderLayout());

JLabel achievementsLabel = new JLabel("업적 목록");
achievementsPanel.add(achievementsLabel, BorderLayout.NORTH);

//여러 업적을 담을 패널
JPanel achievementsListPanel = new JPanel();
achievementsListPanel.setLayout(
 new BoxLayout(achievementsListPanel, BoxLayout.Y_AXIS)
);

//스크롤 기능
JScrollPane achievementsScrollPane =
 new JScrollPane(achievementsListPanel);

achievementsPanel.add(achievementsScrollPane, BorderLayout.CENTER);
//업적 하나를 담는 패널
addAchievementItem(achievementsListPanel, "전화 영어", 7, 3);
addAchievementItem(achievementsListPanel, "운동하기", 5, 2);
addAchievementItem(achievementsListPanel, "물 2L", 7, 3);

JButton addAchievementButton = new JButton("+업적 추가");
achievementsPanel.add(addAchievementButton, BorderLayout.SOUTH);

addAchievementButton.addActionListener(e -> {

    // 업적 추가 입력창
    JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    // 업적 이름
    JTextField nameField = new JTextField(15);

    // 목표 횟수
    JTextField goalField = new JTextField(10);

    inputPanel.add(new JLabel("업적 이름"));
    inputPanel.add(nameField);

    inputPanel.add(new JLabel("목표 횟수"));
    inputPanel.add(goalField);

    // 등록 및 취소 버튼
    int result = JOptionPane.showConfirmDialog(
        this,
        inputPanel,
        "업적 추가",
        JOptionPane.OK_CANCEL_OPTION
    );

    if (result == JOptionPane.OK_OPTION) {

        String name = nameField.getText().trim();
        String goalText = goalField.getText().trim();

        // 이름을 입력하지 않았을 때
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "업적 이름을 입력하세요.");
            return;
        }

        // 목표 횟수를 숫자로 변환
        try {
            int goal = Integer.parseInt(goalText);

            if (goal <= 0) {
                JOptionPane.showMessageDialog(this, "목표 횟수는 1 이상이어야 합니다.");
                return;
            }

            // 새로운 업적 생성
            addAchievementItem(achievementsListPanel, name, goal, 0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "목표 횟수는 숫자로 입력하세요.");
        }
    }
});


centerPanel.add(questPanel);
centerPanel.add(achievementsPanel);
add(centerPanel, BorderLayout.CENTER);

// 메인 화면으로 돌아가기 버튼
JButton backButton = new JButton("메인 화면으로 돌아가기");

backButton.addActionListener(e -> {
    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    frame.setContentPane(new MainScreen());
    frame.revalidate();
    frame.repaint();
});

add(backButton, BorderLayout.SOUTH);

} // ListScreen() 생성자 끝

		
		
		
	

private void addQuestItem(JPanel questListPanel,
                          String questName,
                          String category,
                          String difficulty) {

    // 1. 난이도에 따른 경험치 계산
    int exp = 10;

    if (difficulty.equals("중간")) {
        exp = 20;
    } else if (difficulty.equals("어려움")) {
        exp = 40;
    }

    // 2. 퀘스트 하나를 담을 패널
    JPanel questItemPanel = new JPanel();
    questItemPanel.setLayout(new BorderLayout(10, 0));

    // 3. 퀘스트 이름과 상세 정보를 담을 패널
    JPanel informationPanel = new JPanel();
    informationPanel.setLayout(
        new BoxLayout(informationPanel, BoxLayout.Y_AXIS)
    );

    JLabel questItemName = new JLabel(questName);
    JLabel questItemInformation = new JLabel(
        category + " / " + difficulty + " / +" + exp + "EXP"
    );

    // 4. 완료 체크박스
    JCheckBox questItemCheck = new JCheckBox();

    // 5. 각 구성요소를 패널에 추가
    informationPanel.add(questItemName);
    informationPanel.add(questItemInformation);

    questItemPanel.add(informationPanel, BorderLayout.WEST);
    questItemPanel.add(questItemCheck, BorderLayout.EAST);

    // 6. 퀘스트 박스의 테두리 및 여백 설정
    questItemPanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        )
    );

    questItemPanel.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 85)
    );

    // 7. 퀘스트 목록에 추가
    questListPanel.add(questItemPanel);
    questListPanel.add(Box.createVerticalStrut(15));

    // 8. 변경된 화면 갱신
    questListPanel.revalidate();
    questListPanel.repaint();
}

private void addAchievementItem(JPanel achievementsListPanel,
                                String name,
                                int goal,
                                int current) {

    // 업적 하나의 패널
    JPanel achievementItemPanel =
        new JPanel(new BorderLayout(10, 0));

    JLabel achievementName = new JLabel(name);

    // 진행 상황 표시 영역
    JPanel progressPanel = new JPanel();
    progressPanel.setLayout(
        new BoxLayout(progressPanel, BoxLayout.Y_AXIS)
    );

    JLabel progressLabel = new JLabel(
        "목표 " + goal + "회 / 현재 " + current + "회"
    );

    JProgressBar progressBar = new JProgressBar(0, goal);
    progressBar.setValue(current);

    JButton completeButton = new JButton("수행 완료");

    if (current >= goal) {
        completeButton.setText("달성 완료");
        completeButton.setEnabled(false);
    }

    // 수행 완료 버튼 클릭 이벤트
    completeButton.addActionListener(e -> {
        int count = progressBar.getValue();

        if (count < goal) {
            count++;
            progressBar.setValue(count);

            progressLabel.setText(
                "목표 " + goal + "회 / 현재 " + count + "회"
            );
        }

        if (count >= goal) {
            completeButton.setText("달성 완료");
            completeButton.setEnabled(false);
        }
    });

    progressPanel.add(progressLabel);
    progressPanel.add(progressBar);
    progressPanel.add(completeButton);

    achievementItemPanel.add(achievementName, BorderLayout.WEST);
    achievementItemPanel.add(progressPanel, BorderLayout.CENTER);

    achievementItemPanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        )
    );

    achievementItemPanel.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, 110)
    );

    achievementsListPanel.add(achievementItemPanel);
    achievementsListPanel.add(Box.createVerticalStrut(15));

    achievementsListPanel.revalidate();
    achievementsListPanel.repaint();
}



}


