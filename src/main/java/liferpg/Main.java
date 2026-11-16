package liferpg;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * 프로그램 시작점.
 * 지금은 프로젝트가 제대로 실행되는지 확인하는 빈 창만 띄웁니다.
 * 실제 화면은 view 패키지에서 만들어 여기서 연결합니다.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("내 인생 RPG");
            frame.add(new JLabel("내 인생 RPG - 프로젝트 실행 확인", SwingConstants.CENTER));
            frame.setSize(480, 320);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
