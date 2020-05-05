package itmo.abroskin.wst.core.services.album.dto;

import java.util.Date;

public class AlbumUpdateDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    private String author;

    private Date date;

    private Integer n_songs;

    private String publisher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getN_songs() {
        return n_songs;
    }

    public void setN_songs(Integer n_songs) {
        this.n_songs = n_songs;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
