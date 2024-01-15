UPDATE word_count wc
SET word = w.word
FROM words w
WHERE wc.word_id = w.id;