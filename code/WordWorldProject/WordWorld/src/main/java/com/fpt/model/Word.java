package com.fpt.model;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class Word {
    public int id;
    public String the_word;
    public String description;
    public int status;
    public int count;
    public long created;

    public Word(int id, String the_word, String description, int status, int count, long created) {
        this.id = id;
        this.the_word = the_word;
        this.description = description;
        this.status = status;
        this.count = count;
        this.created = created;
    }
    public Word(String the_word, String description, int status, int count, long created) {
        this.the_word = the_word;
        this.description = description;
        this.status = status;
        this.count = count;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (count != word.count) return false;
        if (created != word.created) return false;
        if (id != word.id) return false;
        if (status != word.status) return false;
        if (description != null ? !description.equals(word.description) : word.description != null)
            return false;
        if (!the_word.equals(word.the_word)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + the_word.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + count;
        result = 31 * result + (int) (created ^ (created >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", the_word='" + the_word + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", created=" + created +
                '}';
    }
}
