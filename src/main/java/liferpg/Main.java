package liferpg;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import liferpg.controller.ListController;
import liferpg.controller.MainController;
import liferpg.model.User;
import liferpg.service.ExpService;

/**
 * 프로그램 시작점.
 * 지금은 프로젝트가 제대로 실행되는지 확인하는 빈 창만 띄웁니다.
 * 실제 화면은 view 패키지에서 만들어 여기서 연결합니다.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("내 인생 RPG");
            User user= new User();
            ExpService expService= new ExpService();
            MainController controller= new MainController(user, expService);
            ListController listController = new ListController(user, expService);
            frame.setContentPane(new liferpg.view.MainScreen(controller,listController));
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
