INSERT INTO user (user_id, nickname, exp, level, completed_count) 
VALUES (1, "길동", 40, 2, 2), (2, "춘식", 20, 1, 3);

INSERT INTO questset(questset_id, repeat_num, completed_num, user_id, start_date, consistent, completed)
VALUES (1, 3, 0, 1, '2026-11-28', 1, 0),
(2, 4, 2, 2, '2026-11-22', 1, 0), (3, 9, 9, 1, '2026-10-24',1, 1);

INSERT INTO quest (quest_id, user_id, title, category, difficulty, completed, due_date, questset_id)
VALUES (1, 1, "줄넘기", "stamina", 10, 0, '2026-12-31', null), 
(2, 1, "영단어 암기", "intelligence", 20, 0, '2026-11-28', 1),
(3, 1, "영단어 암기", "intelligence", 20, 0, '2026-11-29', 1),
(4, 1, "영단어 암기", "intelligence", 20, 0, '2026-11-30', 1),
(5, 2, "자바 공부", "intelligence", 40, 1, '2026-11-22', 2),
(6, 2, "자바 공부", "intelligence", 40, 1, '2026-11-23', 2),
(7, 2, "자바 공부", "intelligence", 40, 0, '2026-11-24', 2),
(8, 2, "자바 공부", "intelligence", 40, 0, '2026-11-25', 2),
(9, 1, "방 청소", "cleanliness", 10, 1, '2026-10-30', null);

 