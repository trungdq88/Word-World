package com.fpt.model;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class Word {
    public int id;
    public int word_id;
    public String the_word;
    public String description;
    public int status;
    public long created;

    public Word(int id, int word_id, String the_word, String description, int status, long created) {
        this.id = id;
        this.word_id = word_id;
        this.the_word = the_word;
        this.description = description;
        this.status = status;
        this.created = created;
    }

    public Word(int word_id, String the_word, String description, int status, long created) {
        this.id = id;
        this.word_id = word_id;
        this.the_word = the_word;
        this.description = description;
        this.status = status;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (created != word.created) return false;
        if (status != word.status) return false;
        if (word_id != word.word_id) return false;
        if (description != null ? !description.equals(word.description) : word.description != null)
            return false;
        if (!the_word.equals(word.the_word)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word_id;
        result = 31 * result + the_word.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word_id=" + word_id +
                ", the_word='" + the_word + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}
