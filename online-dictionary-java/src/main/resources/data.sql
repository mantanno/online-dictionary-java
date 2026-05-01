MERGE INTO languages (id, code, name) KEY(id) VALUES (1, 'uk', 'Українська');
MERGE INTO languages (id, code, name) KEY(id) VALUES (2, 'en', 'Англійська');
MERGE INTO languages (id, code, name) KEY(id) VALUES (3, 'de', 'Німецька');

MERGE INTO words (id, text, language_id) KEY(id) VALUES (1, 'кіт', 1);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (2, 'cat', 2);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (3, 'собака', 1);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (4, 'dog', 2);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (5, 'мова', 1);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (6, 'language', 2);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (7, 'словник', 1);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (8, 'dictionary', 2);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (9, 'katze', 3);
MERGE INTO words (id, text, language_id) KEY(id) VALUES (10, 'hund', 3);

MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (1, 1, 2, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (2, 2, 1, 'noun');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (3, 3, 4, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (4, 4, 3, 'noun');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (5, 5, 6, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (6, 6, 5, 'noun');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (7, 7, 8, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (8, 8, 7, 'noun');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (9, 1, 9, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (10, 9, 1, 'Substantiv');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (11, 3, 10, 'іменник');
MERGE INTO translations (id, source_word_id, target_word_id, note) KEY(id) VALUES (12, 10, 3, 'Substantiv');
